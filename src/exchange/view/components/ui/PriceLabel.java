package view.components.ui;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import view.theme.Theme;

public class PriceLabel extends Label {

    public PriceLabel(String price) {
        super(price);
        setTextFill(Theme.COLOR.GREEN);
        setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 20));
    }
}
