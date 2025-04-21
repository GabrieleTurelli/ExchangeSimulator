package client.controller;

import client.model.clients.MarketClient;
import client.model.scheduler.DailyMarketDataUpdateScheduler;
import client.model.scheduler.HistoricalMarketUpdateScheduler;
import client.model.scheduler.WalletUpdateScheduler;
import client.model.user.User;
import client.view.components.ui.CoinMenu;
import client.view.screen.ExchangeScreen;
import client.view.utils.SceneManager;
import javafx.application.Platform;
import javafx.util.Duration;

public class ExchangeController {

    private ExchangeScreen exchangeScreen;
    // private final SceneManager sceneManager;
    private final User user;
    private DailyMarketDataUpdateScheduler marketDataUpdateService;
    private HistoricalMarketUpdateScheduler historicalMarketUpdateService;
    private WalletUpdateScheduler walletUpdateService;
    private final String[] coins;
    private String currentCoin;
    private final Duration updateInterval = Duration.seconds(1);

    // public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user) {
    //     this.exchangeScreen = exchangeScreen;
    //     this.exchangeScreen.getSubHeader().getCoinMenu().addCoinChangeListener(newCoin -> changeCoin(newCoin.split("/")[0]));
    //     // this.sceneManager = sceneManager;
    //     this.user = user;

    //     System.out.println("Starting market updates");
    //     initializeMarketUpdates();
    //     System.out.println("Starting wallet updates");
    //     initializeWalletUpdates();
    //     System.out.println("Starting history updates");
    //     initializeHistoryUpdates();
    // }

    public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user) {
        this.exchangeScreen = exchangeScreen;
        this.coins = MarketClient.getCoins();

        CoinMenu coinMenu = this.exchangeScreen.getSubHeader().getCoinMenu();
        coinMenu.addCoinChangeListener(newCoin -> changeCoin(newCoin.split("/")[0]));
        coinMenu.addCoins(coins);

        this.currentCoin = coins[0];
        this.user = user;

        initializeMarketUpdates();
        initializeWalletUpdates();
        initializeHistoryUpdates();
    }

    private void initializeWalletUpdates() {
        walletUpdateService = new WalletUpdateScheduler(updateInterval, user.getUsername());

        walletUpdateService.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    // System.out.println("Updating tradepanel with new data: " + newData);
                    exchangeScreen.getTradePanelSection().updateDisplay(this.currentCoin, newData);
                });
            }
        });

        // walletUpdateService.stateProperty().addListener((obs, oldState, newState) ->
        // {
        // System.out.println("Wallet update service state changed to: " + newState);
        // switch (newState) {
        // case READY:
        // break;
        // case SCHEDULED:
        // break;
        // case RUNNING:
        // break;
        // case SUCCEEDED:
        // Platform.runLater(() -> {
        // exchangeScreen.getTradePanelSection().updateDisplay(null);
        // });
        // break;
        // case FAILED:
        // break;
        // case CANCELLED:
        // break;
        // }
        // });
        System.out.println("Starting market data update service for {}" + currentCoin);
        walletUpdateService.start();
    }

    private void initializeMarketUpdates() {
        marketDataUpdateService = new DailyMarketDataUpdateScheduler(currentCoin, updateInterval);

        marketDataUpdateService.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    // System.out.println("Updating SubHeader with new data: " + newData);
                    exchangeScreen.getSubHeader().updateDisplay(newData);
                });
            }
        });

        // marketDataUpdateService.stateProperty().addListener((obs, oldState, newState)
        // -> {
        // System.out.println("Market data service state changed to: " + newState);
        // switch (newState) {
        // case READY:
        // break;
        // case SCHEDULED:
        // break;
        // case RUNNING:
        // break;
        // case SUCCEEDED:
        // Platform.runLater(() -> {
        // exchangeScreen.getSubHeader().updateDisplay(null);
        // });
        // break;
        // case FAILED:
        // break;
        // case CANCELLED:
        // break;
        // }
        // });

        System.out.println("Starting market data update service for {}" + currentCoin);
        marketDataUpdateService.start();
    }

    private void initializeHistoryUpdates() {
        historicalMarketUpdateService = new HistoricalMarketUpdateScheduler(currentCoin, updateInterval);

        historicalMarketUpdateService.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    // System.out.println("Updating chart with new data: " + newData);
                    exchangeScreen.getChartSection().updateDisplay(newData);
                });
            }
        });

        // historicalMarketUpdateService.stateProperty().addListener((obs, oldState,
        // newState) -> {
        // System.out.println("History data service state changed to: " + newState);
        // switch (newState) {
        // case READY:
        // break;
        // case SCHEDULED:
        // break;
        // case RUNNING:
        // break;
        // case SUCCEEDED:
        // break;
        // case FAILED:
        // Platform.runLater(() -> {
        // exchangeScreen.getSubHeader().updateDisplay(null);
        // });
        // break;
        // case CANCELLED:
        // break;
        // }
        // });

        System.out.println("Starting history data update service for {}" + currentCoin);
        historicalMarketUpdateService.start();
    }

    public void shutdown() {
        System.out.println("Shutting down market data update service...");
        if (marketDataUpdateService != null && marketDataUpdateService.isRunning()) {
            marketDataUpdateService.cancel();
            System.out.println("Market data update service cancelled.");
        }
        if (historicalMarketUpdateService != null && historicalMarketUpdateService.isRunning()) {
            historicalMarketUpdateService.cancel();
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

    public void changeCoin(String coin) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("STO CAMBIANDO");
        System.out.println(coin);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        shutdown();

        this.currentCoin = coin;

        // this.exchangeScreen = new ExchangeScreen(user, coin);
        // sceneManager.
        

        initializeHistoryUpdates();
        initializeMarketUpdates();
        initializeWalletUpdates();

    }

    public String getCoin() {
        return currentCoin;
    }
}