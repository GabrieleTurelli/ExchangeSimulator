
package client.model;

public class MarketClient {

    public static double getCoinPrice(String coin) {
        try {
            ClientConnection connection = new ClientConnection();
            System.out.println("Requesting price for " + coin);   
            connection.sendRequest("\\get-last-price " + coin);
            String response = connection.readResponse();
            System.out.println("Request sent : " + response);

            double price = Double.parseDouble(response.split(" ")[1]);
            return price;
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0;
        }

    }
}