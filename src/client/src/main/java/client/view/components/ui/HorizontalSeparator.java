package client.view.components.ui;

import client.view.theme.Theme;
import javafx.scene.shape.Line;

public class HorizontalSeparator extends Line {
    public HorizontalSeparator(double width) {
        setStartX(0);
        setEndX(width);
        setStroke(Theme.COLOR.SURFACE);
        setStrokeWidth(1);
    }
}
