
package client.model;

public class MarketClient {

    public static double getCoinPrice(String coin) {
        try {
            ClientConnection connection = new ClientConnection();
            System.out.println("Requesting price for " + coin);   
            connection.sendRequest("\\get-last-price " + coin);
            String response = connection.readResponse();
            System.out.println("Request sent : " + response);
            
            if (response.contains("OK")){
                double price = Double.parseDouble(response.split(";")[1]);
                return price;
            }else {
                return -1.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0;
        }

    }
}