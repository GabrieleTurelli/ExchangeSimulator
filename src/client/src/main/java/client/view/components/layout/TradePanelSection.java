package client.view.components.layout;

import java.util.function.Consumer;

import client.model.market.OrderRequest;
import client.model.market.OrderSide;
import client.model.market.OrderType;
import client.model.user.Wallet;
import client.view.components.ui.tradepanel.AvailableWallet;
import client.view.components.ui.tradepanel.OrderEntry;
import client.view.components.ui.tradepanel.OrderSideEntry;
import client.view.components.ui.tradepanel.ToggleOrderMode;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Sezione dell'interfaccia per l'inserimento degli ordini di trading.
 * Estende {@link BaseSection} per fornire un background dinamico in base alle
 * proporzioni del {@link GridPane} genitore. Contiene componenti per la scelta
 * della modalità ordine (limit/market), l'immissione di quantità e prezzo,
 * selezione del lato (buy/sell) e visualizzazione del wallet disponibile.
 * 
 * @author Gabriele Turelli
 * @version 1.0
 */
public class TradePanelSection extends BaseSection {
    private String coin;
    private final VBox vbox;
    private OrderEntry orderEntry;
    private final ToggleOrderMode toggleOrderMode;
    private final OrderSideEntry orderSideEntry;
    private final AvailableWallet availableWallet;
    private Consumer<OrderRequest> onSubmit;

    /**
     * 
     * @param gridPane         il {@link GridPane} cui legare le dimensioni della
     *                         sezione
     * @param widthMultiplier  fattore moltiplicativo applicato alla larghezza del
     *                         {@code gridPane}
     * @param heightMultiplier fattore moltiplicativo applicato all'altezza del
     *                         {@code gridPane}
     * @param coin             il simbolo della moneta (es. "BTC") utilizzata per
     *                         gli ordini
     * @param usdtAvailable    quantità disponibile di USDT nel wallet
     * @param coinAvailable    quantità disponibile della moneta specificata nel
     *                         wallet
     */
    public TradePanelSection(GridPane gridPane, double widthMultiplier, double heightMultiplier, String coin,
            double usdtAvailable, double coinAvailable) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);

        this.vbox = new VBox();
        this.coin = coin;

        // Toggle tra ordine limit e market
        this.toggleOrderMode = new ToggleOrderMode();

        // Entry iniziale basata sulla modalità di default
        this.orderEntry = new OrderEntry(toggleOrderMode.isLimit());

        // Selettore lato ordine e callback per trigger
        this.orderSideEntry = new OrderSideEntry();
        this.orderSideEntry.setOnBuy(() -> triggerOrder(OrderSide.BUY));
        this.orderSideEntry.setOnSell(() -> triggerOrder(OrderSide.SELL));

        // Display del saldo disponibile
        this.availableWallet = new AvailableWallet(coin, usdtAvailable, coinAvailable);

        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10));

        vbox.getChildren().addAll(toggleOrderMode, orderEntry, orderSideEntry, availableWallet);
        this.toggleOrderMode.setOnToggleChange(this::rerenderOrderEntry);

        this.getChildren().add(vbox);
    }

    /**
     * Ricrea la sezione di immissione ordine quando cambia la modalità
     * (limit/market).
     * <p>
     * Rimuove i componenti relativi all'ordine corrente e ne istanzia uno nuovo
     * basato sul valore {@code isLimit}.
     * </p>
     * 
     * @param isLimit {@code true} per modalità Limit, {@code false} per Market
     */
    private void rerenderOrderEntry(boolean isLimit) {
        vbox.getChildren().removeAll(orderEntry, orderSideEntry, availableWallet);
        orderEntry = new OrderEntry(isLimit);
        vbox.getChildren().addAll(orderEntry, orderSideEntry, availableWallet);
    }

    /**
     * Aggiorna la visualizzazione del wallet disponibile.
     * <p>
     * Imposta i valori correnti di saldo per la moneta e USDT prese dal
     * {@link Wallet} fornito.
     * </p>
     * 
     * @param coin   il simbolo della moneta da aggiornare nel widget
     * @param wallet l'istanza di {@link Wallet} contenente i saldi
     */
    public void updateDisplay(String coin, Wallet wallet) {
        if (wallet != null) {
            availableWallet.setAvailableWallet(coin, wallet.getCoin(coin), wallet.getCoin("USDT"));
        }
    }

    /**
     * Crea e invia la richiesta di ordine basata sui dati attuali.
     * <p>
     * Legge il tipo (limit/market), il lato (buy/sell), la quantità e il prezzo
     * dalla UI, costruisce un {@link OrderRequest} e lo passa al callback
     * {@code onSubmit} se presente.
     * </p>
     * 
     * @param orderSide il lato dell'ordine ({@link OrderSide#BUY} o
     *                  {@link OrderSide#SELL})
     */
    private void triggerOrder(OrderSide orderSide) {
        boolean isLimit = toggleOrderMode.isLimit();
        OrderType orderType = isLimit ? OrderType.LIMIT : OrderType.MARKET;
        double amount = orderEntry.getAmount();
        double price = orderEntry.getPrice();

        OrderRequest request = new OrderRequest(this.coin, orderType, orderSide, amount, price);
        if (onSubmit != null) {
            onSubmit.accept(request);
        }
    }

    /**
     * Registra la funziona che verrà invocata al submit dell'ordine.
     * 
     * @param callback un {@link Consumer} che riceve l'oggetto {@link OrderRequest}
     */
    public void setOnSubmit(Consumer<OrderRequest> callback) {
        this.onSubmit = callback;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}