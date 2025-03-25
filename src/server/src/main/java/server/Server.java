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

import server.actions.Login;
import server.actions.Register;

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
                String response = Login.handleLogin(request);
                writer.println(response);
            } else if (request.startsWith("\\register")) {
                String response = Register.handleRegistration(request);
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