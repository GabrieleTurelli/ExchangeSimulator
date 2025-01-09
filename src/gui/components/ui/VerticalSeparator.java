package gui.components.ui;

import gui.theme.Theme;
import javafx.scene.shape.Line;

public class VerticalSeparator extends Line {

    public VerticalSeparator(double height) {
        setStartY(0);
        setEndY(height);
        setStroke(Theme.COLOR.BORDER);
        setStrokeWidth(1);
    }
}
