package view.components.ui.orderbook;

import java.util.ArrayList;

import view.theme.Theme;

public class Ask extends OrderBookSide{

    public Ask(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.RED);
    }
}
