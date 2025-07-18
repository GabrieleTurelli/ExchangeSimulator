package client.view.components.ui.orderbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import client.model.market.OrderBookData;
import client.model.market.OrderBookLevelData;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class OrderBookSide extends VBox {

    /**
     * Costruttore per visualizzare un lato (ask/bid) dell'OrderBook.
     * 
     * @param orderBookData dati dei livelli dell'order book
     * @param color         colore di riempimento dei livelli
     * @param isBid         true se lato bid (compratori), false se lato ask
     *                      (venditori)
     */
    public OrderBookSide(OrderBookData orderBookData, Color color, boolean isBid) {
        // Calcolo della massima quantità per normalizzazione delle barre
        System.out.println("OrderBookData: " + orderBookData);
        double maxSize = orderBookData.stream()
                .mapToDouble(OrderBookLevelData::getQuantity)
                .max()
                .orElse(1);
        System.out.println("Max size: " + maxSize);

        List<OrderBookLevelData> levels = new ArrayList<>(orderBookData);

        // vengono mostrati solo i primi 10 livelli
        levels = levels.subList(0, Math.min(10, levels.size()));

        if (!isBid) {
            Collections.reverse(levels);
        }

        System.out.println(levels);
        System.out.println(levels.size() + " levels to display");

        // Aggiunta dei livelli alla VBox
        for (OrderBookLevelData levelData : levels) {
            getChildren().add(new OrderBookLevel(levelData, color, maxSize));
        }
    }
}
