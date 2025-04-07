package client.view.components.layout;

import client.model.user.Wallet;
import client.view.components.ui.tradepanel.AvailableWallet;
import client.view.components.ui.tradepanel.OrderEntry;
import client.view.components.ui.tradepanel.OrderSideEntry;
import client.view.components.ui.tradepanel.ToggleOrderMode;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TradePanelSection extends BaseSection {
    private VBox vbox;
    private OrderEntry orderEntry;
    private final ToggleOrderMode toggleOrderMode;
    private final OrderSideEntry orderSideEntry;
    private AvailableWallet availableWallet;
    private final String coin;

    public TradePanelSection(GridPane gridPane, double widthMultiplier, double heightMultiplier, String coin,
            double usdtAvailable, double coinAvailable) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);

        this.coin = coin;

        this.vbox = new VBox();

        this.toggleOrderMode = new ToggleOrderMode();
        this.orderEntry = new OrderEntry(toggleOrderMode.isLimit());
        this.orderSideEntry = new OrderSideEntry();
        this.availableWallet = new AvailableWallet(coin, usdtAvailable, coinAvailable);

        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10));

        vbox.getChildren().addAll(toggleOrderMode, orderEntry, orderSideEntry, availableWallet);
        this.toggleOrderMode.setOnToggleChange(this::rerenderOrderEntry);

        this.getChildren().add(vbox);

    }

    private void rerenderOrderEntry(boolean isLimit) {
        vbox.getChildren().removeAll(orderEntry, orderSideEntry, availableWallet);
        orderEntry = new OrderEntry(isLimit);
        vbox.getChildren().addAll(orderEntry, orderSideEntry, availableWallet);
    }

    public void updateDisplay(String coin, Wallet wallet) {
        if (wallet != null){
            availableWallet.setAvailableWallet(coin, wallet.getCoin(coin), wallet.getCoin("USDT"));

        }

    }


}
