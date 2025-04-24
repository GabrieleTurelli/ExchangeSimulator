package client.view.components.ui.orderbook;

import client.model.market.OrderBookData;
import client.model.market.OrderBookLevelData;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class OrderBookSide extends VBox {

    public OrderBookSide(OrderBookData orderBookData, Color color) {
        // Find the maximum size for proportionality
        double maxSize = orderBookData.stream()
                .mapToDouble(OrderBookLevelData::getQuantity)
                .max()
                .orElse(1); // Default to 1 if no data

        for (OrderBookLevelData levelData : orderBookData) {
            getChildren().add(new OrderBookLevel(levelData, color, maxSize));
        }
    }
}
