/**
 * Controller per gestione della schermata di login.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.controller;

import java.io.IOException;

import client.model.clients.LoginClient;
import client.model.clients.MarketClient;
import client.model.user.User;
import client.view.screen.ExchangeScreen;
import client.view.screen.LoginScreen;
import client.view.screen.RegisterScreen;
import client.view.utils.SceneManager;

public class LoginController {
    /**
     * Schermata di login associata al controller.
     */
    private final LoginScreen loginScreen;

    /**
     * Gestore delle scene per lo switch tra view.
     */
    private final SceneManager sceneManager;

    /**
     * Costruisce il controller associandolo alla LoginScreen e allo SceneManager.
     *
     * @param loginScreen  istanza di LoginScreen
     * @param sceneManager istanza di SceneManager per il cambio scena
     */
    public LoginController(LoginScreen loginScreen, SceneManager sceneManager) {
        this.loginScreen = loginScreen;
        this.sceneManager = sceneManager;
        initialize();
    }

    /**
     * Inizializza i listener per il pulsante di login e il link di registrazione.
     */
    private void initialize() {
        // Listener per il click sul pulsante di login
        loginScreen.getLoginButton().setOnAction(event -> {
            try {
                handleLogin();
            } catch (IOException e) {
                System.err.println("Error during login: " + e.getMessage());
            }
        });

        // Listener per il click sul link di registrazione
        loginScreen.getRegisterLink().setOnAction(event -> switchToRegisterScreen());
    }

    /**
     * Gestisce la logica di login:
     * validazione input, invio richiesta e cambio view a
     * ExchangeScreen.
     *
     * @throws IOException
     */
    public void handleLogin() throws IOException {
        String username = loginScreen.getUsernameEntry();
        String password = loginScreen.getPasswordEntry();

        // Verifica input non vuoti
        if (username.isEmpty() || password.isEmpty()) {
            loginScreen.setErrorMessage("Input cannot be empty");
            return;
        }

        // Invio richiesta di login al server
        if (!LoginClient.sendLoginRequest(username, password)) {
            loginScreen.setErrorMessage("Login failed");
            return;
        }

        // Selezione della moneta iniziale dal mercato
        String initialCoin = MarketClient.getCoins()[0];

        // Creazione del modello utente e switch a ExchangeScreen
        User user = new User(username);
        switchToExchangeScreen(user, initialCoin);
    }

    /**
     * Passa alla schermata di registrazione.
     * Crea un nuovo RegisterController e cambia scena.
     */
    private void switchToRegisterScreen() {
        RegisterController registerController = new RegisterController(new RegisterScreen(), sceneManager);
        sceneManager.switchScene(registerController.getRegisterScreen(),
                "Register Screen", 300, 450, false);
    }

    /**
     * Passa alla schermata di exchange.
     * Crea un nuovo ExchangeController e cambia scena.
     *
     * @param user utente autenticato
     * @param coin moneta iniziale selezionata
     */
    private void switchToExchangeScreen(User user, String coin) {
        ExchangeController exchangeController = new ExchangeController(new ExchangeScreen(user, coin), sceneManager,
                user);
        sceneManager.switchScene(exchangeController.getExchangeScreen(),
                "Exchange simulator", 1280, 720, true);
    }

    /**
     * Fornisce l'istanza della LoginScreen.
     *
     * @return loginScreen
     */
    public LoginScreen getLoginScreen() {
        return loginScreen;
    }

    /**
     * Fornisce l'istanza dello SceneManager.
     *
     * @return sceneManager
     */
    public SceneManager getSceneManager() {
        return sceneManager;
    }
}