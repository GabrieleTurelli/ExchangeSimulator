package view.components.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import view.theme.Theme;

public class StatBlock extends VBox {

    public StatBlock(String label, String value, javafx.scene.paint.Color valueColor) {
        setAlignment(Pos.CENTER);

        Label titleLabel = new Label(label);
        titleLabel.setTextFill(Theme.COLOR.ON_SURFACE);
        titleLabel.setFont(new Font(10));

        Label valueLabel = new Label(value);
        valueLabel.setTextFill(valueColor);
        valueLabel.setFont(new Font(14));

        getChildren().addAll(titleLabel, valueLabel);
    }
}
