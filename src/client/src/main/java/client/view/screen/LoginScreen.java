package client.view.screen;

import client.view.components.ui.TextInputEntry;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;

public class LoginScreen extends VBox {
    private final TextInputEntry usernameEntry;
    private final TextInputEntry passwordEntry;
    private final Button loginButton;
    private final Label errorLabel;
    private final Hyperlink registerLink;

    public LoginScreen() {
        super(10);
        setPadding(new Insets(20, 40, 20, 40));
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Theme.COLOR.BACKGROUND, new CornerRadii(0), new Insets(0))));

        Image logoImage = new Image("/logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(50);
        logoImageView.setPreserveRatio(true);

        this.usernameEntry = new TextInputEntry("Username");
        this.passwordEntry = new TextInputEntry("Password", true);

        this.loginButton = new Button("Login");
        loginButton.setStyle("-fx-cursor: hand");

        this.registerLink = new Hyperlink("Register here.");
        registerLink.setStyle("-fx-text-fill: cyan; -fx-underline: true;");

        this.errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red");

        addInputListeners();
        setMargin(logoImageView, new Insets(50));
        setMargin(loginButton, new Insets(10, 0, 0, 0));
        setMargin(registerLink, new Insets(5, 0, 0, 0));
        setMargin(errorLabel, new Insets(0,0,10,0));

        getChildren().addAll(logoImageView, usernameEntry, passwordEntry, loginButton, registerLink, errorLabel);
    }

    private void addInputListeners() {
        usernameEntry.getTextField().textProperty().addListener((observable, oldValue, newValue) -> clearErrorMessage());
        passwordEntry.getTextField().textProperty().addListener((observable, oldValue, newValue) -> clearErrorMessage());
    }

    public String getUsernameEntry() {
        return usernameEntry.getEntry();
    }

    public String getPasswordEntry() {
        return passwordEntry.getEntry();
    }

    public Button getLoginButton() {
        return this.loginButton;
    }

    public Hyperlink getRegisterLink() {
        return this.registerLink;
    }

    public void setErrorMessage(String message) {
        this.errorLabel.setText(message);
    }

    public void clearErrorMessage() {
        this.errorLabel.setText("");
    }
}
