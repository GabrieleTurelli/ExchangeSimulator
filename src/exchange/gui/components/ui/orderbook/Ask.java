package gui.components.ui.orderbook;

import java.util.ArrayList;

import gui.theme.Theme;

public class Ask extends OrderBookSide{

    public Ask(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.RED);
    }
}
