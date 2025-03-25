package client.view.components.ui.orderbook;

import java.util.ArrayList;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class OrderBookSide extends VBox {

    public OrderBookSide(ArrayList<OrderBookLevelData> data, Color color) {
        // Find the maximum size for proportionality
        double maxSize = data.stream()
                .mapToDouble(OrderBookLevelData::getSize)
                .max()
                .orElse(1); // Default to 1 if no data

        for (OrderBookLevelData levelData : data) {
            getChildren().add(new OrderBookLevel(levelData, color, maxSize));
        }
    }
}
