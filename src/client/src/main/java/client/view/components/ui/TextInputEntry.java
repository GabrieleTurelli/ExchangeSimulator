package client.view.components.ui;

import client.view.theme.Theme;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TextInputEntry extends VBox {
    private final TextField textField;
    private final Label label;

    public TextInputEntry(String labelText) {
        this(labelText, false);
    }

    public TextInputEntry(String labelText, boolean isPasswordField) {
        this.label = new Label(labelText);
        this.label.setTextFill(Theme.COLOR.ON_BACKGROUND);

        this.textField = isPasswordField ? new PasswordField() : new TextField();
        configureTextField();

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);

        getChildren().addAll(this.label, this.textField);
    }

    private void configureTextField() {
        this.textField.setStyle(
                "-fx-text-fill: white; " +
                        "-fx-background-color: " + Theme.HEX_COLOR.SURFACE + "; " +
                        "-fx-border-color: " + Theme.HEX_COLOR.BORDER + "; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 5;"
        );

        this.textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            this.textField.setStyle(
                    "-fx-text-fill: white; " +
                            "-fx-background-color: " + Theme.HEX_COLOR.SURFACE + "; " +
                            "-fx-border-color: " + (newValue ? Theme.HEX_COLOR.PRIMARY : Theme.HEX_COLOR.BORDER) + "; " +
                            "-fx-border-width: 1; " +
                            "-fx-border-radius: 5;"
            );
        });
    }

    public String getEntry() {
        return textField.getText();
    }

    public void clearEntry() {
        textField.clear();
    }
    public TextField getTextField(){
        return  textField;
    }
}
