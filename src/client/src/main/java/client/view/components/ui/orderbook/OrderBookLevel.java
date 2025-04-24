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
        Region background = createBackground(labelColor);

        StackPane backgroundContainer = new StackPane(background);
        backgroundContainer.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(backgroundContainer, labels);
        setAlignment(backgroundContainer, Pos.CENTER_RIGHT);

        layoutBoundsProperty().addListener(
                (observable, oldBounds, newBounds) -> updateBackgroundWidth(
                        background,
                        newBounds.getWidth(),
                        data.getAmount(),
                        maxSize));
    }

    private GridPane createLabelsGrid(OrderBookLevelData data, Color labelColor) {
        GridPane labels = new GridPane();

        Label price = createLabel(String.format("%.2f", data.getPrice()), labelColor, Pos.CENTER_LEFT);
        Label size = createLabel(String.format("%.2f", data.getQuantity()), Theme.COLOR.ON_BACKGROUND.darker(),
                Pos.CENTER_LEFT);
        var sum = createLabel(String.format("%.2f", data.getAmount()), Theme.COLOR.ON_BACKGROUND.darker(),
                Pos.CENTER_LEFT);

        labels.add(price, 0, 0);
        labels.add(size, 1, 0);
        labels.add(sum, 2, 0);

        labels.getColumnConstraints().addAll(
                createColumnConstraints(33),
                createColumnConstraints(33),
                createColumnConstraints(34));

        labels.setAlignment(Pos.CENTER_LEFT);
        labels.setHgap(10);
        labels.setPadding(new Insets(3, 10, 3, 10));

        return labels;
    }

    private Label createLabel(String text, Color textColor, Pos alignment) {
        Label label = new Label(text);
        label.setTextFill(textColor);
        label.setAlignment(alignment);
        return label;
    }

    private Region createBackground(Color color) {
        Region background = new Region();
        background.setStyle("-fx-background-color: " + toHexString(color) + ";");
        background.setOpacity(0.1);
        return background;
    }

    private ColumnConstraints createColumnConstraints(double percentWidth) {
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setPercentWidth(percentWidth);
        return constraints;
    }

    private void updateBackgroundWidth(Region background, double totalWidth, double size, double maxSize) {
        if (totalWidth > 0) {
            double proportion = size / maxSize;
            double calculatedWidth = totalWidth * proportion;

            background.setPrefWidth(calculatedWidth);
            background.setMinWidth(calculatedWidth);
            background.setMaxWidth(calculatedWidth);
        }
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
