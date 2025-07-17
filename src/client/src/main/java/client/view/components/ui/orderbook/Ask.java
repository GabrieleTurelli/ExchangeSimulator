package client.view.components.ui.orderbook;


import client.model.market.OrderBookData;
import client.view.theme.Theme;

public class Ask extends OrderBookSide{

    public Ask(OrderBookData orderBookdata){
        super(orderBookdata, Theme.COLOR.RED, false);
    }
}
