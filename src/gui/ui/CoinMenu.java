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

    private final Label coinLabel;
    private final ContextMenu dropdownMenu;
    private final List<String> coins;

    public CoinMenu(String initialCoin) {
        coins = new ArrayList<>();
        coinLabel = createLabel(initialCoin, 120, 30, 18, Color.WHITE);

        dropdownMenu = new ContextMenu();
        dropdownMenu.setStyle(generateStyle(THEME.HEX_COLOR.HOVER, 5));

        addCoin("BTC/USDT");
        addCoin("ETH/USDT");
        addCoin("XRP/USDT");
        addCoin("CRO/USDT");
        addCoin("ADA/USDT");

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double screenX = localToScreen(getBoundsInLocal()).getMinX() ;
                double screenY = localToScreen(getBoundsInLocal()).getMaxY();
                dropdownMenu.show(this, screenX, screenY);
            }
        });

//        setOnMouseEntered(event -> setStyle(generateStyle(THEME.HEX_COLOR.HOVER, 15)));
//        setOnMouseExited(event -> setStyle(generateStyle(THEME.HEX_COLOR.BACKGROUND, 15)));

        setAlignment(Pos.CENTER);
        getChildren().add(coinLabel);
    }

    private void addCoin(String coin) {
        coins.add(coin);
        Label itemLabel = createMenuLabel(coin, coinLabel.getPrefWidth() - 10 , coinLabel.getPrefHeight(), 14, Color.WHITE);
        CustomMenuItem menuItem = new CustomMenuItem(itemLabel, true);
        menuItem.setOnAction(event -> setCoin(coin));
        dropdownMenu.getItems().add(menuItem);
    }

    public void setCoin(String coin) {
        coinLabel.setText(coin);
    }

    public void setCoin() {
        setCoin(coins.get(new Random().nextInt(coins.size())));
    }

    public String getCurrentCoin() {
        return coinLabel.getText();
    }

    private Label createLabel(String text, double width, double height, int fontSize, Color textColor) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", fontSize));
        label.setPadding(new Insets(10, 15, 10, 15));
        label.setTextFill(textColor);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
        label.setAlignment(Pos.CENTER);
        label.setStyle(generateStyle(THEME.HEX_COLOR.BACKGROUND ,  5));
        label.setOnMouseEntered(e -> label.setStyle(generateStyle(THEME.HEX_COLOR.HOVER,  5)));
        label.setOnMouseExited(e -> label.setStyle(generateStyle(THEME.HEX_COLOR.BACKGROUND, 5)));
        return label;
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

    private String generateStyle(String backgroundColor,  int radius) {
        return String.format("-fx-background-color: %s; -fx-cursor: hand; -fx-border-radius: %d; -fx-background-radius: %d;", backgroundColor, radius, radius);
    }
}
