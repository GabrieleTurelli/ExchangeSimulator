package client.model.scheduler;

import client.model.clients.MarketClient;
import client.model.market.DailyMarketData;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class DailyMarketDataUpdateScheduler extends BaseUpdateScheduler<DailyMarketData> {

    private final String coin;

    public DailyMarketDataUpdateScheduler(String coin, Duration updateInterval) {
        super("Daily market data for " + coin, updateInterval);
        this.coin = coin;
    }

    @Override
    protected Task<DailyMarketData> createTask() {
        return new Task<>() {
            @Override
            protected DailyMarketData call() throws Exception {
                System.out.println("Fetching daily market data for " + coin);
                try {
                    DailyMarketData data = MarketClient.getDailyMarketData(coin);
                    if (data == null) {
                        throw new Exception("Null data from MarketClient for " + coin);
                    }
                    updateMessage("Last update: " + java.time.LocalTime.now());
                    return data;
                } catch (Exception e) {
                    updateMessage("Error fetching data: " + e.getMessage());
                    throw e;
                }
            }
        };
    }
}
