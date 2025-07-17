package client.view.components.ui.orderbook;

import client.model.market.OrderBookData;
import client.view.theme.Theme;

public class Bid extends OrderBookSide{

    public Bid(OrderBookData data){
        super(data, Theme.COLOR.GREEN, true);
    }
}
