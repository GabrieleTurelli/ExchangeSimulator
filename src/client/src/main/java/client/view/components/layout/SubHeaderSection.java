package client.view.components.layout;

import client.view.components.ui.CoinMenu;
import client.view.components.ui.PriceLabel;
import client.view.components.ui.StatBlock;
import client.view.components.ui.VerticalSeparator;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class SubHeaderSection extends BaseSection {
    private String initialCoin;
    private double price;
    private double dailyChange;
    private double dailyLow;
    private double dailyHigh;

    public SubHeaderSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
        this.initialCoin = "BTC/USDT";
        this.dailyChange = 0.0;
        this.dailyLow = 0.0;
        this.dailyHigh = 0.0;

        HBox subHeaderContent = new HBox(10);
        subHeaderContent.setSpacing(10);
        subHeaderContent.setPadding(new Insets(10));
        subHeaderContent.setAlignment(Pos.CENTER_LEFT);

        subHeaderContent.getChildren().addAll(
                new CoinMenu(initialCoin),
                new VerticalSeparator(20),
                createSpacer(10),
                new PriceLabel(String.valueOf(price)),
                createSpacer(40),
                new StatBlock("24h Change", String.valueOf(dailyChange), Theme.COLOR.GREEN),
                createSpacer(10),
                new StatBlock("24h Low", String.valueOf(dailyLow), Theme.COLOR.TEXT_PRIMARY),
                createSpacer(10),
                new StatBlock("24h High", String.valueOf(dailyHigh), Theme.COLOR.TEXT_PRIMARY));

        this.getChildren().add(subHeaderContent);
    }

    private Region createSpacer(double width) {
        Region spacer = new Region();
        spacer.setPrefWidth(width);
        return spacer;
    }

    public void setInitialCoin(String initialCoin) {
        this.initialCoin = initialCoin;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDailyChange(double dailyChange) {
        this.dailyChange = dailyChange;
    }

    public void setDailyLow(double dailyLow) {
        this.dailyLow = dailyLow;
    }

    public void setDailyHigh(double dailyHigh) {
        this.dailyHigh = dailyHigh;
    }

}
