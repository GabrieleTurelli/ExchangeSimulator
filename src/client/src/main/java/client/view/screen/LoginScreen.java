/**
 * Schermata di login 
 *
 * @author Gabriele Turelli 
 * @version 1.0
 */
package client.view.screen;

import java.io.IOException;

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
    /**
     * input dell' username.
     */
    private final TextInputEntry usernameEntry;

    /**
     * input della password
     */
    private final TextInputEntry passwordEntry;

    /**
     * Pulsante per inviare la richiesta di login.
     */
    private final Button loginButton;

    /**
     * Label da mostrare in caso di errore, in modo da informare del fail del login
     */
    private final Label errorLabel;

    /**
     * Link per passare alla schermata di registrazione al click.
     */
    private final Hyperlink registerLink;

    /**
     * Costruttore della schermata di login.
     * Imposta layout, stile e listener base per i campi.
     *
     * @throws IOException
     */
    public LoginScreen() throws IOException {
        // Imposta uno spaziatura iniziale di 10 px su tutti i lati
        super(10);

        setPadding(new Insets(20, 40, 20, 40));
        setAlignment(Pos.CENTER);
        setBackground(new Background(
                new BackgroundFill(Theme.COLOR.BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));

        // Logo dell'applicazione
        Image logoImage = new Image("/logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setPreserveRatio(true);
        logoImageView.setFitWidth(100);

        // campi di input
        this.usernameEntry = new TextInputEntry("Username");
        this.passwordEntry = new TextInputEntry("Password", true);

        // pulsante di login
        this.loginButton = new Button("Login");
        loginButton.setTextFill(Theme.COLOR.ON_PRIMARY);
        loginButton.setBackground(new Background(
                new BackgroundFill(Theme.COLOR.PRIMARY, new CornerRadii(5), Insets.EMPTY)));
        loginButton.setStyle("-fx-cursor: hand");

        // Adatta la larghezza del pulsante al campo username
        loginButton.prefWidthProperty().bind(usernameEntry.widthProperty());

        // Effetto hover in modo da cabiare il colore dello sfondo
        loginButton.setOnMouseEntered(e -> loginButton.setBackground(
                new Background(new BackgroundFill(Theme.COLOR.PRIMARY_VARIANT, new CornerRadii(5), Insets.EMPTY))));
        loginButton.setOnMouseExited(e -> loginButton.setBackground(
                new Background(new BackgroundFill(Theme.COLOR.PRIMARY, new CornerRadii(5), Insets.EMPTY))));

        // Link per passare allo schermata di registrazione
        this.registerLink = new Hyperlink("Register here.");
        registerLink.setStyle("-fx-text-fill: cyan; -fx-underline: true;");

        // Label da mostrare in caso di errori, inizialmente vuota
        this.errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red");

        // Aggiunge listener per pulire il messaggio d'errore quando l'utente
        // ridigita dopo esserci stato un errore
        addInputListeners();

        // margini specifici per i vari componenti
        setMargin(logoImageView, new Insets(10));
        setMargin(loginButton, new Insets(10, 0, 0, 0));
        setMargin(registerLink, new Insets(5, 0, 0, 0));
        setMargin(errorLabel, new Insets(0, 0, 10, 0));

        // Aggiunge i nuovi componenti alla lista di nodi figli della VBox
        getChildren().addAll(
                logoImageView,
                usernameEntry,
                passwordEntry,
                loginButton,
                registerLink,
                errorLabel);
    }

    /**
     * Listener sui campi di input per
     * azzerare il messaggio di errore ad ogni modifica.
     */
    private void addInputListeners() {
        usernameEntry.getTextField().textProperty()
                .addListener((obs, oldVal, newVal) -> clearErrorMessage());
        passwordEntry.getTextField().textProperty()
                .addListener((obs, oldVal, newVal) -> clearErrorMessage());
    }

    /**
     * Restituisce il testo inserito nel campo username.
     *
     * @return username inserito dall'utente
     */
    public String getUsernameEntry() {
        return usernameEntry.getEntry();
    }

    /**
     * Restituisce il testo inserito nel campo password.
     *
     * @return password inserita dall'utente
     */
    public String getPasswordEntry() {
        return passwordEntry.getEntry();
    }

    /**
     *
     * @return pulsante di login
     */
    public Button getLoginButton() {
        return this.loginButton;
    }

    /**
     *
     * @return hyperlink per la registrazione
     */
    public Hyperlink getRegisterLink() {
        return this.registerLink;
    }

    /**
     *
     * @param message testo del messaggio di errore
     */
    public void setErrorMessage(String message) {
        this.errorLabel.setText(message);
    }

    /**
     */
    public void clearErrorMessage() {
        this.errorLabel.setText("");
    }
}