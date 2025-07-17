package client.model.clients;

import java.io.IOException;

import client.model.market.OrderRequest;
import client.model.user.Wallet;

/**
 * Classe che gestisce le richieste relative all'utente.
 * 
 * @author Gabriele Turelli
 * @version 1.0
 */
public class UserClient {

    /**
     * Invia una richiesta al server per ottenere il wallet dell'utente specificato.
     * 
     * @param username il nome utente di cui si vuole ottenere il wallet
     * @return un oggetto {@link Wallet} contenente i dati del wallet,
     *         oppure {@code null} in caso di errore di comunicazione o se la
     *         risposta del server non è corretta.
     */
    public static Wallet getWallet(String username) {
        try {
            try (ClientConnection connection = new ClientConnection()) {
                connection.sendRequest("\\get-wallet " + username);
                String response = connection.readResponse();

                if (response.contains("OK")) {
                    String walletString = response.split(";")[1];
                    return new Wallet(walletString);
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

    /**
     * Invia al server una richiesta di acquisto di mercato per la cripto
     * specificata.
     * 
     * @param username     il nome utente che effettua l'ordine
     * @param orderRequest un oggetto {@link OrderRequest} che contiene la
     *                     cripto da acquistare e la quantità desiderata
     * @return una stringa contenente i dettagli della risposta del server (ad
     *         es. ID dell'ordine), oppure {@code null} in caso di errore
     *         imprevisto durante l’invio della richiesta o di risposta non
     *         conforme.
     * @throws RuntimeException se il server restituisce una risposta di errore
     *                          diversa da un problema di I/O
     */
    public static String sendBuyMarketOrder(String username, OrderRequest orderRequest) {
        try {
            try (ClientConnection connection = new ClientConnection()) {
                connection.sendRequest(
                        "\\buy-market " + username + " " + orderRequest.getCoin() + " " + orderRequest.getAmount());
                String response = connection.readResponse();

                // if (response.contains("OK")) {
                //     return response.split(";")[1];
                // } else {
                // }
                return response.split(";")[1];
            }
        } catch (Exception e) {
            System.err.println("Unexpected error while sending buy market order: " + e.getMessage());
            return null;
        }
    }

    /**
     * Invia al server una richiesta di vendita di mercato per la cripto
     * specificata.
     * 
     * @param username     il nome utente che effettua l'ordine
     * @param orderRequest un oggetto {@link OrderRequest} che contiene la
     *                     cripto da vendere e la quantità desiderata
     * @return una stringa contenente il risultato dell'operazione
     * @throws RuntimeException se il server restituisce una risposta di errore
     *                          diversa da un problema di I/O
     */
    public static String sendSellMarketOrder(String username, OrderRequest orderRequest) {
        try {
            try (ClientConnection connection = new ClientConnection()) {
                connection.sendRequest(
                        "\\sell-market " + username + " " + orderRequest.getCoin() + " " + orderRequest.getAmount());
                String response = connection.readResponse();

                // if (response.contains("OK")) {
                //     return response.split(";")[1];
                // } else {
                //     System.err.println("Error in sell market order: " + response);
                //     return response.split(";")[1];
                // }

                return response.split(";")[1];
            }
        } catch (Exception e) {
            System.err.println("Unexpected error while sending sell market order: " + e.getMessage());
            return null;
        }
    }
}
