package server.model.market;

import java.util.ArrayList;

public class OrderBook extends ArrayList<OrderBookLevel> {
    private final String coin;

    public OrderBook(String coin) {
        super();
        this.coin  = coin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OrderBookLevel orderBookLevel : this) {
            sb.append(orderBookLevel.toString()).append("|");
        }
        return sb.toString();

    }

    public String getCoin(){
        return this.coin;

    }
}
