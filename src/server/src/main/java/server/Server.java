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

public class Server {
    private static final int PORT = 12345;
    private static final HashMap<String, Double> coins = new HashMap<>(
            Map.of(
                    "BTC", 85015.35,
                    "ETH", 1598.39,
                    "BNB", 607.20,
                    "VH3", 1598.39,
                    "SOL", 113.15,
                    "GSO", 4598.39,
                    "SHL", 258.39,
                    "ADO", 18.39
                    ));

    private static Connection generatorConnection;
    private static Connection userConnection;
    private static Connection marketConnection;
    private static LoginServer loginServer;
    private static RegisterServer registerServer;
    private static UserServer userServer;
    private static MarketServer marketServer;
    private static MarketActionServer marketActionServer;

    // Map.of(),
    // Map.of());

    public static void main(String[] args) throws IOException, SQLException {
        DataSource ds = DbConnector.getDataSource();
        userConnection = ds.getConnection();
        marketConnection = ds.getConnection();
        generatorConnection = ds.getConnection();

        loginServer = new LoginServer(userConnection);
        registerServer = new RegisterServer(userConnection);
        userServer = new UserServer(userConnection);
        marketServer = new MarketServer(marketConnection);
        marketActionServer = new MarketActionServer(marketConnection);

        // Controlla se viene passsato il flag --init dall run.sh ed in caso inizializza
        // il db
        boolean initMode = Arrays.stream(args)
                .anyMatch("--init"::equals);

        if (initMode) {
            init();

        }

        // Start del service per la creazione dei prezzi delle kline e dell'orderbook
        // Necessito la generazione a random dei prezzi perche senza interazione con
        // altri utenti i prezzi non si muoverebbero e sarebbe tutto fermo
        RandomPriceGeneratorScheduler priceService = new RandomPriceGeneratorScheduler(
                coins.keySet().toArray(String[]::new), generatorConnection);
        priceService.start(1);

        // inizilizzazione del server socket sulla porta 12345
        try (
                ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                try {
                    // creazioe e start di un nuovo thread per ogni client che richiedere la
                    // connessione
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Server failed to start or run on port " + PORT + ": " +
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(input, java.nio.charset.StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(output, true, java.nio.charset.StandardCharsets.UTF_8)) {

            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println("Received request from " + clientSocket.getRemoteSocketAddress() + ": " + request);

                String trimmedRequest = request.trim();
                if (trimmedRequest.isEmpty()) {
                    System.out.println(
                            "Received empty request from " + clientSocket.getRemoteSocketAddress() + ", ignoring.");
                    continue;
                }

                String[] parts = trimmedRequest.split(" ", 2);
                String command = parts[0];

                String response = null;

                switch (command) {
                    case "\\login" -> response = loginServer.handleLogin(trimmedRequest);

                    case "\\register" -> response = registerServer.handleRegistration(trimmedRequest);

                    // case "\\logout" -> response =

                    case "\\get-coins" -> response = marketServer.handleGetCoins();

                    case "\\get-last-price" -> response = marketServer.handleGetLastPrice(trimmedRequest);

                    case "\\get-kline" -> response = marketServer.handleGetKline(trimmedRequest);

                    case "\\get-daily-market-data" -> response = marketServer.handleGetKline(trimmedRequest);

                    case "\\get-history" -> response = marketServer.handleGetHistory(trimmedRequest);

                    case "\\get-orderbook" -> response = marketServer.handleGetOrderBook(trimmedRequest);

                    case "\\get-orderbook-level" -> response = marketServer.handleGetOrderBookLevel(trimmedRequest);

                    case "\\get-wallet" -> response = userServer.handleGetWallet(trimmedRequest);

                    case "\\get-open-position" -> response = userServer.handleGetWallet(trimmedRequest);

                    case "\\buy-market" -> response = marketActionServer.handleBuyMarket(trimmedRequest);

                    case "\\buy-limit" -> response = marketActionServer.handleBuyLimit(trimmedRequest);

                    case "\\sell-market" -> response = marketActionServer.handleSellMarket(trimmedRequest);

                    case "\\sell-limit" -> response = marketActionServer.handleSellLimit(trimmedRequest);

                    default -> {
                        System.out.println("Received unknown command '" + command + "' from "
                                + clientSocket.getRemoteSocketAddress());
                        response = "ERROR: Unknown command '" + command + "'";
                    }
                }

                if (response != null) {
                    writer.println(response);
                    System.out.println("Sent response to " + clientSocket.getRemoteSocketAddress() + ": " + response);
                }
            }

            System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());

        } catch (IOException e) {
            System.err.println(
                    "IOException handling client " + clientSocket.getRemoteSocketAddress() + ": " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(
                    "SQLException handling client " + clientSocket.getRemoteSocketAddress() + ": " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error handling client " + clientSocket.getRemoteSocketAddress() + ": "
                    + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void init() throws IOException, SQLException {
        DbInitializer dbInitializer = new DbInitializer(coins, DbConnector.getConnection());

        for (String coin : coins.keySet()) {

            dbInitializer.dropTable("coin_" + coin);
        }
        dbInitializer.dropTable("Coins");
        dbInitializer.dropTable("coin_BTC");
        dbInitializer.dropTable("coin_SOL");
        dbInitializer.dropTable("coin_ETH");
        dbInitializer.dropTable("coin_XRP");
        dbInitializer.dropTable("orderbook_BTC");
        dbInitializer.dropTable("orderbook_SOL");
        dbInitializer.dropTable("orderbook_ETH");
        dbInitializer.dropTable("orderbook_XRP");
        dbInitializer.dropTable("Coins");
        dbInitializer.initializeDatabase();

    }
}