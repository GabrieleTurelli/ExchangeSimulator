/**
 * Classe che rappresenta i dati dell'orderbook.
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.market;

import java.util.ArrayList;

public class OrderBookData extends ArrayList<OrderBookLevelData> {
    private final String coin;

    public OrderBookData(String coin) {
        super();
        this.coin = coin;
    }

    public String getCoin() {
        return this.coin;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OrderBookLevelData orderBookLevel : this) {
            sb.append(orderBookLevel.toString()).append("|");
        }
        return sb.toString();

    }

}
