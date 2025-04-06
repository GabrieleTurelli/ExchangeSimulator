package client.model.service;

import client.model.clients.MarketClient;
import client.model.market.DailyMarketData;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class MarketDataUpdateService extends ScheduledService<DailyMarketData> {

    private final String coin;

    public MarketDataUpdateService(String coin, Duration updateInterval) {
        this.coin = coin;
        setPeriod(updateInterval);
        setRestartOnFailure(true);
    }

    @Override
    protected Task<DailyMarketData> createTask() {
        return new Task<>() {
            @Override
            protected DailyMarketData call() throws Exception {
                System.out.println("Fetching daily market data for" + coin);
                try {
                    DailyMarketData data = MarketClient.getDailyMarketData(coin);
                    if (data == null) {
                        throw new Exception("Received null data from MarketClient for " + coin);
                    }
                    System.out.println("Successfully fetched data: " + data);
                    updateMessage("Last update: " + java.time.LocalTime.now());
                    return data;
                } catch (Exception e) {
                    System.out
                            .println("Failed to fetch market data for :" + coin + "Exception: " + e.getMessage());
                    updateMessage("Error fetching data: " + e.getMessage());
                    throw e;
                }
            }
        };
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("Market data update succeeded for" + coin);
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("Market data update task failed for." + coin + " Exception:" + getException() != null
                ? getException().getMessage()
                : "Unknown");
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        System.out.println("Market data update service cancelled for" + coin);
    }
}