package client.view.components.ui;

import client.view.theme.Theme;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PriceLabel extends Label {

    public PriceLabel(String price) {
        super(price);
        setTextFill(Theme.COLOR.TEXT_PRIMARY);
        setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 20));
    }

    public void updatePrice(String priceText, double change) {
        setText(priceText);
        if (change > 0) {
            setTextFill(Theme.COLOR.GREEN);
        } else if (change < 0) {
            setTextFill(Theme.COLOR.RED);
        } else {
            setTextFill(Theme.COLOR.ON_SURFACE); 
        }
    }
}
