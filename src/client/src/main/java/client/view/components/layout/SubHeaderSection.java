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

    public SubHeaderSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);

        HBox subHeaderContent = new HBox(10);
        subHeaderContent.setSpacing(10);
        subHeaderContent.setPadding(new Insets(10));
        subHeaderContent.setAlignment(Pos.CENTER_LEFT);

        subHeaderContent.getChildren().addAll(
                new CoinMenu("BTC/USDT"),
                new VerticalSeparator(20),
                createSpacer(10),
                new PriceLabel("12.483"),
                createSpacer(40),
                new StatBlock("24h Change", "-12.65%", Theme.COLOR.GREEN),
                createSpacer(10),
                new StatBlock("24h Low", "11.653", Theme.COLOR.TEXT_PRIMARY),
                createSpacer(10),
                new StatBlock("24h High", "12.813", Theme.COLOR.TEXT_PRIMARY)
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
