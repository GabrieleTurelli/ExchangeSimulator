package gui.components.ui.orderbook;

import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class OrderBook extends VBox {
    public OrderBook (ArrayList<OrderBookLevelData> bid, ArrayList<OrderBookLevelData> ask){
        Region spacer = new Region();
        spacer.setPrefHeight(30);
        getChildren().add(new Ask(ask));
        getChildren().add(spacer);
        getChildren().add(new Bid(bid));
    }
}
