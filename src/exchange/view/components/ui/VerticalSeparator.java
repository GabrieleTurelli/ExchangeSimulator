package view.components.ui;

import javafx.scene.shape.Line;
import view.theme.Theme;

public class VerticalSeparator extends Line {

    public VerticalSeparator(double height) {
        setStartY(0);
        setEndY(height);
        setStroke(Theme.COLOR.BORDER);
        setStrokeWidth(1);
    }
}
