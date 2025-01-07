package gui.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SubHeader extends Section {
    public SubHeader(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);
        CoinMenu coinMenu = new CoinMenu("BTC/USDT");
        coinMenu.setMaxHeight(30);
        coinMenu.setMaxWidth(150);
        HBox subHeaderContent = new HBox(10);
        subHeaderContent.getChildren().add(coinMenu);
        subHeaderContent.setSpacing(10);
        subHeaderContent.setPadding(new Insets(10));
        subHeaderContent.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(subHeaderContent);
//        ImageView logo = new ImageView(logoPath);
//        logo.setFitHeight(20);
//        logo.setPreserveRatio(true);
//        Text title = new Text(Title);
//        title.setFill(Color.WHITE);
//        title.setFont(Font.font("Verdana", 18));

//        HBox headerContent = new HBox(10);
//        headerContent.getChildren().addAll(logo, title);
//        headerContent.setAlignment(Pos.CENTER);
//        this.getChildren().add(headerContent);
    }
}
