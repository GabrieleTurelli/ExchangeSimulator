package main.java.gui.components.ui.orderbook;

import java.util.ArrayList;

import main.java.gui.theme.Theme;

public class Ask extends OrderBookSide{

    public Ask(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.RED);
    }
}
