// package client.controller.exchangecontroller;
package client.controller.exchangecontroller;

import client.model.service.MarketDataUpdateService;
import client.model.user.User;
import client.view.screen.ExchangeScreen;
import client.view.utils.SceneManager;
import javafx.application.Platform;
import javafx.util.Duration;

public class ExchangeController {

    private final ExchangeScreen exchangeScreen;
    private final User user;
    private MarketDataUpdateService marketDataUpdateService;
    private String coin = "BTC";

    public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user) {
        this.exchangeScreen = exchangeScreen;
        this.user = user;

        initializeMarketUpdates();
    }

    public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user, String coin) {
        this.exchangeScreen = exchangeScreen;
        this.coin = coin;
        this.user = user;

        initializeMarketUpdates();
    }
    private void initializeAccountUpdates(){
        Duration updateInterval = Duration.seconds(1);

    }
    private void initializeMarketUpdates() {
        Duration updateInterval = Duration.seconds(1);

        marketDataUpdateService = new MarketDataUpdateService(coin, updateInterval);

        marketDataUpdateService.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    System.out.println("Updating SubHeader with new data: {}" + newData);
                    exchangeScreen.getSubHeader().updateDisplay(newData);
                });
            }
        });

        marketDataUpdateService.stateProperty().addListener((obs, oldState, newState) -> {
            System.out.println("Market data service state changed to: {}" + newState);
            switch (newState) {
                case READY:
                    break;
                case SCHEDULED:
                    break;
                case RUNNING:
                    break;
                case SUCCEEDED:
                    break;
                case FAILED:
                    Platform.runLater(() -> {
                        exchangeScreen.getSubHeader().updateDisplay(null);
                    });
                    break;
                case CANCELLED:
                    break;
            }
        });

        System.out.println("Starting market data update service for {}" + coin);
        marketDataUpdateService.start();
    }

    /**
     * Call this method to gracefully stop the background updates
     * when this screen or the application is closing.
     */
    public void shutdown() {
        System.out.println("Shutting down market data update service...");
        if (marketDataUpdateService != null && marketDataUpdateService.isRunning()) {
            marketDataUpdateService.cancel();
            System.out.println("Market data update service cancelled.");
        }
    }

    // Keep the getter if needed elsewhere
    public ExchangeScreen getExchangeScreen() {
        return exchangeScreen;
    }
}