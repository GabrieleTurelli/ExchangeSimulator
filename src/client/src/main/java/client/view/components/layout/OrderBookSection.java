package client.view.components.layout;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import client.view.components.ui.orderbook.OrderBook;
import client.view.theme.Theme;
import client.model.market.OrderBookLevelData;
import client.model.market.OrderBookData;

import java.util.ArrayList;


public class OrderBookSection extends BaseSection {
    private ArrayList<OrderBookLevelData> levelData = new ArrayList<OrderBookLevelData>();

    public OrderBookSection(GridPane gridPane, double widthMultiplier, double heightMultiplier,
            OrderBookData bid, OrderBookData ask) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);

        VBox container = new VBox();
        Label orderBookLabel = new Label("Order Book");
        orderBookLabel.setTextFill(Theme.COLOR.ON_BACKGROUND);
        orderBookLabel.setFont(Theme.FONT_STYLE.BODY);
        orderBookLabel.setPadding(new Insets(10, 10, 10, 10));
        container.getChildren().add(orderBookLabel);

        GridPane itemsLabel = new GridPane();
        Label priceItemLabel = new Label("Price(USDT)");
        priceItemLabel.setTextFill(Theme.COLOR.ON_BACKGROUND);
        priceItemLabel.setFont(Theme.FONT_STYLE.CAPTION);
        priceItemLabel.setTextFill(Theme.COLOR.ON_BACKGROUND.darker());

        Label sizeItemLabebl = new Label("Size(BTC)");
        sizeItemLabebl.setTextFill(Theme.COLOR.ON_BACKGROUND);
        sizeItemLabebl.setFont(Theme.FONT_STYLE.CAPTION);
        sizeItemLabebl.setTextFill(Theme.COLOR.ON_BACKGROUND.darker());

        Label SumItemLabels = new Label("Sum(BTC)");
        SumItemLabels.setTextFill(Theme.COLOR.ON_BACKGROUND);
        SumItemLabels.setFont(Theme.FONT_STYLE.CAPTION);
        SumItemLabels.setTextFill(Theme.COLOR.ON_BACKGROUND.darker());

        itemsLabel.setPadding(new Insets(10, 10, 10, 10));
        itemsLabel.add(priceItemLabel, 0, 0);
        itemsLabel.add(sizeItemLabebl, 1, 0);
        itemsLabel.add(SumItemLabels, 2, 0);
        itemsLabel.getColumnConstraints().addAll(
                createColumnConstraints(33),
                createColumnConstraints(33),
                createColumnConstraints(34));
        container.getChildren().add(itemsLabel);

        OrderBook orderBook = new OrderBook(bid, ask);
        container.getChildren().add(orderBook);
        getChildren().addAll(container);
    }

    private ColumnConstraints createColumnConstraints(double percentWidth) {
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setPercentWidth(percentWidth);
        return constraints;
    }
}
