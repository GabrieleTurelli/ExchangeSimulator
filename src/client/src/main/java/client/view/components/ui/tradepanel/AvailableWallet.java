package client.view.components.ui.tradepanel;

import client.view.theme.Theme;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class AvailableWallet extends HBox {
    private final Label coinLabel;
    private final Label coinValue;
    private final Label usdtValue;

    public AvailableWallet(String coin, double usdtAvailable, double coinAvailable) {
        setAlignment(Pos.CENTER_LEFT);

        HBox usdtContainer = new HBox();
        usdtContainer.setAlignment(Pos.CENTER_LEFT);
        Label usdtLabel = new Label("USDT : ");
        usdtLabel.setTextFill(Theme.COLOR.TEXT_SECONDARY);
        this.usdtValue = new Label(String.valueOf(usdtAvailable));
        this.usdtValue.setTextFill(Theme.COLOR.ON_BACKGROUND);
        this.usdtValue.setStyle("-fx-font-weight: bold;");
        usdtContainer.getChildren().addAll(usdtLabel, usdtValue);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox coinContainer = new HBox();
        coinContainer.setAlignment(Pos.CENTER_RIGHT);
        this.coinLabel = new Label("--- : ");
        this.coinLabel.setTextFill(Theme.COLOR.TEXT_SECONDARY);
        this.coinValue = new Label(String.valueOf(coinAvailable));
        this.coinValue.setTextFill(Theme.COLOR.ON_BACKGROUND);
        this.coinValue.setStyle("-fx-font-weight: bold;");
        coinContainer.getChildren().addAll(coinLabel, coinValue);

        getChildren().addAll(usdtContainer, spacer, coinContainer);
    }

    public void setAvailableWallet(String coin, double availableCoin, double availableUsdt) {
        this.coinLabel.setText(coin + " : ");
        this.coinValue.setText(String.format("%.2f", availableCoin));
        this.usdtValue.setText(String.format("%.2f", availableUsdt));
    }

}
