package gui.components.layout;

import gui.components.ui.IconButton;
import gui.theme.Theme;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class HeaderSection extends BaseSection {
    public HeaderSection(GridPane gridPane, String logoPath, String Title, Color fillColor, double widthMultiplier, double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);

        // Logo
        ImageView logo = new ImageView(logoPath);
        logo.setFitHeight(20);
        logo.setPreserveRatio(true);

        // Title
        Text title = new Text(Title);
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Verdana", 18));

        // Account Icon Button
        IconButton accountButton = new IconButton("/assets/account.png", 30);

        // Title Content (centered)
        HBox titleContent = new HBox(10);
        titleContent.getChildren().addAll(logo, title);
        titleContent.setAlignment(Pos.CENTER);
        // OFC THIS IS NOT THE BEST WAY TO CENTER THE TITLE CORRECTLY (50 = (20 [padding of the account icon] + 30 icon size))
        titleContent.setPadding(new Insets(0, 0, 0,50));

        // Account Content (Right side)
        HBox accountContent = new HBox();
        accountContent.getChildren().add(accountButton);
        accountContent.setAlignment(Pos.CENTER_RIGHT);
        accountContent.setPadding(new Insets(0, 20, 0, 0));

        // Header Layout using BorderPane
        BorderPane headerLayout = new BorderPane();
        headerLayout.setCenter(titleContent);
        headerLayout.setRight(accountContent);

        // Add the BorderPane to the header
        this.getChildren().add(headerLayout);
    }
    public HeaderSection(GridPane gridPane, String logoPath, String Title, double widthMultiplier, double heightMultiplier) {
        this(gridPane, logoPath, Title, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }
}
