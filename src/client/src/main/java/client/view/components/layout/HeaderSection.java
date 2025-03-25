package client.view.components.layout;

import client.view.components.ui.IconButton;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HeaderSection extends BaseSection {
    public HeaderSection(GridPane gridPane, String logoPath, String Title, Color fillColor, double widthMultiplier, double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);

        ImageView logo = new ImageView(logoPath);
        logo.setFitHeight(20);
        logo.setPreserveRatio(true);

        Text title = new Text(Title);
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Verdana", 18));

        IconButton accountButton = new IconButton("/account.png", 30);

        HBox titleContent = new HBox(10);
        titleContent.getChildren().addAll(logo, title);
        titleContent.setAlignment(Pos.CENTER);
        titleContent.setPadding(new Insets(0, 0, 0,50));

        HBox accountContent = new HBox();
        accountContent.getChildren().add(accountButton);
        accountContent.setAlignment(Pos.CENTER_RIGHT);
        accountContent.setPadding(new Insets(0, 20, 0, 0));

        BorderPane headerLayout = new BorderPane();
        headerLayout.setCenter(titleContent);
        headerLayout.setRight(accountContent);

        this.getChildren().add(headerLayout);
    }
    public HeaderSection(GridPane gridPane, String logoPath, String Title, double widthMultiplier, double heightMultiplier) {
        this(gridPane, logoPath, Title, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }
}
