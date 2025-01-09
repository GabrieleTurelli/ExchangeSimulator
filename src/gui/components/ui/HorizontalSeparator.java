package gui.components.ui;

import gui.theme.Theme;
import javafx.scene.shape.Line;

public class HorizontalSeparator extends Line {
    public HorizontalSeparator(double width) {
        setStartX(0);
        setEndX(width);
        setStroke(Theme.COLOR.BORDER);
        setStrokeWidth(1);
    }
}
