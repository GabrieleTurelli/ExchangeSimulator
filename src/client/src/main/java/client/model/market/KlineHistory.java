/**
 * Classe che rappresenta la cronologia dei dati Kline (candlestick) nel mercato.
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.market;

import java.util.ArrayList;

public class KlineHistory extends ArrayList<Kline> {

    public KlineHistory() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Kline kline : this) {
            sb.append(kline.toString()).append("|");
        }
        return sb.toString();

    }

}
