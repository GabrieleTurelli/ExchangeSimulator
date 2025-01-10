package view.components.ui.orderbook;

import view.theme.Theme;

import java.util.ArrayList;

public class Bid extends OrderBookSide{

    public Bid(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.GREEN);
    }
}
