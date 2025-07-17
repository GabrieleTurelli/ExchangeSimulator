package client.view.components.layout;

import java.util.List;

import client.model.market.OrderBookData;
import client.model.market.OrderBookLevelData;
import client.view.components.ui.orderbook.OrderBook;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Sezione per la visualizzazione dell'Order Book di una determinata moneta.
 * 
 * @author Gabriele Turelli
 * @version 1.0
 */
public class OrderBookSection extends BaseSection {
    private OrderBookData bidData;
    private OrderBookData askData;
    private String coin;

    /**
     * Costruttore primario.
     * 
     * @param gridPane         il {@link GridPane} cui legare le dimensioni della
     *                         sezione
     * @param widthMultiplier  fattore moltiplicativo applicato alla larghezza del
     *                         {@code gridPane}
     * @param heightMultiplier fattore moltiplicativo applicato all'altezza del
     *                         {@code gridPane}
     * @param bid              dati iniziali degli ordini di tipo bid
     * @param ask              dati iniziali degli ordini di tipo ask
     */
    public OrderBookSection(GridPane gridPane,
            double widthMultiplier,
            double heightMultiplier,
            OrderBookData bid,
            OrderBookData ask) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);

        this.bidData = bid;
        this.askData = ask;
        this.coin = bid.getCoin();

        initUi();
    }

    /**
     * Costruttore di comodità che inizializza bid e ask vuoti per una moneta.
     * 
     * @param gridPane         il {@link GridPane} cui legare le dimensioni della
     *                         sezione
     * @param widthMultiplier  fattore moltiplicativo applicato alla larghezza del
     *                         {@code gridPane}
     * @param heightMultiplier fattore moltiplicativo applicato all'altezza del
     *                         {@code gridPane}
     * @param coin             il simbolo della moneta di cui mostrare l'order book
     */
    public OrderBookSection(GridPane gridPane,
            double widthMultiplier,
            double heightMultiplier,
            String coin) {
        this(gridPane,
                widthMultiplier,
                heightMultiplier,
                new OrderBookData(coin),
                new OrderBookData(coin));
    }

    /**
     * Inizializza
     */
    private void initUi() {
        // rimuove l'orderbook esistente se presente in modo da ricostruirlo con i nuovi dati
        getChildren().removeIf(node -> !(node instanceof javafx.scene.shape.Rectangle));

        VBox container = new VBox();

        Label header = new Label("Order Book");
        header.setTextFill(Theme.COLOR.ON_BACKGROUND);
        header.setFont(Theme.FONT_STYLE.BODY);
        header.setPadding(new Insets(10));
        container.getChildren().add(header);

        GridPane titles = new GridPane();
        titles.setPadding(new Insets(10));
        Label priceTitle = new Label("Price (USDT)");
        Label sizeTitle = new Label(String.format("Size (%s)", coin));
        Label sumTitle = new Label(String.format("Sum (%s)", coin));
        for (Label lbl : List.of(priceTitle, sizeTitle, sumTitle)) {
            lbl.setFont(Theme.FONT_STYLE.CAPTION);
            lbl.setTextFill(Theme.COLOR.ON_BACKGROUND.darker());
        }
        titles.add(priceTitle, 0, 0);
        titles.add(sizeTitle, 1, 0);
        titles.add(sumTitle, 2, 0);
        titles.getColumnConstraints().addAll(
                createColumnConstraints(33),
                createColumnConstraints(33),
                createColumnConstraints(34));
        container.getChildren().add(titles);

        container.getChildren().add(new OrderBook(askData, bidData));

        getChildren().add(container);
    }

    /**
     * Crea e configura i vincoli di colonna per il {@link GridPane} dei titoli.
     * 
     * @param pct percentuale di larghezza della colonna sul totale (0-100)
     * @return i {@link ColumnConstraints} con la percentuale impostata
     */
    private ColumnConstraints createColumnConstraints(double pct) {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(pct);
        return cc;
    }

    /**
     * Aggiorna i dati dell'order book e ricostruisce la UI.
     * Divide i livelli di ordine in bid e ask, memorizza i nuovi dati e richiama
     * {@link #initUi()} per rigenerare il contenuto.
     * 
     * @param newData i dati aggiornati dell'order book
     */
    public void updateDisplay(OrderBookData newData) {
        coin = newData.getCoin();
        OrderBookData bids = new OrderBookData(coin);
        OrderBookData asks = new OrderBookData(coin);

        for (OrderBookLevelData lvl : newData) {
            if (lvl.isBid())
                bids.add(lvl);
            else
                asks.add(lvl);
        }
        bidData = bids;
        askData = asks;

        initUi();
    }
}
