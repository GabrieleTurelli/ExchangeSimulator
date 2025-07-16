/**
 * Scheduler per l'aggiornamento periodico dei dati dell'order book
 * per aggiornare l'orderbook.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.scheduler;

import java.io.IOException;
import java.util.Optional;

import client.model.clients.MarketClient;
import client.model.market.OrderBookData;
import javafx.concurrent.Task;
import javafx.util.Duration;

/*  
 *  Estende {@link BaseUpdateScheduler} per eseguire l'aggiornamento dei dati
 *  dell'order book.
 */
public class OrderBookUpdateScheduler extends BaseUpdateScheduler<OrderBookData> {
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
    public OrderBookUpdateScheduler(String coin, Duration updateInterval) {
        super("Order book update for " + coin, updateInterval);
        this.coin = coin;

    }

    /**
     * Crea il task responsabile del fetch dei dati dell'order book.
     * 
     * @return una {@link Task} che esegue la chiamata a
     *         {@link MarketClient#getOrderBook(String)}
     *         e restituisce un oggetto {@link OrderBookData}
     */
    @Override
    protected Task<OrderBookData> createTask() {
        return new Task<>() {
            /**
             * Metodo chiamato in background per recuperare i dati dell'order book.
             * 
             * @return i dati dell'order book per la "coin"
             * @throws IOException           durante la chiamata al server si verifica un
             *                               errore
             * @throws IllegalStateException se il client restituisce dati null
             */
            @Override
            protected OrderBookData call() throws IllegalStateException, IOException {
                System.out.println("Fetching order book for " + coin);
                return Optional
                        .ofNullable(MarketClient.getOrderBook(coin))
                        .orElseThrow(() -> new IllegalStateException("Null data from MarketClient for order book"));

            }

        };

    }

}