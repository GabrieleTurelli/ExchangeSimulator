/**
 * Classe che gestisce la richiesta di registrazione al server.
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.clients;

import java.io.IOException;

public class RegisterClient {
    /**
     * Invia una richiesta di registrazione al server utilizzando le credenziali fornite.
     * Crea una nuova connessione al server, invia il comando di registrazione con
     * username e password, legge la risposta e restituisce il risultato della
     * registrazione.
     *
     * @param username il nome utente da inviare al server
     * @param password la password associata all'utente
     * @return una stringa che rappresenta il risultato della registrazione
     */
    public static String sendRegisterRequest(String username, String password) {
        try (ClientConnection connection = new ClientConnection()) {
            connection.sendRequest("\\register " + username + " " + password);
            return connection.readResponse();
        } catch (IOException e) {
            System.err.println("Error while sending register request: " + e.getMessage());
            return "ERROR;Connection to server failed";
        }
    }
}
