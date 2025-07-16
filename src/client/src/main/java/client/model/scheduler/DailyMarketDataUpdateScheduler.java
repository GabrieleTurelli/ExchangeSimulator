/**
 * Scheduler per l'aggiornamento periodico dei dati di mercato
 * per aggiornare il grafico.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.scheduler;

import java.io.IOException;
import java.util.Optional;

import client.model.clients.MarketClient;
import client.model.market.DailyMarketData;
import javafx.concurrent.Task;
import javafx.util.Duration;

/**
 * Estende {@link BaseUpdateScheduler} per eseguire l'aggiornamento dei dati di
 * mercato.
 */
public class DailyMarketDataUpdateScheduler extends BaseUpdateScheduler<DailyMarketData> {

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
    public DailyMarketDataUpdateScheduler(String coin, Duration updateInterval) {
        super("Daily market data for " + coin, updateInterval);
        this.coin = coin;
    }

    /**
     * Crea il task responsabile del fetch dei dati di mercato.
     * 
     * @return una {@link Task} che esegue la chiamata a
     *         {@link MarketClient#getDailyMarketData(String)}
     *         e restituisce un oggetto {@link DailyMarketData}
     */
    @Override
    protected Task<DailyMarketData> createTask() {
        return new Task<>() {
            /**
             * Metodo chiamato in background per recuperare i dati.
             * 
             * @return i dati di mercato giornalieri per la "coin"
             * @throws IOException           se durante la chiamata al server si verifica un
             *                               errore
             * @throws IllegalStateException se il client restituisce dati null
             */
            @Override
            protected DailyMarketData call() throws IOException, IllegalStateException {
                System.out.println("Fetching daily market data for " + coin);
                return Optional
                        .ofNullable(MarketClient.getDailyMarketData(coin))
                        .orElseThrow(
                                () -> new IllegalStateException("Null data from MarketClient for Daily Market Data"));
            }
        };
    }
}