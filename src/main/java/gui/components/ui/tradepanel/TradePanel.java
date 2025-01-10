package main.java.gui.components.ui.tradepanel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.gui.components.ui.TextToggleButton;
import main.java.gui.theme.Theme;

public class TradePanel extends VBox {
    public TradePanel() {
        super();

        HBox orderTypeContainer = new HBox();
        TextToggleButton marketButton = new TextToggleButton("Market", Theme.HEX_COLOR.ON_SURFACE, Theme.HEX_COLOR.PRIMARY);
        marketButton.toggle();
        TextToggleButton limitButton = new TextToggleButton("Limit", Theme.HEX_COLOR.ON_SURFACE, Theme.HEX_COLOR.PRIMARY);
        orderTypeContainer.setAlignment(Pos.CENTER);
        orderTypeContainer.setPadding(new Insets(5,0,20,0));
        orderTypeContainer.getChildren().addAll(marketButton, limitButton);

        getChildren().add(orderTypeContainer);
    }
}

