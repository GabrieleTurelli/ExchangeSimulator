package gui.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoinMenu extends HBox {

    private final Label coinTextLabel;
    private final Label chevronLabel;
    private final ContextMenu dropdownMenu;
    private final List<String> coins;

    public CoinMenu(String initialCoin) {
        coins = new ArrayList<>();
        dropdownMenu = new ContextMenu();
        dropdownMenu.setStyle(generateStyle(THEME.HEX_COLOR.HOVER, 5));

        coinTextLabel = new Label(initialCoin);
        coinTextLabel.setFont(new Font("Arial", 16));
        coinTextLabel.setTextFill(Color.WHITE);
        coinTextLabel.setAlignment(Pos.CENTER_LEFT);

        chevronLabel = new Label("▼");
        chevronLabel.setFont(new Font("Arial", 12));
        chevronLabel.setTextFill(Color.WHITE);
        chevronLabel.setAlignment(Pos.CENTER);

        HBox labelContainer = new HBox(coinTextLabel, chevronLabel);
        labelContainer.setPadding(new Insets(5, 5, 5, 5));
        labelContainer.setSpacing(25);
        labelContainer.setAlignment(Pos.CENTER);
        labelContainer.setStyle(generateStyle(THEME.HEX_COLOR.BACKGROUND, 5));
        labelContainer.setOnMouseEntered(e -> labelContainer.setStyle(generateStyle(THEME.HEX_COLOR.HOVER, 5)));
        labelContainer.setOnMouseExited(e -> {
            if (!dropdownMenu.isShowing()) {
                labelContainer.setStyle(generateStyle(THEME.HEX_COLOR.BACKGROUND, 5));
            }
        });

        dropdownMenu.setOnShowing(event -> chevronLabel.setText("▲"));
        dropdownMenu.setOnHidden(event -> {
            chevronLabel.setText("▼");
            labelContainer.setStyle(generateStyle(THEME.HEX_COLOR.BACKGROUND, 5));
        });

        addCoin("BTC/USDT");
        addCoin("ETH/USDT");
        addCoin("XRP/USDT");
        addCoin("CRO/USDT");
        addCoin("ADA/USDT");

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double screenX = localToScreen(getBoundsInLocal()).getMinX();
                double screenY = localToScreen(getBoundsInLocal()).getMaxY();
                if (!dropdownMenu.isShowing()) {
                    dropdownMenu.show(this, screenX, screenY);
                    labelContainer.setStyle(generateStyle(THEME.HEX_COLOR.HOVER, 5));
                }else{
                    dropdownMenu.hide();
                    labelContainer.setStyle(generateStyle(THEME.HEX_COLOR.BACKGROUND, 5));
                }
            }
        });

        setAlignment(Pos.CENTER);
        getChildren().add(labelContainer);
    }

    private void addCoin(String coin) {
        coins.add(coin);
        Label itemLabel = createMenuLabel(coin, 110, 30, 14, Color.WHITE);
        CustomMenuItem menuItem = new CustomMenuItem(itemLabel, true);
        menuItem.setOnAction(event -> setCoin(coin));
        dropdownMenu.getItems().add(menuItem);
    }

    public void setCoin(String coin) {
        coinTextLabel.setText(coin);
    }

    public void setCoin() {
        setCoin(coins.get(new Random().nextInt(coins.size())));
    }

    public String getCurrentCoin() {
        return coinTextLabel.getText();
    }

    private Label createMenuLabel(String text, double width, double height, int fontSize, Color textColor) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", fontSize));
        label.setTextFill(textColor);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private String generateStyle(String backgroundColor, int radius) {
        return String.format("-fx-background-color: %s; -fx-cursor: hand; -fx-border-radius: %d; -fx-background-radius: %d;", backgroundColor, radius, radius);
    }
}
