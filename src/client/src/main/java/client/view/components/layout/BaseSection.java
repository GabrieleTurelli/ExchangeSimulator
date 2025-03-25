package client.view.components.layout;

import client.view.theme.Theme;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BaseSection extends StackPane {

    public BaseSection(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        Rectangle background = new Rectangle();
        background.setStrokeWidth(0.1);
        background.setStroke(Theme.COLOR.BORDER);
        background.setFill(fillColor);
        background.widthProperty().bind(gridPane.widthProperty().multiply(widthMultiplier));
        background.heightProperty().bind(gridPane.heightProperty().multiply(heightMultiplier));
        this.getChildren().addAll(background);

    }

    public BaseSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        this(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }
}
