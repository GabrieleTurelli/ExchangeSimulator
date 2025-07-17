package client.view.components.ui.tradepanel;

import java.util.function.Consumer;

import client.view.components.ui.HorizontalSeparator;
import client.view.components.ui.TextToggleButton;
import client.view.theme.Theme;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ToggleOrderMode extends VBox {
    private boolean limitToggled;
    private boolean marketToggled;

    private Consumer<Boolean> onToggleChange;

    public ToggleOrderMode() {
        super();
        setSpacing(10);
        TextToggleButton marketButton = new TextToggleButton("Market", Theme.HEX_COLOR.ON_SURFACE,
                Theme.HEX_COLOR.PRIMARY);
        TextToggleButton limitButton = new TextToggleButton("Limit", Theme.HEX_COLOR.ON_SURFACE,
                Theme.HEX_COLOR.PRIMARY);
        HorizontalSeparator spacer = new HorizontalSeparator(0);
        spacer.endXProperty().bind(this.widthProperty().subtract(10));
        //

        limitButton.toggle();

        marketButton.setOnAction(event -> {
            if (!marketButton.isToggled()) {
                marketButton.toggle(true);
                limitButton.toggle(false);
                setMarketToggled(true);
            }
        });

        limitButton.setOnAction(event -> {
            if (!limitButton.isToggled()) {
                limitButton.toggle(true);
                marketButton.toggle(false);
                setMarketToggled(false);
            }
        });

        this.limitToggled = limitButton.isToggled();
        this.marketToggled = marketButton.isToggled();

        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(marketButton, limitButton);
        buttonContainer.setAlignment(Pos.CENTER);

        getChildren().addAll(buttonContainer, spacer);
    }

    public boolean isLimit() {
        return limitToggled;
    }

    public boolean isMarket() {
        return marketToggled;
    }

    public void setOnToggleChange(Consumer<Boolean> onToggleChange) {
        this.onToggleChange = onToggleChange;
    }

    private void setMarketToggled(boolean isMarket) {
        this.marketToggled = isMarket;
        this.limitToggled = !isMarket;
        if (onToggleChange != null) {
            onToggleChange.accept(limitToggled);
        }
    }
}
