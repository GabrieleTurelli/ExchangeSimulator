package client.view.components.ui.orderbook;


import client.model.market.OrderBookData;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class OrderBook extends VBox {
    public OrderBook (OrderBookData ask, OrderBookData bid){
        Region spacer = new Region();
        spacer.setPrefHeight(30);

        getChildren().add(new Ask(ask));
        getChildren().add(spacer);
        getChildren().add(new Bid(bid));
    }
}
