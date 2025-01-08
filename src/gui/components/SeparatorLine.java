package gui.components;

import gui.theme.Theme;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SeparatorLine extends Line {

    public SeparatorLine(double height) {
        setStartY(0);
        setEndY(height);
        setStroke(Theme.COLOR.BORDER);
        setStrokeWidth(1);
    }
}
