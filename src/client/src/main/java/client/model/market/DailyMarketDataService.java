package client.model.market;

import client.model.clients.MarketClient;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.io.IOException;

public class DailyMarketDataService extends Service<DailyMarketData> {

    private String coinSymbol;

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    @Override
    protected Task<DailyMarketData> createTask() {
        final String currentCoin = this.coinSymbol;

        return new Task<DailyMarketData>() {
            @Override
            protected DailyMarketData call() throws Exception { 
                if (currentCoin == null || currentCoin.isEmpty()) {
                     throw new IllegalStateException("Coin symbol not set");
                }
                try {
                    System.out.println("Fetching market data for " + currentCoin + " on thread: " + Thread.currentThread().getName());
                    DailyMarketData data = (DailyMarketData) MarketClient.getDailyMarketData(coinSymbol);
                    return data;
                } catch (IOException e) {
                    System.err.println("Failed to fetch market data for " + currentCoin + ": " + e.getMessage());
                    throw e;
                }
            }
        };
    }
}