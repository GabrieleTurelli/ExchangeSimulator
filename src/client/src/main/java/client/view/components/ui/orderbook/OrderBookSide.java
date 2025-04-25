package client.view.components.ui.orderbook;

import client.model.market.OrderBookData;
import client.model.market.OrderBookLevelData;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class OrderBookSide extends VBox {

    public OrderBookSide(OrderBookData orderBookData, Color color) {
        System.out.println("Orderbookdataaaaaaa");
        System.out.println(orderBookData);
        double maxSize = orderBookData.stream()
                .mapToDouble(OrderBookLevelData::getQuantity)
                .max()
                .orElse(1);
        System.out.println("Max size: " + maxSize);

        for (OrderBookLevelData levelData : orderBookData) {
            getChildren().add(new OrderBookLevel(levelData, color, maxSize));
        }
    }
}
