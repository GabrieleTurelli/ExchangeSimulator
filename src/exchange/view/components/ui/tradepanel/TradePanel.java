package view.components.ui.tradepanel;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class TradePanel extends VBox {

    private final ToggleOrderMode toggleOrderMode;
    private OrderEntry orderEntry;
    private OrderSideEntry orderSideEntry;

    public TradePanel() {
        super();

        toggleOrderMode = new ToggleOrderMode();
        orderEntry = new OrderEntry(toggleOrderMode.isLimit());
        orderSideEntry = new OrderSideEntry();



        setSpacing(20);
        setPadding(new Insets(10));

        getChildren().addAll(toggleOrderMode, orderEntry, orderSideEntry);

        toggleOrderMode.setOnToggleChange(this::rerenderOrderEntry);
    }

    private void rerenderOrderEntry(boolean isLimit) {
        getChildren().remove(orderEntry);
        getChildren().remove(orderSideEntry);
        orderEntry = new OrderEntry(isLimit);
        getChildren().addAll(orderEntry, orderSideEntry);
    }
}
