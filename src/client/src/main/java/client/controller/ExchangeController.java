package client.controller;

import client.model.clients.MarketClient;
import client.model.scheduler.DailyMarketDataUpdateScheduler;
import client.model.scheduler.HistoricalMarketUpdateScheduler;
import client.model.scheduler.OrderBookUpdateScheduler;
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
    private final String[] coins;
    private String currentCoin;
    private final Duration updateInterval = Duration.seconds(1);

    private DailyMarketDataUpdateScheduler marketDataUpdateScheduler;
    private HistoricalMarketUpdateScheduler historicalMarketUpdateScheduler;
    private WalletUpdateScheduler walletUpdateScheduler;
    private OrderBookUpdateScheduler orderBookUpdateScheduler;

    // public ExchangeController(ExchangeScreen exchangeScreen, SceneManager
    // sceneManager, User user) {
    // this.exchangeScreen = exchangeScreen;
    // this.exchangeScreen.getSubHeader().getCoinMenu().addCoinChangeListener(newCoin
    // -> changeCoin(newCoin.split("/")[0]));
    // // this.sceneManager = sceneManager;
    // this.user = user;

    // System.out.println("Starting market updates");
    // initializeMarketUpdates();
    // System.out.println("Starting wallet updates");
    // initializeWalletUpdates();
    // System.out.println("Starting history updates");
    // initializeHistoryUpdates();
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
        initializeOrderBookUpdates();
    }

    private void initializeWalletUpdates() {
        walletUpdateScheduler = new WalletUpdateScheduler(updateInterval, user.getUsername());

        walletUpdateScheduler.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    // System.out.println("Updating tradepanel with new data: " + newData);
                    exchangeScreen.getTradePanelSection().updateDisplay(this.currentCoin, newData);
                });
            }
        });

        System.out.println("Starting market data update service for {}" + currentCoin);
        walletUpdateScheduler.start();
    }

    private void initializeMarketUpdates() {
        marketDataUpdateScheduler = new DailyMarketDataUpdateScheduler(currentCoin, updateInterval);

        marketDataUpdateScheduler.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    // System.out.println("Updating SubHeader with new data: " + newData);
                    exchangeScreen.getSubHeader().updateDisplay(newData);
                });
            }
        });

        System.out.println("Starting market data update service for {}" + currentCoin);
        marketDataUpdateScheduler.start();
    }

    private void initializeHistoryUpdates() {
        historicalMarketUpdateScheduler = new HistoricalMarketUpdateScheduler(currentCoin, updateInterval);

        historicalMarketUpdateScheduler.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    // System.out.println("Updating chart with new data: " + newData);
                    exchangeScreen.getChartSection().updateDisplay(newData);
                });
            }
        });

        System.out.println("Starting history data update service for {}" + currentCoin);
        historicalMarketUpdateScheduler.start();
    }

    private void initializeOrderBookUpdates() {
        orderBookUpdateScheduler = new OrderBookUpdateScheduler(currentCoin, updateInterval);

        orderBookUpdateScheduler.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    // System.out.println("Updating chart with new data: " + newData);
                    exchangeScreen.getOrderBookSection().updateDisplay(newData);
                });
            }
        });

        System.out.println("Starting history data update service for {}" + currentCoin);
        orderBookUpdateScheduler.start();
    }

    public void shutdown() {
        System.out.println("Shutting down market data update service...");
        if (marketDataUpdateScheduler != null && marketDataUpdateScheduler.isRunning()) {
            marketDataUpdateScheduler.cancel();
            System.out.println("Market data update service cancelled.");
        }
        if (historicalMarketUpdateScheduler != null && historicalMarketUpdateScheduler.isRunning()) {
            historicalMarketUpdateScheduler.cancel();
            System.out.println("Market data update service cancelled.");
        }
        if (walletUpdateScheduler != null && walletUpdateScheduler.isRunning()) {
            walletUpdateScheduler.cancel();
            System.out.println("Market data update service cancelled.");
        }
        if (orderBookUpdateScheduler != null && orderBookUpdateScheduler.isRunning()) {
            orderBookUpdateScheduler.cancel();
            System.out.println("Market data update service cancelled.");
        }

    }

    public ExchangeScreen getExchangeScreen() {
        return exchangeScreen;
    }

    public void changeCoin(String coin) {
        shutdown();

        this.currentCoin = coin;
        initializeHistoryUpdates();
        initializeMarketUpdates();
        initializeWalletUpdates();
        initializeOrderBookUpdates();

    }

    public String getCoin() {
        return currentCoin;
    }
}