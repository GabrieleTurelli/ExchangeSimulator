package gui.components.ui.orderbook;

import gui.theme.Theme;

import java.util.ArrayList;

public class Ask extends OrderBookSide{

    public Ask(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.RED);
    }
}
