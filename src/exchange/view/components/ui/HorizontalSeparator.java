package view.components.ui;

import javafx.scene.shape.Line;
import view.theme.Theme;

public class HorizontalSeparator extends Line {
    public HorizontalSeparator(double width) {
        setStartX(0);
        setEndX(width);
        setStroke(Theme.COLOR.SURFACE);
        setStrokeWidth(1);
    }
}
