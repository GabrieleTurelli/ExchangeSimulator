package view.components.ui.tradepanel;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import view.theme.Theme;

public class AvailableCoin extends HBox {
    public AvailableCoin() {
        setAlignment(Pos.CENTER_LEFT);

        HBox usdtContainer = new HBox();
        usdtContainer.setAlignment(Pos.CENTER_LEFT);
        Label usdtLabel = new Label("USDT : ");
        usdtLabel.setTextFill(Theme.COLOR.TEXT_SECONDARY);
        Label usdtValue = new Label("10.000");
        usdtValue.setTextFill(Theme.COLOR.ON_BACKGROUND);
        usdtValue.setStyle("-fx-font-weight: bold;");
        usdtContainer.getChildren().addAll(usdtLabel, usdtValue);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox coinContainer = new HBox();
        coinContainer.setAlignment(Pos.CENTER_RIGHT);
        Label coinLabel = new Label("BTC : ");
        coinLabel.setTextFill(Theme.COLOR.TEXT_SECONDARY);
        Label coinValue = new Label("10.0");
        coinValue.setTextFill(Theme.COLOR.ON_BACKGROUND);
        coinValue.setStyle("-fx-font-weight: bold;");
        coinContainer.getChildren().addAll(coinLabel, coinValue);

        getChildren().addAll(usdtContainer, spacer, coinContainer);
    }
}
