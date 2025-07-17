package client.view.components.ui;

import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class NumericInputEntry extends HBox {

    public NumericInputEntry(String labelText, String placeholder) {
        super();
        setSpacing(20);
        setPadding(new Insets(10));

        setBackground(new Background(new BackgroundFill(Theme.COLOR.SURFACE, new CornerRadii(5), Insets.EMPTY)));

        Label label = new Label(labelText);
        label.setFont(Theme.FONT_STYLE.SUBTITLE);
        label.setTextFill(Theme.COLOR.ON_SURFACE);
        label.setAlignment(Pos.CENTER_LEFT);

        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.setOnMouseEntered(event -> textField.setCursor(Cursor.TEXT));
        textField.setOnMouseExited(event -> textField.setCursor(Cursor.DEFAULT));

        textField.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-prompt-text-fill: " + Theme.HEX_COLOR.ON_SURFACE + ";");
        textField.setAlignment(Pos.CENTER_RIGHT);

        // Filtro per accettare solo numeri e un punto decimale 
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String ch = event.getCharacter();
            // Se non è cifra né punto, blocca
            if (!ch.matches("\\d") && !ch.equals(".")) {
                event.consume();
            }
            // Se è punto ma il testo già lo contiene, blocca
            else if (ch.equals(".") && textField.getText().contains(".")) {
                event.consume();
            }
        });

        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(label, textField);
    }

    public double getValue() {
        TextField textField = (TextField) getChildren().get(1);
        String text = textField.getText();
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
