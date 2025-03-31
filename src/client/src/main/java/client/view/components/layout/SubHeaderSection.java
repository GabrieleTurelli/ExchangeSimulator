package client.view.components.layout;

import client.view.components.ui.PriceLabel;
import client.view.components.ui.StatBlock;
import client.view.components.ui.VerticalSeparator;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class SubHeaderSection extends BaseSection {
    private String initialCoin;
    private double price;
    private double dailyChange;
    private double dailyLow;
    private double dailyHigh;
    private StatBlock changeStatBlock;
    private StatBlock lowStatBlock;
    private StatBlock highStatBlock;

    public SubHeaderSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
        this.initialCoin = "BTC/USDT";
        this.dailyChange = 0.0;
        this.dailyLow = 0.0;
        this.dailyHigh = 0.0;
        this.changeStatBlock = new StatBlock("24h Change", String.valueOf(dailyChange), Theme.COLOR.GREEN);
        this.lowStatBlock = new StatBlock("24h Low", String.valueOf(dailyLow), Theme.COLOR.TEXT_PRIMARY);
        this.highStatBlock = new StatBlock("24h High", String.valueOf(dailyHigh), Theme.COLOR.TEXT_PRIMARY);

        HBox subHeaderContent = new HBox(10);
        subHeaderContent.setSpacing(10);
        subHeaderContent.setPadding(new Insets(10));
        subHeaderContent.setAlignment(Pos.CENTER_LEFT);
        Label coinLabel = new Label(initialCoin);
        coinLabel.setTextFill(Theme.COLOR.ON_BACKGROUND);
        coinLabel.setFont(Theme.FONT_STYLE.TITLE);
        coinLabel.setPadding(new Insets(10, 10, 10, 10));

        subHeaderContent.getChildren().addAll(
                coinLabel,
                new VerticalSeparator(20),
                createSpacer(10),
                new PriceLabel(String.valueOf(price)),
                createSpacer(40),
                this.changeStatBlock,
                createSpacer(10),
                this.lowStatBlock,
                createSpacer(10),
                this.highStatBlock);

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

    public void updateStatBlocks() {
        this.changeStatBlock.setStatBlock("24h Change", dailyChange);
        this.lowStatBlock.setStatBlock("24h Low", dailyLow);
        this.highStatBlock.setStatBlock("24h High", dailyHigh);
    }
}
