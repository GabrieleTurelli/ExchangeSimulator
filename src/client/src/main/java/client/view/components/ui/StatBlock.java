package client.view.components.ui;

import client.view.theme.Theme;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StatBlock extends VBox {

    private final Label titleLabel;
    private final Label valueLabel;

    public StatBlock(String label, String value, Color valueColor) {
        setAlignment(Pos.CENTER_LEFT); 

        titleLabel = new Label(label);
        titleLabel.setTextFill(Theme.COLOR.ON_SURFACE);
        titleLabel.setFont(new Font(10));

        valueLabel = new Label(value);
        valueLabel.setTextFill(valueColor);
        valueLabel.setFont(new Font(14));

        getChildren().addAll(titleLabel, valueLabel);
        setSpacing(2); 
    }

    public void setStatBlock(String label, String value, Color valueColor) {
        titleLabel.setText(label);
        valueLabel.setText(value);
        valueLabel.setTextFill(valueColor);
    }

    public void setStatBlock(String label, String value) {
        setStatBlock(label, value, Theme.COLOR.TEXT_PRIMARY); 
    }

    public void setStatBlock(String label, double value) {
        setStatBlock(label, String.valueOf(value), Theme.COLOR.TEXT_PRIMARY);
    }

}