// package client.view.components.layout;
package client.view.components.layout;

import java.text.DecimalFormat; 

import client.model.market.DailyMarketData;
import client.view.components.ui.CoinMenu;
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
import javafx.scene.paint.Color;

public class SubHeaderSection extends BaseSection {
    private String initialCoin;
    private final Label coinLabel;
    private final PriceLabel priceLabel;
    private final StatBlock changeStatBlock;
    private final StatBlock lowStatBlock;
    private final StatBlock highStatBlock;
    private final CoinMenu coinMenu;

    private final DecimalFormat priceFormat = new DecimalFormat("#,##0.00");
    private final DecimalFormat changeFormat = new DecimalFormat("+#,##0.00;-#,##0.00");
    private final DecimalFormat percentFormat = new DecimalFormat("+#,##0.00'%';-#,##0.00'%'");

    public SubHeaderSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
        this.initialCoin = "BTC/USDT";

        this.coinLabel = new Label(initialCoin);
        coinLabel.setTextFill(Theme.COLOR.ON_BACKGROUND);
        coinLabel.setFont(Theme.FONT_STYLE.TITLE);
        coinLabel.setPadding(new Insets(10, 10, 10, 10));

        this.priceLabel = new PriceLabel("-.--");
        this.changeStatBlock = new StatBlock("24h Change", "-.-- %", Theme.COLOR.TEXT_PRIMARY);
        this.lowStatBlock = new StatBlock("24h Low", "-.--", Theme.COLOR.TEXT_PRIMARY);
        this.highStatBlock = new StatBlock("24h High", "-.--", Theme.COLOR.TEXT_PRIMARY);

        HBox subHeaderContent = new HBox(10);
        subHeaderContent.setSpacing(10);
        subHeaderContent.setPadding(new Insets(10));
        subHeaderContent.setAlignment(Pos.CENTER_LEFT);

        this.coinMenu = new CoinMenu(initialCoin);

        subHeaderContent.getChildren().addAll(
                this.coinMenu,
                new VerticalSeparator(20),
                createSpacer(10),
                priceLabel,
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

    public void updateDisplay(DailyMarketData data) {
        if (data == null) {
            priceLabel.setText("Error");
            changeStatBlock.setStatBlock("24h Change", "Error", Theme.COLOR.RED);
            lowStatBlock.setStatBlock("24h Low", "Error", Theme.COLOR.TEXT_PRIMARY);
            highStatBlock.setStatBlock("24h High", "Error", Theme.COLOR.TEXT_PRIMARY);
            return;
        }

        priceLabel.setText(priceFormat.format(data.getPrice()));

        double changeValue = data.getDailyChange();
        Color changeColor = changeValue >= 0 ? Theme.COLOR.GREEN : Theme.COLOR.RED;
        changeStatBlock.setStatBlock("24h Change", percentFormat.format(changeValue), changeColor);

        lowStatBlock.setStatBlock("24h Low", priceFormat.format(data.getDailyLow()), Theme.COLOR.TEXT_PRIMARY);
        highStatBlock.setStatBlock("24h High", priceFormat.format(data.getDailyHigh()), Theme.COLOR.TEXT_PRIMARY);

    }

    public void setInitialCoin(String initialCoin) {
        this.initialCoin = initialCoin;
        this.coinLabel.setText(initialCoin);
    }

    public CoinMenu getCoinMenu() {
        return this.coinMenu;
    }

}