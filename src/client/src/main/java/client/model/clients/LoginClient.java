/**
 * Classe che gestisce la richiesta di login al server.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.clients;

import java.io.IOException;

public class LoginClient {

    /**
     * Invia una richiesta di login al server utilizzando le credenziali fornite.
     * Crea una nuova connessione al server, invia il comando di login con
     * username e password, legge la risposta e verifica se il login ha avuto
     * successo.
     *
     * @param username il nome utente da inviare al server
     * @param password la password associata all'utente
     * @return {@code true} se la risposta del server indica successo ("OK"),
     *         {@code false} in caso di fallimento o errore di comunicazione
     */
    public static boolean sendLoginRequest(String username, String password) {
        try (ClientConnection connection = new ClientConnection()) {
            connection.sendRequest("\\login " + username + " " + password);
            String response = connection.readResponse();
            System.out.println("Request sent : " + response);
            String responseStatus = response.split(";")[0];

            return responseStatus.contains("OK");

        } catch (IOException e) {
            System.err.println(password + "Error while sending login request: " + e.getMessage());
            return false;
        }
    }
}
