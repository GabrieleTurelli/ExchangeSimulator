package gui.components.ui;

import javafx.scene.shape.Line;
import gui.theme.Theme;

public class HorizontalSeparator extends Line {
    public HorizontalSeparator(double width) {
        setStartX(0);
        setEndX(width);
        setStroke(Theme.COLOR.SURFACE);
        setStrokeWidth(1);
    }
}
