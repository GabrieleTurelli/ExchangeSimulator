package model.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import model.db.DbConnector;
import java.sql.Connection;
import java.sql.SQLException;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Login server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                handleClient(clientSocket);
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
                PrintWriter writer = new PrintWriter(output, true)
        ) {
            String request = reader.readLine();
            System.out.println("Received request: " + request);

            if (request.startsWith("\\login")) {
                String response = LoginServer.handleLogin(request);
                writer.println(response);
            } else if (request.startsWith("\\register")) {
                String response = RegisterServer.handleRegistration(request);
                writer.println(response);
            } else if (request.startsWith("\\logout")) {
//                handleLogout(request, writer);
            } else {
                writer.println("ERROR: Unknown command");
            }
        } catch (IOException e){

            System.out.println("IO EXC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL EXC");
            e.printStackTrace();
        }
    }

}