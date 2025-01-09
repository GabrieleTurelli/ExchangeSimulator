package gui.components.ui.orderbook;

import gui.theme.Theme;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Bid extends OrderBookSide{

    public Bid(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.GREEN);
    }
}
