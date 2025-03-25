package client.view.components.ui.tradepanel;
import client.view.theme.Theme;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class OrderSideEntry extends HBox {

    public OrderSideEntry() {
        super();
        setSpacing(5);

        OrderSideButton buyButton = new OrderSideButton("Buy", Theme.HEX_COLOR.GREEN, Theme.HEX_COLOR.DARK_GREEN);
        OrderSideButton sellButton = new OrderSideButton("Sell",Theme.HEX_COLOR.RED, Theme.HEX_COLOR.DARK_RED);

        HBox.setHgrow(buyButton, Priority.ALWAYS);
        HBox.setHgrow(sellButton, Priority.ALWAYS);

        setAlignment(Pos.CENTER);
        getChildren().addAll(buyButton, sellButton);
    }
}