package exchange.gui.components.ui.orderbook;

import java.util.ArrayList;

import exchange.gui.theme.Theme;

public class Ask extends OrderBookSide{

    public Ask(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.RED);
    }
}
