package gui;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Section extends StackPane {

    public Section(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        Rectangle background = new Rectangle();
        background.setStrokeWidth(0.1);
        background.setStroke(Color.DARKGRAY);
        background.setFill(fillColor);
        background.widthProperty().bind(gridPane.widthProperty().multiply(widthMultiplier));
        background.heightProperty().bind(gridPane.heightProperty().multiply(heightMultiplier));
        this.getChildren().addAll(background);

    }

    public Section(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        this(gridPane, Color.BLACK, widthMultiplier, heightMultiplier);
    }
}
