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

public class OrderBookSection extends BaseSection {
    private OrderBookData bidData;
    private OrderBookData askData;

    /** Primary ctor */
    public OrderBookSection(GridPane gridPane,
            double widthMultiplier,
            double heightMultiplier,
            OrderBookData bid,
            OrderBookData ask) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);

        this.bidData = bid;
        this.askData = ask;

        initUi();
    }

    /** Convenience ctor */
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

    /** (Re)builds the full content directly into this StackPane */
    private void initUi() {
        // Clear any previous children (if you ever re-init)
        getChildren().removeIf(node -> !(node instanceof javafx.scene.shape.Rectangle));

        VBox container = new VBox();

        // — Header —
        Label header = new Label("Order Book");
        header.setTextFill(Theme.COLOR.ON_BACKGROUND);
        header.setFont(Theme.FONT_STYLE.BODY);
        header.setPadding(new Insets(10));
        container.getChildren().add(header);

        // — Column titles —
        GridPane titles = new GridPane();
        titles.setPadding(new Insets(10));
        Label priceTitle = new Label("Price (USDT)");
        Label sizeTitle = new Label("Size (BTC)");
        Label sumTitle = new Label("Sum (BTC)");
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

        // — Order book UI —
        container.getChildren().add(new OrderBook(askData, bidData));

        // Add it on top of the BaseSection’s Rectangle
        getChildren().add(container);
    }

    private ColumnConstraints createColumnConstraints(double pct) {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(pct);
        return cc;
    }

    public void updateDisplay(OrderBookData newData) {
        String coin = newData.getCoin();
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
