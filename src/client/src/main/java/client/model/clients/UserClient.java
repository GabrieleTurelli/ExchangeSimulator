package client.model.clients;

import client.model.user.Wallet;

public class UserClient {

    public static Wallet getWallet(String username) {

        try {
            ClientConnection connection = new ClientConnection();
            // System.out.println("Requesting price for " + username);
            connection.sendRequest("\\get-wallet " + username);
            String response = connection.readResponse();
            // System.out.println("Request sent : " + response);

            if (response.contains("OK")) {
                String walletString = response.split(";")[1];
                Wallet wallet = new Wallet(walletString); 
                // System.out.println("Wallet received: " + wallet); 
                return wallet;
                // return new Wallet(walletString);
                
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
