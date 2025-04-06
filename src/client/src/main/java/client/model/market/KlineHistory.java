package client.model.market;

import java.util.ArrayList;

public class KlineHistory extends ArrayList<Kline> {

    public KlineHistory(){
        super();
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Kline kline : this) {
            sb.append(kline.toString()).append("|");
        }
        return sb.toString();

    }
    
}
