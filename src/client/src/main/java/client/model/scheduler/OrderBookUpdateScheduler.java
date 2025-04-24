package client.model.scheduler;

import java.util.Optional;

import client.model.clients.MarketClient;
import client.model.market.OrderBookData;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class OrderBookUpdateScheduler extends BaseUpdateScheduler<OrderBookData> {

    private final String coin;

    public OrderBookUpdateScheduler(String coin, Duration updateInterval) {
        super("Order book update for " + coin, updateInterval);
        this.coin = coin;

    }

    @Override
    protected Task<OrderBookData> createTask() {
        return new Task<>() {
            @Override
            protected OrderBookData call() throws Exception {
                System.out.println("Fetching order book for " + coin);
                try {
                    OrderBookData orderBook = Optional
                            .ofNullable(MarketClient.getOrderBook(coin))
                            .orElseThrow(() -> new Exception("Null data from MarketClient for order book"));
                    updateMessage("Last update: " + java.time.LocalTime.now());
                    return orderBook;
                } catch (Exception e) {
                    updateMessage("Error fetching data: " + e.getMessage());
                    throw e;
                }

            }

        };

    }

}