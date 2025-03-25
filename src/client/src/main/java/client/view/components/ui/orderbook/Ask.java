package client.view.components.ui.orderbook;
import java.util.ArrayList;


import client.view.theme.Theme;

public class Ask extends OrderBookSide{

    public Ask(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.RED);
    }
}
