
package client.model;

import java.io.IOException;

public class MarketClient {
    
    public static String getCoinPrice(String coin){
        try (ClientConnection connection = new ClientConnection()) {
            connection.sendRequest("\\get-price " + coin);
            System.out.println("Request sent : " +  connection.readResponse());
            return connection.readResponse();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR;Connection to server failed";
        }
    }
}