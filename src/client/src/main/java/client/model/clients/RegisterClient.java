package client.model.clients;

import java.io.IOException;

public class RegisterClient {

    public static String sendRegisterRequest(String username, String password) {
        try (ClientConnection connection = new ClientConnection()) {
            connection.sendRequest("\\register " + username + " " + password);
            return connection.readResponse();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR;Connection to server failed";
        }
    }
}
