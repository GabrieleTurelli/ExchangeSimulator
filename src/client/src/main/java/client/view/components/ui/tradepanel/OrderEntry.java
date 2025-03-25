package client.view.components.ui.tradepanel;

import client.view.components.ui.HorizontalSeparator;
import client.view.components.ui.NumericInputEntry;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class OrderEntry extends VBox {
    public OrderEntry(boolean isLimit){
        super();
        setSpacing(10);
        NumericInputEntry sizeEntry = new NumericInputEntry("Size", "enter size");

        if (isLimit) {
            NumericInputEntry priceEntry = new NumericInputEntry("Price", "enter price");
            getChildren().addAll(priceEntry, sizeEntry);
        }else{
            getChildren().add(sizeEntry);
        }
        HorizontalSeparator separator = new HorizontalSeparator(235);
        setMargin(separator, new Insets(10,0,10,0));
        getChildren().add(separator);
    }

}
