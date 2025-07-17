/**
 * Scheduler per l'aggiornamento periodico dei dati del wallet.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.scheduler;

import java.util.Optional;

import client.model.clients.UserClient;
import client.model.user.Wallet;
import javafx.concurrent.Task;
import javafx.util.Duration;

/**
 * Estende {@link BaseUpdateScheduler} per eseguire l'aggiornamento dei dati del
 * wallet dell'utente.
 */
public class WalletUpdateScheduler extends BaseUpdateScheduler<Wallet> {
    /**
     * Nome utente dell'utente corrente
     */
    private final String username;

    /**
     * Costruttore del scheduler.
     * 
     * @param updateInterval periodo di aggiornamento
     * @param username       nome utente dell'utente corrente
     */
    public WalletUpdateScheduler(Duration updateInterval, String username) {
        super("Wallet for " + username, updateInterval);
        this.username = username;
    }

    /**
     * Crea il task responsabile del fetch dei dati del wallet.
     * 
     * @return una {@link Task} che esegue la chiamata a
     *         {@link UserClient#getWallet(String)}
     *         e restituisce un oggetto {@link Wallet}
     */
    @Override
    protected Task<Wallet> createTask() {
        return new Task<>() {
            /**
             * Metodo chiamato in background per recuperare i dati del wallet.
             * 
             * @return il wallet dell'utente
             * @throws IllegalStateException se il client restituisce dati null
             */
            @Override
            protected Wallet call() throws IllegalStateException {
                System.out.println("Fetching wallet for " + username);
                return Optional
                        .ofNullable(UserClient.getWallet(username))
                        .orElseThrow(() -> new IllegalStateException("Null data from UserClient for wallet"));
            }
        };
    }
}
