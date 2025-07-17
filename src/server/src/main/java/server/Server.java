package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import server.actions.LoginServer;
import server.actions.MarketActionServer;
import server.actions.MarketServer;
import server.actions.RegisterServer;
import server.actions.UserServer;
import server.model.db.DbConnector;
import server.model.db.DbInitializer;
import server.model.scheduler.RandomPriceGeneratorScheduler;

/**
 * Classe principale del server.
 * <p>
 * Gestisce l'inizializzazione del database, il pool di connessioni,
 * l'avvio del scheduler per i prezzi casuali e l'accettazione di client
 * tramite ServerSocket su una porta predefinita.
 * </p>
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
public class Server {

    /** Porta TCP su cui il server rimane in ascolto. */
    private static final int PORT = 12345;

    /** Mappa delle criptovalute con prezzo iniziale. */
    private static final HashMap<String, Double> coins = new HashMap<>(
            Map.of(
                    "BTC", 85015.35,
                    "ETH", 1598.39,
                    "BNB", 607.20,
                    "VH3", 1598.39,
                    "SOL", 113.15,
                    "GSO", 4598.39,
                    "SHL", 258.39,
                    "ADO", 18.39));

    private static Connection generatorConnection;
    private static Connection userConnection;
    private static Connection marketConnection;

    private static LoginServer loginServer;
    private static RegisterServer registerServer;
    private static UserServer userServer;
    private static MarketServer marketServer;
    private static MarketActionServer marketActionServer;

    /**
     * Punto di ingresso dell'applicazione.
     * <p>
     * Configura hook di shutdown, inizializza connessioni al database,
     * avvia il servizio di generazione prezzi e accetta connessioni client.
     * </p>
     *
     * @param args argomenti della linea di comando; se contiene "--init"
     *             vengono (ri)inizializzate le tabelle del database
     * @throws IOException  in caso di errori di I/O sul ServerSocket
     * @throws SQLException in caso di errori di accesso al database
     */
    public static void main(String[] args) throws IOException, SQLException {
        // Registra un hook per chiusura pulita delle connessioni al termine
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            shutdown();
        }));

        // Ottiene il DataSource e apre connessioni separate per utenti, mercato e
        // generator
        DataSource ds = DbConnector.getDataSource();
        userConnection = ds.getConnection();
        marketConnection = ds.getConnection();
        generatorConnection = ds.getConnection();

        // Inizializza i componenti server per ciascun servizio
        loginServer = new LoginServer(userConnection);
        registerServer = new RegisterServer(userConnection);
        userServer = new UserServer(userConnection);
        marketServer = new MarketServer(marketConnection);
        marketActionServer = new MarketActionServer(marketConnection);

        // Se è passato il flag --init, ricrea le tabelle del database
        boolean initMode = Arrays.stream(args)
                .anyMatch("--init"::equals);
        if (initMode) {
            System.out.println("Initializing database...");
            init();
        }

        boolean randomPrice = Arrays.stream(args)
                .anyMatch("--random"::equals);

        if (randomPrice) {
            // Avvia il scheduler per generare prezzi casuali periodicamente solo nel caso
            // gli venga passato il flag
            RandomPriceGeneratorScheduler priceService = new RandomPriceGeneratorScheduler(
                    coins.keySet().toArray(String[]::new), generatorConnection);
            priceService.start(1); // un aggiornamento al secondo
        }

        // Avvia il ServerSocket per accettare client
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    // Per ogni client, avvia un thread dedicato
                    new Thread(() -> handleClient(clientSocket)).start();
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server failed to start or run on port " + PORT + ": "
                    + e.getMessage());
        }
    }

    /**
     * Gestisce la comunicazione con un singolo client su un Socket.
     * <p>
     * Legge richieste, instrada i comandi ai server di azione e invia risposte.
     * </p>
     *
     * @param clientSocket socket della connessione client
     */
    private static void handleClient(Socket clientSocket) {
        try (
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(input, java.nio.charset.StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(output, true,
                        java.nio.charset.StandardCharsets.UTF_8)) {

            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println("Received request from " + clientSocket.getRemoteSocketAddress() + ": " + request);

                // Ignora richieste vuote
                String trimmed = request.trim();
                if (trimmed.isEmpty()) {
                    System.out.println("Ignoring empty request from " + clientSocket.getRemoteSocketAddress());
                    continue;
                }

                String[] parts = trimmed.split(" ", 2);
                String command = parts[0];
                String response;

                // Instrada il comando al server di azione appropriato
                switch (command) {
                    case "\\login" -> response = loginServer.handleLogin(trimmed);
                    case "\\register" -> response = registerServer.handleRegistration(trimmed);
                    case "\\get-coins" -> response = marketServer.handleGetCoins();
                    case "\\get-last-price" -> response = marketServer.handleGetLastPrice(trimmed);
                    case "\\get-kline" -> response = marketServer.handleGetKline(trimmed);
                    case "\\get-daily-market-data" -> response = marketServer.handleGetKline(trimmed);
                    case "\\get-history" -> response = marketServer.handleGetHistory(trimmed);
                    case "\\get-orderbook" -> response = marketServer.handleGetOrderBook(trimmed);
                    case "\\get-orderbook-level" -> response = marketServer.handleGetOrderBookLevel(trimmed);
                    case "\\get-wallet" -> response = userServer.handleGetWallet(trimmed);
                    case "\\get-open-position" -> response = userServer.handleGetOpenPosition(trimmed);
                    case "\\buy-market" -> response = marketActionServer.handleBuyMarket(trimmed);
                    case "\\buy-limit" -> response = marketActionServer.handleBuyLimit(trimmed);
                    case "\\sell-market" -> response = marketActionServer.handleSellMarket(trimmed);
                    case "\\sell-limit" -> response = marketActionServer.handleSellLimit(trimmed);
                    default -> {
                        System.out.println("Unknown command '" + command + "' from "
                                + clientSocket.getRemoteSocketAddress());
                        response = "ERROR: Unknown command '" + command + "'";
                    }
                }

                // Invia la risposta se non nulla
                if (response != null) {
                    writer.println(response);
                    System.out.println("Sent response to " + clientSocket.getRemoteSocketAddress() + ": " + response);
                }
            }

            System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());

        } catch (IOException | SQLException e) {
            System.err.println("Error handling client " + clientSocket.getRemoteSocketAddress() + ": "
                    + e.getMessage());
        }
    }

    /**
     * (Ri)inizializza il database, cancellando e ricreando tabelle.
     *
     * @throws IOException  in caso di errori I/O durante l'inizializzazione
     * @throws SQLException in caso di errori SQL
     */
    public static void init() throws IOException, SQLException {
        DbInitializer dbInit = new DbInitializer(coins, DbConnector.getConnection());

        // Elimina tabelle esistenti per ricreare daccapo
        for (String coin : coins.keySet()) {
            dbInit.dropTable("coin_" + coin);
        }
        dbInit.dropTable("Coins");
        dbInit.initializeDatabase();
        System.out.println("Database initialized successfully.");
    }

    /**
     * Chiude le connessioni al database in modo pulito.
     */
    private static void shutdown() {
        try {
            if (userConnection != null && !userConnection.isClosed())
                userConnection.close();
            if (marketConnection != null && !marketConnection.isClosed())
                marketConnection.close();
            if (generatorConnection != null && !generatorConnection.isClosed())
                generatorConnection.close();
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    /**
     * Fornisce la connessione usata per il generatore di prezzi.
     *
     * @return connessione al database utilizzata dal scheduler
     */
    public static Connection getGeneratorConnection() {
        return generatorConnection;
    }
}
