package client.view.components.ui.tradepanel;

import client.view.theme.Theme;
import javafx.scene.control.Button;

class OrderSideButton extends Button {

    public OrderSideButton(String text, String initialColor, String hoverColor) {
        super(text);

        setTextFill(Theme.COLOR.ON_BACKGROUND);
        setFont(Theme.FONT_STYLE.SUBTITLE);
        setMinHeight(40);
        setMaxHeight(40);
        setMaxWidth(Double.MAX_VALUE);

        setStyle(getButtonStyle(initialColor));

        setOnMouseEntered(e -> setStyle(getButtonStyle(hoverColor)));
        setOnMouseExited(e -> setStyle(getButtonStyle(initialColor)));
    }

    private String getButtonStyle(String backgroundColor) {
        return "-fx-cursor: hand;" +
                "-fx-background-color: " + backgroundColor + ";" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16px;" +
                "-fx-padding: 5 10;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;";
    }

}