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

public class RegisterScreen extends VBox {
    private final TextInputEntry usernameEntry;
    private final TextInputEntry passwordEntry;
    private final TextInputEntry confirmPasswordEntry;
    private final Button registerButton;
    private final Hyperlink loginLink;
    private final Label errorLabel;

    public RegisterScreen() {
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
        this.confirmPasswordEntry = new TextInputEntry("Confirm Password", true);

        this.registerButton = new Button("Register");
        registerButton.setStyle("-fx-cursor: hand");

        this.loginLink = new Hyperlink("Login here.");
        loginLink.setStyle("-fx-text-fill: cyan; -fx-underline: true;");

        this.errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red");

        addInputListeners();
        setMargin(logoImageView, new Insets(20));
        setMargin(registerButton, new Insets(10, 0, 0, 0));
        setMargin(loginLink, new Insets(5, 0, 0, 0));
        setMargin(errorLabel, new Insets(0,0,10,0));

        getChildren().addAll(logoImageView, usernameEntry, passwordEntry, confirmPasswordEntry, registerButton, loginLink, errorLabel);
    }

    private void addInputListeners() {
        usernameEntry.getTextField().textProperty().addListener((observable, oldValue, newValue) -> clearErrorMessage());
        passwordEntry.getTextField().textProperty().addListener((observable, oldValue, newValue) -> clearErrorMessage());
        confirmPasswordEntry.getTextField().textProperty().addListener((observable, oldValue, newValue) -> clearErrorMessage());
    }

    public String getUsernameEntry() {
        return usernameEntry.getEntry();
    }

    public String getPasswordEntry() {
        return passwordEntry.getEntry();
    }

    public String getConfirmPasswordEntry() {
        return confirmPasswordEntry.getEntry();
    }

    public Button getRegisterButton() {
        return this.registerButton;
    }

    public Hyperlink getLoginLink() {
        return this.loginLink;
    }

    public void setErrorMessage(String message) {
        this.errorLabel.setText(message);
    }

    public void clearErrorMessage() {
        this.errorLabel.setText("");
    }
}
