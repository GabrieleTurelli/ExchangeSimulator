package client.model.service;

import client.model.clients.MarketClient;
import client.model.market.KlineHistory;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class HistoricalMarketUpdateService extends BaseUpdateService<KlineHistory> {

    private final String coin;

    public HistoricalMarketUpdateService(String coin, Duration updateInterval) {
        super("Market history for " + coin, updateInterval);
        this.coin = coin;
    }

    @Override
    protected Task<KlineHistory> createTask() {
        return new Task<>() {
            @Override
            protected KlineHistory call() throws Exception {
                System.out.println("Fetching historical data for " + coin);
                try {
                    KlineHistory data = MarketClient.getHistory(coin);
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
