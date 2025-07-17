/**
 * Classe che gestisce la connessione client al server tramite socket.
 * Fornisce metodi per inviare richieste e leggere risposte.
 * Implementa AutoCloseable per garantire la chiusura delle risorse.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Implementa {@link AutoCloseable} in modo da poter utilizzare la classe in un blocco try-with-resource e 
// garantire la chiusura automatica delle risorse.
public class ClientConnection implements AutoCloseable {
    /**
     * Indirizzo IP del server a cui connettersi.
     */
    private static final String SERVER_HOST = "127.0.0.1";

    /**
     * Porta del server a cui connettersi.
     */
    private static final int SERVER_PORT = 12345;

    /**
     * Socket utilizzato per la comunicazione con il server.
     */
    private final Socket socket;

    /**
     * Oggetto per inviare dati al server.
     */
    private final PrintWriter writer;

    /**
     * Oggetto per leggere dati in arrivo dal server.
     */
    private final BufferedReader reader;

    /**
     * Costruttore della classe ClientConnection.
     * Inizializza socket, writer e reader.
     *
     * @throws IOException se si verifica un errore di I/O durante la connessione.
     */
    public ClientConnection() throws IOException {
        // Inizializza il socket verso l'host e la porta specificati
        this.socket = new Socket(SERVER_HOST, SERVER_PORT);
        // Crea un writer per inviare messaggi al server (auto-flush abilitato)
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        // Crea un reader per ricevere messaggi dal server
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Invia una richiesta di testo al server.
     *
     * @param request stringa contenente la richiesta da inviare.
     */
    public void sendRequest(String request) {
        // Invia la riga al server
        writer.println(request);
    }

    /**
     * Legge e restituisce la risposta del server.
     * Blocca finché non riceve una linea di testo oppure si verifica un errore.
     *
     * @return la linea di risposta dal server.
     * @throws IOException se si verifica un errore di I/O durante la lettura.
     */
    public String readResponse() throws IOException {
        // Restituisce la prima linea di risposta ricevuta
        return reader.readLine();
    }

    /** Implementazione del metodo close() dell'interfaccia AutoCloseable.
     * Chiude tutte le risorse aperte: reader, writer e socket.
     * Viene invocato automaticamente alla fine di un blocco try-with-resources.
     */
    @Override
    public void close() {
        try {
            // Chiude il reader, se inizializzato
            if (reader != null)
                reader.close();
            // Chiude il writer, se inizializzato
            if (writer != null)
                writer.close();
            // Chiude il socket, se inizializzato
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            // In caso di eccezione, logga l'errore su stderr
            System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
        }
    }
}
