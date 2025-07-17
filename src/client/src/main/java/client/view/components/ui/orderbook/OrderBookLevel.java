// OrderBookLevel.java
package client.view.components.ui.orderbook;

import client.model.market.OrderBookLevelData;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class OrderBookLevel extends StackPane {
    public OrderBookLevel(OrderBookLevelData data, Color labelColor, double maxSize) {
        GridPane labels = createLabelsGrid(data, labelColor);

        labels.prefWidthProperty().bind(widthProperty());

        Region background = createBackground(labelColor);
        background.prefHeightProperty().bind(labels.heightProperty());

        StackPane bgContainer = new StackPane(background);
        bgContainer.setAlignment(Pos.CENTER_RIGHT);
        getChildren().addAll(bgContainer, labels);
        setAlignment(bgContainer, Pos.CENTER_RIGHT);

        layoutBoundsProperty().addListener((obs, oldB, newB) -> {
            double w = newB.getWidth() * (data.getQuantity() / maxSize);
            background.setMinWidth(w);
            background.setPrefWidth(w);
            background.setMaxWidth(w);
        });
    }

    private GridPane createLabelsGrid(OrderBookLevelData data, Color labelColor) {
        GridPane grid = new GridPane();

        Label price = createLabel(String.format("%.2f", data.getPrice()), labelColor, Pos.CENTER_LEFT);
        Label size = createLabel(String.format("%.2f", data.getQuantity()), Theme.COLOR.ON_BACKGROUND.darker(),
                Pos.CENTER_LEFT);
        Label sum = createLabel(String.format("%.2f", data.getAmount()), Theme.COLOR.ON_BACKGROUND.darker(),
                Pos.CENTER_LEFT);

        grid.add(price, 0, 0);
        grid.add(size, 1, 0);
        grid.add(sum, 2, 0);

        grid.getColumnConstraints().addAll(
                createColumnConstraints(33),
                createColumnConstraints(33),
                createColumnConstraints(34));

        grid.setHgap(10);
        grid.setPadding(new Insets(3, 10, 3, 10));
        grid.setAlignment(Pos.CENTER_LEFT);
        return grid;
    }

    private Label createLabel(String text, Color color, Pos align) {
        Label l = new Label(text);
        l.setTextFill(color);
        l.setAlignment(align);
        return l;
    }

    private Region createBackground(Color c) {
        Region r = new Region();
        r.setStyle("-fx-background-color: " + toHexString(c) + ";");
        r.setOpacity(0.1);
        return r;
    }

    private ColumnConstraints createColumnConstraints(double pct) {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(pct);
        return cc;
    }

    private String toHexString(Color c) {
        return String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }
}
