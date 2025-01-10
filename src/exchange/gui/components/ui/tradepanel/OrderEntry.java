package gui.components.ui.tradepanel;

import gui.components.ui.HorizontalSeparator;
import gui.components.ui.InputEntry;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class OrderEntry extends VBox {
    public OrderEntry(boolean isLimit){
        super();
        setSpacing(10);
        InputEntry sizeEntry = new InputEntry("Size", "enter size");

        if (isLimit) {
            InputEntry priceEntry = new InputEntry("Price", "enter price");
            getChildren().addAll(priceEntry, sizeEntry);
        }else{
            getChildren().add(sizeEntry);
        }
        HorizontalSeparator separator = new HorizontalSeparator(235);
        setMargin(separator, new Insets(10,0,10,0));
        getChildren().add(separator);
    }

}
