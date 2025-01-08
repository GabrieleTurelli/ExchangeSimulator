package gui.components;

import gui.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class SubHeader extends Section {

    public SubHeader(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);

        HBox subHeaderContent = new HBox(10);
        subHeaderContent.setSpacing(10);
        subHeaderContent.setPadding(new Insets(10));
        subHeaderContent.setAlignment(Pos.CENTER_LEFT);

        subHeaderContent.getChildren().addAll(
                new CoinMenu("BTC/USDT"),
                new SeparatorLine(20),
                createSpacer(10),
                new PriceLabel("12.483"),
                createSpacer(40),
                new StatBlock("24h Change", "-12.65%", Theme.COLOR.GREEN),
                createSpacer(10),
                new StatBlock("24h Low", "11.653", Theme.COLOR.PRIMARY),
                createSpacer(10),
                new StatBlock("24h High", "12.813", Theme.COLOR.PRIMARY)
        );

        this.getChildren().add(subHeaderContent);
    }

    // Creates a spacer (empty region with fixed width)
    private Region createSpacer(double width) {
        Region spacer = new Region();
        spacer.setPrefWidth(width);
        return spacer;
    }
}
