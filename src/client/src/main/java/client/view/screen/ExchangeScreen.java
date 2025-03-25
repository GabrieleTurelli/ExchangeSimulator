package client.view.screen;

import java.util.ArrayList;

import client.view.components.layout.BaseSection;
import client.view.components.layout.ChartSection;
import client.view.components.layout.HeaderSection;
import client.view.components.layout.OrderBookSection;
import client.view.components.layout.SubHeaderSection;
import client.view.components.layout.TradePanelSection;
import client.view.components.ui.orderbook.OrderBookLevelData;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ExchangeScreen extends GridPane {

    public ExchangeScreen() {
        setPadding(new Insets(0));
        setHgap(0);
        setVgap(0);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(60);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        getColumnConstraints().addAll(column1, column2, column3);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(5);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(5);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(60);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(30);
        getRowConstraints().addAll(row1, row2, row3, row4);

        HeaderSection header = new HeaderSection(
                this,
                "/logo.png",
                "Exchange Simulator",
                1.0,
                0.1);
        SubHeaderSection subHeader = new SubHeaderSection(
                this,
                1.0,
                0.05);

        ChartSection chartSection = new ChartSection(
                this,
                0.6,
                0.6
        );

        ArrayList<OrderBookLevelData> ask = new ArrayList<>();
        ask.add(new OrderBookLevelData(10.4, 234.9));
        ask.add(new OrderBookLevelData(10.3, 204.8));
        ask.add(new OrderBookLevelData(10.2, 134.1));
        ask.add(new OrderBookLevelData(10.1, 34.3));
        ask.add(new OrderBookLevelData(10.0, 4.2));

        ArrayList<OrderBookLevelData> bid = new ArrayList<>();
        bid.add(new OrderBookLevelData(9.9, 34.9));
        bid.add(new OrderBookLevelData(9.8, 104.8));
        bid.add(new OrderBookLevelData(9.7, 234.1));
        bid.add(new OrderBookLevelData(9.6, 334.3));
        bid.add(new OrderBookLevelData(9.5, 524.2));
        OrderBookSection orderBookSection = new OrderBookSection(this, 0.2, 0.9, bid, ask);
        TradePanelSection tradePanelSection = new TradePanelSection(this, 0.2, 0.9);
        BaseSection positionSection = new BaseSection(this, 0.6, 0.3);

        add(header, 0, 0, 3, 1);
        add(subHeader, 0, 1, 3, 1);
        add(chartSection, 0, 2, 1, 1);
        add(positionSection, 0, 3, 1, 1);
        add(orderBookSection, 1, 2, 1, 2);
        add(tradePanelSection, 2, 2, 1, 2);
    }
}
