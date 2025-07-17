package client.view.components.ui.tradepanel;

import client.model.market.OrderSide;
import client.view.theme.Theme;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class OrderSideEntry extends HBox {
    private final OrderSideButton buyButton;
    private final OrderSideButton sellButton;
    private Runnable onBuy;
    private Runnable onSell;

    public OrderSideEntry() {
        super();
        setSpacing(5);

        buyButton = new OrderSideButton(OrderSide.BUY.getValue(), Theme.HEX_COLOR.GREEN, Theme.HEX_COLOR.DARK_GREEN);
        sellButton = new OrderSideButton(OrderSide.SELL.getValue(), Theme.HEX_COLOR.RED, Theme.HEX_COLOR.DARK_RED);

        buyButton.setOnAction(evt -> {
            if (onBuy != null)
                onBuy.run();
        });
        sellButton.setOnAction(evt -> {
            if (onSell != null)
                onSell.run();
        });

        HBox.setHgrow(buyButton, Priority.ALWAYS);
        HBox.setHgrow(sellButton, Priority.ALWAYS);

        setAlignment(Pos.CENTER);
        getChildren().addAll(buyButton, sellButton);

    }

    public void setOnBuy(Runnable callback) {
        this.onBuy = callback;
    }

    /** Callback da chiamare quando si preme “Sell” */
    public void setOnSell(Runnable callback) {
        this.onSell = callback;
    }
}
