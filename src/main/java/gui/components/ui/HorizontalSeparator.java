package main.java.gui.components.ui;

import javafx.scene.shape.Line;
import main.java.gui.theme.Theme;

public class HorizontalSeparator extends Line {
    public HorizontalSeparator(double width) {
        setStartX(0);
        setEndX(width);
        setStroke(Theme.COLOR.BORDER);
        setStrokeWidth(1);
    }
}
