package client.view.components.ui;

import java.util.ArrayList;
import java.util.List;

import client.view.theme.Theme;
import client.view.utils.StyleUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CoinMenu extends HBox {

    public interface CoinChangeListener {
        void onCoinChange(String newCoin);
    }

    private final List<CoinChangeListener> listeners = new ArrayList<>();
    private final Label coinTextLabel;
    private final Label chevronLabel;
    private final ContextMenu dropdownMenu;
    private final List<String> coins;
    private final HBox labelContainer;

    public CoinMenu(String initialCoin) {
        this.coins = new ArrayList<>();
        this.dropdownMenu = initializeDropdownMenu();
        this.coinTextLabel = createLabel(initialCoin, Theme.FONT_SIZE.LARGE, Color.WHITE, Pos.CENTER_LEFT);
        this.chevronLabel = createLabel("▼", Theme.FONT_SIZE.SMALL, Color.WHITE, Pos.CENTER);

        this.labelContainer = new HBox(coinTextLabel, chevronLabel);
        setMargin(labelContainer, new Insets(10, 0, 10, 0));
        setupLabelContainer();
        setupClickHandler();
    }

    private void setupLabelContainer() {
        labelContainer.setPrefWidth(140);
        labelContainer.setSpacing(15);
        labelContainer.setAlignment(Pos.CENTER);
        labelContainer.setStyle(StyleUtils.generateStyle(Theme.HEX_COLOR.BACKGROUND, Theme.SPACING.SMALL));
        labelContainer.setOnMouseEntered(
                e -> labelContainer.setStyle(StyleUtils.generateStyle(Theme.HEX_COLOR.SURFACE, Theme.SPACING.SMALL)));
        labelContainer.setOnMouseExited(e -> resetStyle(labelContainer));

        dropdownMenu.setOnShowing(event -> chevronLabel.setText("▲"));
        dropdownMenu.setOnHidden(event -> {
            chevronLabel.setText("▼");
            resetStyle(labelContainer);
        });

        getChildren().add(labelContainer);
    }

    private void resetStyle(HBox labelContainer) {
        if (!dropdownMenu.isShowing()) {
            labelContainer.setStyle(StyleUtils.generateStyle(Theme.HEX_COLOR.BACKGROUND, Theme.SPACING.SMALL));
        }
    }

    private void setupClickHandler() {
        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (dropdownMenu.isShowing()) {
                    dropdownMenu.hide();
                } else {
                    dropdownMenu.show(this, localToScreen(getBoundsInLocal()).getMinX(),
                            localToScreen(getBoundsInLocal()).getMaxY() - 4);
                }
            }
        });
    }

    private ContextMenu initializeDropdownMenu() {
        ContextMenu menu = new ContextMenu();
        menu.setStyle(StyleUtils.generateStyle(Theme.HEX_COLOR.SURFACE, Theme.SPACING.SMALL));
        return menu;
    }

    private void addCoin(String coin) {
        coins.add(coin);

        Label itemLabel = createLabel(coin, Theme.FONT_SIZE.MEDIUM, Color.WHITE, Pos.CENTER);
        itemLabel.setPrefWidth(labelContainer.getPrefWidth() - 10);
        itemLabel.setAlignment(Pos.CENTER);

        CustomMenuItem menuItem = new CustomMenuItem(itemLabel, true);
        menuItem.setOnAction(event -> setCoin(coin));
        dropdownMenu.getItems().add(menuItem);
    }

    public void addCoins(String[] coins) {
        for (String coin : coins) {
            addCoin(coin + "/USDT");
        }

    }

    private Label createLabel(String text, int fontSize, Color color, Pos alignment) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial bold", fontSize));
        label.setTextFill(color);
        label.setAlignment(alignment);
        return label;
    }

    public String getCurrentCoin() {
        return coinTextLabel.getText();
    }

    public void addCoinChangeListener(CoinChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyCoinChange(String newCoin) {
        for (CoinChangeListener listener : listeners) {
            listener.onCoinChange(newCoin);
        }
    }

    public void setCoin(String coin) {
        coinTextLabel.setText(coin);
        notifyCoinChange(coin);
    }

}
