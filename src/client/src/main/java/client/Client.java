package client;

import java.util.ArrayList;

import client.view.components.layout.BaseSection;
import client.view.components.layout.ChartSection;
import client.view.components.layout.HeaderSection;
import client.view.components.layout.OrderBookSection;
import client.view.components.layout.SubHeaderSection;
import client.view.components.ui.orderbook.OrderBookLevelData;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0));
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(60);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(5);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(5);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(60);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(30);
        gridPane.getRowConstraints().addAll(row1, row2, row3, row4);


        HeaderSection header = new HeaderSection(
               gridPane,
                "/logo.png",
               "Exchange Simulator",
               1.0,
               0.1);
        SubHeaderSection subHeader = new SubHeaderSection(
                gridPane,
                1.0,
                0.05);

        ChartSection chartSection = new ChartSection(
                gridPane,
                0.6,
                0.6
        );
//        Section chartSection = new Section(gridPane, 0.6, 0.6);

//        BaseSection orderBookSection = new BaseSection(gridPane, 0.2, 0.9);

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
        OrderBookSection orderBookSection = new OrderBookSection(gridPane, 0.2, 0.9, bid, ask);
//        BaseSection orderSection = new BaseSection(gridPane, Theme.COLOR.SURFACE,0.2, 0.9);
        // TradePanelSection tradePanelSection = new TradePanelSection(gridPane, 0.2, 0.9);
        BaseSection positionSection = new BaseSection(gridPane, 0.6, 0.3);

        gridPane.add(header, 0, 0, 3, 1);
        gridPane.add(subHeader, 0, 1, 3, 1);
        gridPane.add(chartSection, 0, 2, 1, 1);
        gridPane.add(positionSection, 0, 3, 1, 1);
        gridPane.add(orderBookSection, 1, 2, 1, 2);
        // gridPane.add(tradePanelSection, 2, 2, 1, 2);

        Scene scene = new Scene(gridPane, 1280, 720 );
        stage.setTitle("Exchange Simulator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
