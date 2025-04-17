package server.model.market;

import java.util.ArrayList;

public class OrderBook extends ArrayList<OrderBookLevel> {

    public OrderBook() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OrderBookLevel orderBookLevel : this) {
            sb.append(orderBookLevel.toString()).append("|");
        }
        return sb.toString();

    }

}
