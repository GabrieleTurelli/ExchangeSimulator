package main.java.gui.components.ui.orderbook;

import javafx.scene.layout.VBox;
import main.java.gui.theme.Theme;

import java.util.ArrayList;

public class Bid extends OrderBookSide{

    public Bid(ArrayList<OrderBookLevelData> data){
        super(data, Theme.COLOR.GREEN);
    }
}
