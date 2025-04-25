package client.model.clients;

import java.io.IOException;

public class LoginClient {

    public static boolean sendLoginRequest(String username, String password) {
        try (ClientConnection connection = new ClientConnection()) {
            connection.sendRequest("\\login " + username + " " + password);
            String response = connection.readResponse();
            System.out.println("Request sent : " + response);
            String responseStatus = response.split(";")[0];

            return responseStatus.equals("OK");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
