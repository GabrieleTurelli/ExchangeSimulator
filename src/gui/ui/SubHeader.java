package gui.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SubHeader extends Section {

    public SubHeader(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);

        CoinMenu coinMenu = new CoinMenu("BTC/USDT");
        coinMenu.setMaxHeight(20);
        coinMenu.setMaxWidth(150);

        Line separator = new Line();
        separator.setStartY(0);
        separator.setEndY(20); separator.setStroke(THEME.COLOR.TERTIARY);
        separator.setStrokeWidth(1);

        Label coinPriceLabel = new Label("12.483");
        coinPriceLabel.setTextFill(Color.GREEN);
        coinPriceLabel.setFont(Font.font("Arial black", FontWeight.EXTRA_BOLD, 20));

        Region spacer1 = new Region();
        spacer1.setPrefWidth(10);

        VBox dailyChange = new VBox();

        Label dailyChangeLabel = new Label("24h Change");
        dailyChangeLabel.setTextFill(THEME.COLOR.TEXT_SECONDARY);
        dailyChangeLabel.setFont(new Font(10));
        dailyChangeLabel.setAlignment(Pos.BOTTOM_CENTER);

        Label dailyChangeValue = new Label("-12.65%");
        dailyChangeValue.setTextFill(Color.GREEN);
        dailyChangeValue.setFont(new Font(14));
        dailyChangeValue.setAlignment(Pos.TOP_CENTER);
        dailyChange.getChildren().addAll(dailyChangeLabel, dailyChangeValue);

        Region spacer2 = new Region();
        spacer2.setPrefWidth(40);


        VBox dailyLow = new VBox();

        Label dailyLowLabel = new Label("24h low");
        dailyLowLabel.setTextFill(THEME.COLOR.TEXT_SECONDARY);
        dailyLowLabel.setFont(new Font(10));
        dailyLowLabel.setAlignment(Pos.BOTTOM_CENTER);

        Label dailyLowValue = new Label("11.653");
        dailyLowValue.setTextFill(THEME.COLOR.TEXT_PRIMARY);
        dailyLowValue.setFont(new Font(14));
        dailyLowValue.setAlignment(Pos.TOP_CENTER);
        dailyLow.getChildren().addAll(dailyLowLabel, dailyLowValue);

        Region spacer3 = new Region();
        spacer3.setPrefWidth(10);

        VBox dailyHigh = new VBox();

        Label dailyHighLabel = new Label("24h high");
        dailyHighLabel.setTextFill(THEME.COLOR.TEXT_SECONDARY);
        dailyHighLabel.setFont(new Font(10));
        dailyHighLabel.setAlignment(Pos.BOTTOM_CENTER);

        Label dailHighValue = new Label("12.813");
        dailHighValue.setTextFill(THEME.COLOR.TEXT_PRIMARY);
        dailHighValue.setFont(new Font(14));
        dailHighValue.setAlignment(Pos.TOP_CENTER);
        dailyHigh.getChildren().addAll(dailyHighLabel, dailHighValue);

        Region spacer4 = new Region();
        spacer4.setPrefWidth(10);


        HBox subHeaderContent = new HBox(10);
        subHeaderContent.getChildren().addAll(coinMenu, separator,spacer1, coinPriceLabel, spacer2, dailyChange, spacer3, dailyLow, spacer4, dailyHigh);
        subHeaderContent.setSpacing(10);
        subHeaderContent.setPadding(new Insets(10));
        subHeaderContent.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().add(subHeaderContent);
    }
}
