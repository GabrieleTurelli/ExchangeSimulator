/**
 * Controller per gestione della schermata di registrazione.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.controller;

import client.model.clients.MarketClient;
import client.model.clients.UserClient;
import client.model.market.OrderRequest;
import client.model.market.OrderSide;
import client.model.market.OrderType;
import client.model.scheduler.DailyMarketDataUpdateScheduler;
import client.model.scheduler.HistoricalMarketUpdateScheduler;
import client.model.scheduler.OrderBookUpdateScheduler;
import client.model.scheduler.WalletUpdateScheduler;
import client.model.user.User;
import client.view.components.layout.TradePanelSection;
import client.view.components.ui.CoinMenu;
import client.view.components.ui.Toast;
import client.view.components.ui.tradepanel.TradePanel;
import client.view.screen.ExchangeScreen;
import client.view.utils.SceneManager;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ExchangeController {

    /**
     * Schermata principale dell'exchange
     */
    private final ExchangeScreen exchangeScreen;


    private final TradePanelSection tradePanelSection;
    /**
     * Utente autenticato che utilizza l'exchange
     */
    private final User user;
    /**
     * Lista di criptovalute disponibili
     */
    private final String[] coins;
    /**
     * Criptovaluta corrente selezionata
     */
    private String currentCoin;
    /**
     * Intervallo di aggiornamento per i scheduler
     */
    private final Duration updateInterval = Duration.seconds(1);

    /** Dichiarazione degli scheduler */
    private DailyMarketDataUpdateScheduler marketDataUpdateScheduler;
    private HistoricalMarketUpdateScheduler historicalMarketUpdateScheduler;
    private WalletUpdateScheduler walletUpdateScheduler;
    private OrderBookUpdateScheduler orderBookUpdateScheduler;

    /**
     * Costruttore del controller.
     * 
     * @param exchangeScreen la schermata di exchange da controllare
     * @param sceneManager   il manager delle scene
     * @param user           l'utente corrente
     */
    public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user) {
        this.exchangeScreen = exchangeScreen;
        this.coins = MarketClient.getCoins();

        // Inizializza il dropdown menu delle coin disponibili
        CoinMenu coinMenu = this.exchangeScreen.getSubHeader().getCoinMenu();
        coinMenu.addCoinChangeListener(newCoin -> changeCoin(newCoin.split("/")[0]));
        coinMenu.addCoins(coins);

        // Inizializza la sezione di trade panel che gestisce l'evento di invio
        // dell'ordine
        // intercetta l'evento di submit
        this.tradePanelSection = this.exchangeScreen.getTradePanelSection();
        tradePanelSection.setOnSubmit(orderRequest -> {
            sendOrder(orderRequest);
        });

        // Inizialmente vene aperta la schermata di exchange con la prima moneta che
        // fornisce il server
        this.currentCoin = coins[0];
        exchangeScreen.setCoin(currentCoin);
        this.user = user;

        // Avvio degli scheduelr per aggiornare tutti i dati in realtime
        initializeMarketUpdates();
        initializeWalletUpdates();
        initializeHistoryUpdates();
        initializeOrderBookUpdates();
    }

    /**
     * Inizializza il servizio di aggiornamento del wallet.
     */
    private void initializeWalletUpdates() {
        walletUpdateScheduler = new WalletUpdateScheduler(updateInterval, user.getUsername());

        walletUpdateScheduler.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    exchangeScreen.getTradePanelSection().updateDisplay(this.currentCoin, newData);
                });
            }
        });

        System.out.println("Starting wallet update service for " + currentCoin);
        walletUpdateScheduler.start();
    }

    /**
     * Inizializza il servizio di aggiornamento dei dati di mercato giornalieri.
     */
    private void initializeMarketUpdates() {
        marketDataUpdateScheduler = new DailyMarketDataUpdateScheduler(currentCoin, updateInterval);

        marketDataUpdateScheduler.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    exchangeScreen.getSubHeader().updateDisplay(newData);
                });
            }
        });

        System.out.println("Starting market data update service for " + currentCoin);
        marketDataUpdateScheduler.start();
    }

    /**
     * Inizializza il servizio di aggiornamento dello storico di mercato.
     */
    private void initializeHistoryUpdates() {
        historicalMarketUpdateScheduler = new HistoricalMarketUpdateScheduler(currentCoin, updateInterval);

        historicalMarketUpdateScheduler.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    exchangeScreen.getChartSection().updateDisplay(newData);
                });
            }
        });

        System.out.println("Starting history data update service for " + currentCoin);
        historicalMarketUpdateScheduler.start();
    }

    /**
     * Inizializza il servizio di aggiornamento dell'order book.
     */
    private void initializeOrderBookUpdates() {
        orderBookUpdateScheduler = new OrderBookUpdateScheduler(currentCoin, updateInterval);

        orderBookUpdateScheduler.lastValueProperty().addListener((obs, oldData, newData) -> {
            if (newData != null) {
                Platform.runLater(() -> {
                    exchangeScreen.getOrderBookSection().updateDisplay(newData);
                });
            }
        });

        System.out.println("Starting order book update service for " + currentCoin);
        orderBookUpdateScheduler.start();
    }

    /**
     * Interrompe tutti i servizi di aggiornamento esistenti.
     */
    public void shutdown() {
        System.out.println("Shutting down update services...");
        if (marketDataUpdateScheduler != null && marketDataUpdateScheduler.isRunning()) {
            marketDataUpdateScheduler.cancel();
            System.out.println("Market data update service cancelled.");
        }
        if (historicalMarketUpdateScheduler != null && historicalMarketUpdateScheduler.isRunning()) {
            historicalMarketUpdateScheduler.cancel();
            System.out.println("History data update service cancelled.");
        }
        if (walletUpdateScheduler != null && walletUpdateScheduler.isRunning()) {
            walletUpdateScheduler.cancel();
            System.out.println("Wallet update service cancelled.");
        }
        if (orderBookUpdateScheduler != null && orderBookUpdateScheduler.isRunning()) {
            orderBookUpdateScheduler.cancel();
            System.out.println("Order book update service cancelled.");
        }
    }

    /**
     * Restituisce la schermata di exchange.
     * 
     * @return l'istanza di ExchangeScreen
     */
    public ExchangeScreen getExchangeScreen() {
        return exchangeScreen;
    }

    /**
     * Cambia la criptovaluta corrente e riavvia i servizi di aggiornamento.
     * 
     * @param coin simbolo della nuova criptovaluta
     */
    public void changeCoin(String coin) {
        shutdown();

        this.currentCoin = coin;
        initializeHistoryUpdates();
        initializeMarketUpdates();
        initializeWalletUpdates();
        initializeOrderBookUpdates();
        tradePanelSection.setCoin(coin);
    }

    /**
     * Invia un ordine al server.
     * 
     * @param orderRequest oggetto {@link OrderRequest} che contiene i dettagli
     * 
     */
    public void sendOrder(OrderRequest orderRequest) {
        try {
            if (orderRequest.getType() == OrderType.LIMIT) {
                throw new UnsupportedOperationException("Limit orders are not supported yet.");
            }

            String response;
            if (orderRequest.getSide() == OrderSide.BUY) {
                response = UserClient.sendBuyMarketOrder(user.getUsername(), orderRequest);
            } else {
                response = UserClient.sendSellMarketOrder(user.getUsername(), orderRequest);
            }
            showToast(response);

        } catch (RuntimeException e) {
            showToast("Error placing order: " + e.getMessage());

        } catch (Exception ex) {
            showToast("Error: " + ex.getMessage());
        }
    }

    /**
     * Restituisce la coin attualmente selezionata.
     * 
     * @return simbolo della moneta corrente
     */
    public String getCoin() {
        return currentCoin;
    }

    private void showToast(String message) {
        Stage stage = (Stage) exchangeScreen.getScene().getWindow();
        Toast.show(stage, message);
    }
}
