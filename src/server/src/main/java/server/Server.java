package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import server.actions.LoginServer;
import server.actions.MarketServer;
import server.actions.RegisterServer;
import server.actions.UserServer;
import server.model.service.RandomPriceGeneratorService;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException, SQLException {
        // DbInitializer.initializeDatabase("BTC");
        System.out.println("Database Initialized successfully.");
        RandomPriceGeneratorService priceService = new RandomPriceGeneratorService("BTC");
        priceService.start(1);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected from " +
                            clientSocket.getRemoteSocketAddress());
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

    // public static void main(String[] args) throws IOException, SQLException {
        // DbInitializer.dropTable(DbConnector.getConnection(), "user_test");
        // DbInitializer.createUserTable(DBConnector.getConnection(), "user_test");
        // DbInitializer.createCoinTable(DbConnector.getConnection(), "ETH");
        // CoinDAO coinDao = new CoinDAO("ETH");
        // UserDAO userDao = new UserDAO("test");
        // userDao.addCoin("ETH", 50);
        // coinDao.populateCoinTable(1500, 50);
        // RandomPriceGeneratorService randomPriceGenerator = new RandomPriceGeneratorService("BTC");
        // randomPriceGenerator.start(10);
    // }

    private static void handleClient(Socket clientSocket) {
        try (
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(input, java.nio.charset.StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(output, true, java.nio.charset.StandardCharsets.UTF_8)) { // Auto-flush
                                                                                                               // true

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
                    case "\\login" -> response = LoginServer.handleLogin(trimmedRequest);

                    case "\\register" -> response = RegisterServer.handleRegistration(trimmedRequest);

                    case "\\logout" -> response = "OK: Logged out";

                    case "\\get-last-price" -> response = MarketServer.handleGetLastPrice(trimmedRequest);

                    case "\\get-kline" -> response = MarketServer.handleGetKline(trimmedRequest);

                    case "\\get-daily-market-data" -> response = MarketServer.handleGetKline(trimmedRequest);

                    case "\\get-history" -> response = MarketServer.handleGetHistory(trimmedRequest);

                    case "\\get-wallet" -> response = UserServer.handleGetWallet(trimmedRequest);

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
}