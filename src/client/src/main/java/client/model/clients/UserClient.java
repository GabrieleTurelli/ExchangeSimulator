/**
 * Classe che gestisce le richieste relative all'utente.
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.clients;

import java.io.IOException;

import client.model.user.Wallet;

public class UserClient {
    /**
     * Invia una richiesta al server per ottenere il wallet dell'utente specificato.
     * 
     * @param username il nome utente di cui si vuole ottenere il wallet
     * @return un oggetto {@link Wallet} contenente i dati del wallet, oppure null
     *         in caso di errore.
     */
    public static Wallet getWallet(String username) {

        try {
            try (ClientConnection connection = new ClientConnection()) {
                connection.sendRequest("\\get-wallet " + username);
                String response = connection.readResponse();

                if (response.contains("OK")) {
                    String walletString = response.split(";")[1];
                    Wallet wallet = new Wallet(walletString);
                    return wallet;
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while getting wallet: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while getting wallet: " + e.getMessage());
            return null;
        }

    }
}
