package client.model;

import java.io.IOException;

public class LoginClient {

    public static String sendLoginRequest(String username, String password) {
        try (ClientConnection connection = new ClientConnection()) {
            connection.sendRequest("\\login " + username + " " + password);
            return connection.readResponse();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR;Connection to server failed";
        }
    }
}
