/**
 * Controller per gestione della schermata di registrazione.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.controller;

import java.io.IOException;

import client.model.clients.RegisterClient;
import client.view.screen.LoginScreen;
import client.view.screen.RegisterScreen;
import client.view.utils.SceneManager;

public class RegisterController {
    /**
     * Schermata di registrazione associata al controller
     */
    private final RegisterScreen registerScreen;

    /**
     * Gestore delle scene per lo switch tra view.
     */
    private final SceneManager sceneManager;

    /**
     * Costruisce il controller associandolo al RegisterScreen.
     * 
     * @param registerScreen istanza di RegisterScreen
     * @param sceneManager   istanza di SceneManager per il cambio scena
     */
    public RegisterController(RegisterScreen registerScreen, SceneManager sceneManager) {
        this.registerScreen = registerScreen;
        this.sceneManager = sceneManager;
        initialize();
    }

    /**
     * IInizializza i listener per i componenti della schermata di registrazione.
     */
    private void initialize() {
        // Gestione click sul bottone di registrazione
        registerScreen.getRegisterButton().setOnAction(event -> {
            try {
                handleRegister();

            } catch (IOException e) {
                System.err.println("Error during registration: " + e.getMessage());
            }
        });

        // Gestione click sul link per passare al login
        registerScreen.getLoginLink().setOnAction(event -> {
            try {
                switchToLoginScreen();

            } catch (IOException e) {
                System.err.println("Error switching to login screen: " + e.getMessage());
            }
        });
    }

    /**
     * Gestisce la logica di registrazione:
     * validazione input, invio richiesta, creazione utente e cambio view a
     * LoginScreen.
     *
     * @throws IOException
     */
    private void handleRegister() throws IOException {
        String username = registerScreen.getUsernameEntry();
        String password = registerScreen.getPasswordEntry();
        String passwordConfirm = registerScreen.getConfirmPasswordEntry();

        // Verifica input non vuoti
        if (username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            registerScreen.setErrorMessage("Input cannot be empty");
            return;
        }

        // Verifica corrispondenza password
        if (!password.equals(passwordConfirm)) {
            registerScreen.setErrorMessage("Password doesn't match");
            return;
        }

        // Verifica lunghezza minima password
        if (password.length() < 8) {
            registerScreen.setErrorMessage("At least 8 character");
            return;
        }

        // Invio richiesta di registrazione al client
        String response = RegisterClient.sendRegisterRequest(username, password);
        String responseStatus = response.split(";")[0];
        String responseMessage = response.split(";")[1];

        // Gestione risposta di errore dal server
        if (responseStatus.equals("ERROR")) {
            registerScreen.setErrorMessage(responseMessage);
            return;
        }

        // In caso di successo, ritorna al login
        switchToLoginScreen();
    }

    /**
     * Passa alla schermata di login.
     * 
     * @throws IOException in caso di errore I/O durante la creazione della
     *                     schermata
     */
    private void switchToLoginScreen() throws IOException {
        LoginController loginController = new LoginController(new LoginScreen(), sceneManager);
        sceneManager.switchScene(loginController.getLoginScreen(), "Login Screen", 300, 450, false);
    }

    /**
     * Restituisce la schermata di registrazione associata.
     * 
     * @return l'istanza di RegisterScreen
     */
    public RegisterScreen getRegisterScreen() {
        return registerScreen;
    }

    /**
     * Restituisce il SceneManager utilizzato dal controller.
     * 
     * @return l'istanza di SceneManager
     */
    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
