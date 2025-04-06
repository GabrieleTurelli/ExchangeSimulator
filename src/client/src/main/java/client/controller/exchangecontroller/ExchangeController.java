// package client.controller.exchangecontroller;
package client.controller.exchangecontroller;

import client.model.service.MarketDataUpdateService;
import client.model.service.WalletUpdateService;
import client.model.user.User;
import client.view.screen.ExchangeScreen;
import client.view.utils.SceneManager;
import javafx.application.Platform;
import javafx.util.Duration;

public class ExchangeController {

    private final ExchangeScreen exchangeScreen;
    private final User user;
    private MarketDataUpdateService marketDataUpdateService;
    private WalletUpdateService walletUpdateService;
    private String coin = "BTC";
    private final Duration updateInterval = Duration.seconds(1);

    public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user) {
        this.exchangeScreen = exchangeScreen;
        this.user = user;

        System.out.println("Starting market updates");
        initializeMarketUpdates();
        // System.out.println("Starting wallet updates");
        // initializeWalletUpdates();
    }

    public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user, String coin) {
        this.exchangeScreen = exchangeScreen;
        this.coin = coin;
        this.user = user;

        initializeMarketUpdates();
        // initializeWalletUpdates();
    }

    private void initializeWalletUpdates() {
        walletUpdateService = new WalletUpdateService(updateInterval, user.getUsername());

        walletUpdateService.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    System.out.println("Updating SubHeader with new data: " + newData);
                    exchangeScreen.getTradePanelSection().updateDisplay(newData);
                });
            }
        });

        walletUpdateService.stateProperty().addListener((obs, oldState, newState) -> {
            System.out.println("Wallet update service state changed to: " + newState);
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
                        exchangeScreen.getTradePanelSection().updateDisplay(null);
                    });
                    break;
                case CANCELLED:
                    break;
            }
        });
        System.out.println("Starting market data update service for {}" + coin);
        walletUpdateService.start();
    }

    private void initializeMarketUpdates() {
        marketDataUpdateService = new MarketDataUpdateService(coin, updateInterval);

        marketDataUpdateService.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    System.out.println("Updating SubHeader with new data: " + newData);
                    exchangeScreen.getSubHeader().updateDisplay(newData);
                });
            }
        });

        marketDataUpdateService.stateProperty().addListener((obs, oldState, newState) -> {
            System.out.println("Market data service state changed to: " + newState);
            switch (newState) {
                case READY:
                    break;
                case SCHEDULED:
                    break;
                case RUNNING:
                    break;
                case SUCCEEDED:
                    Platform.runLater(() -> {
                        exchangeScreen.getSubHeader().updateDisplay(null);
                    });
                    break;
                case FAILED:
                    break;
                case CANCELLED:
                    break;
            }
        });

        System.out.println("Starting market data update service for {}" + coin);
        marketDataUpdateService.start();
    }

    public void shutdown() {
        System.out.println("Shutting down market data update service...");
        if (marketDataUpdateService != null && marketDataUpdateService.isRunning()) {
            marketDataUpdateService.cancel();
            System.out.println("Market data update service cancelled.");
        }

        if (walletUpdateService != null && walletUpdateService.isRunning()) {
            walletUpdateService.cancel();
            System.out.println("Market data update service cancelled.");
        }
    }

    public ExchangeScreen getExchangeScreen() {
        return exchangeScreen;
    }
}