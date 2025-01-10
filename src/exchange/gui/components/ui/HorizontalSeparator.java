package exchange.gui.components.ui;

import javafx.scene.shape.Line;
import exchange.gui.theme.Theme;

public class HorizontalSeparator extends Line {
    public HorizontalSeparator(double width) {
        setStartX(0);
        setEndX(width);
        setStroke(Theme.COLOR.BORDER);
        setStrokeWidth(1);
    }
}
