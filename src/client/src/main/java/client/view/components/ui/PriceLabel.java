package client.view.components.ui;

import client.view.theme.Theme;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PriceLabel extends Label {

    public PriceLabel(String price) {
        super(price);
        setTextFill(Theme.COLOR.GREEN);
        setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 20));
    }
}
