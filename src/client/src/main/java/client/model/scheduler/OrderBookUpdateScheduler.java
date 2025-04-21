package client.model.scheduler;

import java.time.Duration;
import java.util.ArrayList;

import client.model.clients.MarketClient;
import client.model.market.OrderBook;
import javafx.concurrent.Task;

public class OrderBookUpdateScheduler extends BaseUpdateScheduler<OrderBook> {


    private final String coin;

    public OrderBookUpdateScheduler(String coin, Duration updateInterval){
        // super("Order book update for " + coin, updateInterval);
        super("Order book update for " + coin, updateInterval);
        this.coin = coin;

    } 

    @Override
    protected Task<OrderBook> createTask(){
        return new Task<>(){
            @Override
            protected OrderBook call() throws Exception {
                System.out.println("Fetching order book for " + coin);
                try {
                    OrderBook orderBook = MarketClient.getOrderBook(coin);
                    if (orderBook == null) {
                        throw new Exception("Null data from MarketClient for order book");
                    }
                    updateMessage("Last update: " + java.time.LocalTime.now());
                    return orderBook;
                } catch (Exception e) {
                    updateMessage("Error fetching data: " + e.getMessage());
                    throw e;
                }

                return new OrderBook( coin);
            }


        };

    }
    
}