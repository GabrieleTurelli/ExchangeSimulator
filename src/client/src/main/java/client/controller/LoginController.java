package client.controller;

import java.io.IOException;

import client.controller.exchangecontroller.ExchangeController;
import client.model.clients.LoginClient;
import client.model.user.User;
import client.view.screen.ExchangeScreen;
import client.view.screen.LoginScreen;
import client.view.screen.RegisterScreen;
import client.view.utils.SceneManager;
import javafx.scene.Parent;

public class LoginController {
    private final LoginScreen loginScreen;
    private final SceneManager sceneManager;

    public LoginController(LoginScreen loginScreen, SceneManager sceneManager) {
        this.loginScreen = loginScreen;
        this.sceneManager = sceneManager;
        initialize();
    }

    private void initialize() {
        loginScreen.getLoginButton().setOnAction(event -> {
            try {
                handleLogin();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        loginScreen.getRegisterLink().setOnAction(event -> switchToRegisterScreen());
    }

    public void handleLogin() throws IOException {
        String username = loginScreen.getUsernameEntry();
        String password = loginScreen.getPasswordEntry();

        if (username.isEmpty() || password.isEmpty()) {
            loginScreen.setErrorMessage("Input cannot be empty");
            return;
        }
        String response = LoginClient.sendLoginRequest(username, password);
        String responseStatus = response.split(";")[0];
        String responseMessage = response.split(";")[1];
        System.out.println(responseStatus);
        System.out.println(responseMessage);

        if (responseStatus.equals("ERROR")) {
            loginScreen.setErrorMessage(responseMessage);
            return;
        }
        User user = new User(username);
        switchToExchangeScreen(user);
    }

    private void switchToRegisterScreen() {
        RegisterController registerController = new RegisterController(new RegisterScreen(), sceneManager);
        switchToScreen(registerController.getRegisterScreen(), "Register Screen", 300, 450, false);
    }

    private void switchToExchangeScreen(User user) {
        ExchangeController exchangeController = new ExchangeController(new ExchangeScreen(user, "BTC"), sceneManager,
                user);
        switchToScreen(exchangeController.getExchangeScreen(), "Exchange simulator", 1280, 720, true);
    }

    private void switchToScreen(Parent screen, String title, int width, int height, boolean resizable) {
        sceneManager.switchScene(screen, title, width, height, resizable);

    }

    public LoginScreen getLoginScreen() {
        return loginScreen;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
