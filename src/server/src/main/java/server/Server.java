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
import server.model.db.DbInitializer;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException, SQLException, InterruptedException {
        DbInitializer dbInitializer = new DbInitializer();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void handleClient(Socket clientSocket) {
        try (
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true)) {
            String request;
            if ((request = reader.readLine()) != null) {
                System.out.println("Received request: " + request);

                if (request.startsWith("\\login")) {
                    String response = LoginServer.handleLogin(request);
                    writer.println(response);
                } else if (request.startsWith("\\register")) {
                    String response = RegisterServer.handleRegistration(request);
                    writer.println(response);
                } else if (request.startsWith("\\logout")) {
                    writer.println("OK: Logged out");
                } else if (request.startsWith("\\get-last-price")) {
                    String response = MarketServer.handleGetLastPrice(request);
                    writer.println(response);
                } else if (request.startsWith("\\get-kline")) {
                    String response = MarketServer.handleGetKline(request);
                    writer.println(response);
                } else if (request.startsWith("\\get-daily-market-data")) {
                    String response = MarketServer.handleGetKline(request);
                    writer.println(response);
                } else {
                    writer.println("ERROR: Unknown command");
                }
            }

            System.out.println("Client disconnected");

        } catch (IOException e) {
            System.out.println("IO EXC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL EXC");
            e.printStackTrace();
        }
    }
}