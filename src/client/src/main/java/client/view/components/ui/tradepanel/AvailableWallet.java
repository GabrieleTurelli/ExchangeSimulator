package client.view.components.ui.tradepanel;

import client.view.theme.Theme;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class AvailableWallet extends HBox {
    private Label coinValue;
    private Label usdtValue;

    public AvailableWallet(double usdtAvailable, double coinAvailable) {
        setAlignment(Pos.CENTER_LEFT);

        HBox usdtContainer = new HBox();
        usdtContainer.setAlignment(Pos.CENTER_LEFT);
        Label usdtLabel = new Label("USDT : ");
        usdtLabel.setTextFill(Theme.COLOR.TEXT_SECONDARY);
        usdtValue = new Label(String.valueOf(usdtAvailable));
        usdtValue.setTextFill(Theme.COLOR.ON_BACKGROUND);
        usdtValue.setStyle("-fx-font-weight: bold;");
        usdtContainer.getChildren().addAll(usdtLabel, usdtValue);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox coinContainer = new HBox();
        coinContainer.setAlignment(Pos.CENTER_RIGHT);
        Label coinLabel = new Label("BTC : ");
        coinLabel.setTextFill(Theme.COLOR.TEXT_SECONDARY);
        coinValue = new Label(String.valueOf(coinAvailable));
        coinValue.setTextFill(Theme.COLOR.ON_BACKGROUND);
        coinValue.setStyle("-fx-font-weight: bold;");
        coinContainer.getChildren().addAll(coinLabel, coinValue);

        getChildren().addAll(usdtContainer, spacer, coinContainer);
    }

    public void setAvailableWallet(double availableCoin, double availableUsdt) {
        this.coinValue.setText(String.valueOf(availableCoin));
        this.usdtValue.setText(String.valueOf(availableUsdt));
    }

}
