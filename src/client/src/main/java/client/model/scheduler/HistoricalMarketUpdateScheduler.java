/**
 * Scheduler per l'aggiornamento periodico dei dati di mercato
 * per aggiornare il grafico.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.scheduler;

import java.util.Optional;

import client.model.clients.MarketClient;
import client.model.market.KlineHistory;
import javafx.concurrent.Task;
import javafx.util.Duration;

/**
 * Estende {@link BaseUpdateScheduler} per eseguire l'aggiornamento dei dati di
 * mercato.
 */
public class HistoricalMarketUpdateScheduler extends BaseUpdateScheduler<KlineHistory> {

    /**
     * Simbolo della coin
     */
    private final String coin;

    /**
     * Costruttore del scheduler.
     * 
     * @param coin           simbolo della coin (es. "BTC")
     * @param updateInterval periodo di aggiornamento
     */
    public HistoricalMarketUpdateScheduler(String coin, Duration updateInterval) {
        super("Market history for " + coin, updateInterval);
        this.coin = coin;
    }

    /**
     * Crea il task responsabile del fetch dei dati di mercato.
     * 
     * @return una {@link Task} che esegue la chiamata a
     *         {@link MarketClient#getHistory(String)}
     *         e restituisce un oggetto {@link KlineHistory}
     */
    @Override
    protected Task<KlineHistory> createTask() {
        return new Task<>() {
            /**
             * Metodo chiamato in background per recuperare i dati.
             * 
             * @return i dati di mercato storici per la "coin"
             * @throws IllegalStateException se il client restituisce dati null
             */
            @Override
            protected KlineHistory call() throws IllegalStateException {
                System.out.println("Fetching historical data for " + coin);
                // try {
                return Optional
                        .ofNullable(MarketClient.getHistory(coin))
                        .orElseThrow(
                                () -> new IllegalStateException("Null data from MarketClient for Historical Data"));
                // updateMessage("Last update: " + java.time.LocalTime.now());
                // return data;
                // } catch (Exception e) {
                // // updateMessage("Error fetching data: " + e.getMessage());
                // throw e;
                // }
            }
        };
    }
}
