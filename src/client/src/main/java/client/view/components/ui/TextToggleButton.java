package client.view.components.ui;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TextToggleButton extends Button {

    private boolean toggled;
    private final String color;
    private final String activeColor;

    public TextToggleButton(String text, String color, String activeColor) {
        super(text);
        this.color = color;
        this.activeColor = activeColor;
        toggled = false;

        updateStyle();
        setOnMouseEntered(event -> setCursor(Cursor.HAND));
        setOnMouseExited(event -> setCursor(Cursor.DEFAULT));

        setOnAction(event -> {
            toggled = !toggled;
            updateStyle();

        });

        setFont(Font.font("Arial", FontWeight.BOLD, 14));
    }

    private void updateStyle() {
        if (toggled) {
            setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill:  " + activeColor + " ;");
        } else {
            setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: " + color + ";");
        }
    }

    public void toggle() {
        toggled = !toggled;
        updateStyle();
    }
    public void toggle(Boolean status){
        toggled = status;
        updateStyle();

    }
    public boolean isToggled() {
        return toggled;
    }

    public void setCustomFont(String fontFamily, double fontSize, boolean isBold) {
        FontWeight fontWeight = isBold ? FontWeight.BOLD : FontWeight.NORMAL;
        setFont(Font.font(fontFamily, fontWeight, fontSize));
    }
}
