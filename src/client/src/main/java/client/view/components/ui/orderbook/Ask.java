package client.view.components.ui.orderbook;
import java.util.ArrayList;


import client.view.theme.Theme;
import client.model.market.OrderBookData;

public class Ask extends OrderBookSide{

    public Ask(OrderBookData orderBookdata){
        super(orderBookdata, Theme.COLOR.RED);
    }
}
