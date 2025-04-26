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
    private String coin;

    /** Primary ctor */
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

    private void initUi() {
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

    private ColumnConstraints createColumnConstraints(double pct) {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(pct);
        return cc;
    }

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
