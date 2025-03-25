package client.view.components.ui.orderbook;

import java.util.ArrayList;

import client.view.theme.Theme;

public class Bid extends OrderBookSide{

    public Bid(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.GREEN);
    }
}
