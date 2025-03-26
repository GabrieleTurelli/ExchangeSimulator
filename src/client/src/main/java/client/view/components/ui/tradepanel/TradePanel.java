package client.view.components.ui.tradepanel;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class TradePanel extends VBox {

    private final ToggleOrderMode toggleOrderMode;
    private OrderEntry orderEntry;
    private OrderSideEntry orderSideEntry;
    private AvailableCoin availableCoin;
    private double usdtAvailable;
    private double coinAvailable;

    public TradePanel(String coin, double usdtAvailable, double coinAvailable) {
        super();

        toggleOrderMode = new ToggleOrderMode();
        orderEntry = new OrderEntry(toggleOrderMode.isLimit());
        orderSideEntry = new OrderSideEntry();
        availableCoin = new AvailableCoin(this.usdtAvailable, this.coinAvailable);

        setSpacing(20);
        setPadding(new Insets(10));

        getChildren().addAll(toggleOrderMode, orderEntry, orderSideEntry, availableCoin);

        toggleOrderMode.setOnToggleChange(this::rerenderOrderEntry);
    }

    private void rerenderOrderEntry(boolean isLimit) {
        getChildren().removeAll(orderEntry, orderSideEntry, availableCoin);
        orderEntry = new OrderEntry(isLimit);
        getChildren().addAll(orderEntry, orderSideEntry, availableCoin);
    }

}
