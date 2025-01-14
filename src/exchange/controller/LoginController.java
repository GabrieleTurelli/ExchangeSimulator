package controller;

import model.client.LoginModel;
import model.client.RegisterModel;
import view.screen.LoginScreen;
import view.screen.RegisterScreen;
import utils.SceneManager;

public class LoginController {
    private final LoginScreen loginScreen;
    private final LoginModel loginModel;
    private final SceneManager sceneManager;

    public LoginController(LoginScreen loginScreen, LoginModel loginModel, SceneManager sceneManager) {
        this.loginScreen = loginScreen;
        this.loginModel = loginModel;
        this.sceneManager = sceneManager;
        initialize();
    }

    private void initialize() {
        loginScreen.getLoginButton().setOnAction(event -> handleLogin());
        loginScreen.getRegisterLink().setOnAction(event -> switchToRegisterScreen());
    }

    public void handleLogin() {
        String username = loginScreen.getUsernameEntry();
        String password = loginScreen.getPasswordEntry();

        if (username.isEmpty() || password.isEmpty()){
            loginScreen.setErrorMessage("Input cannot be empty");
            return;
        }

        if (loginModel.userExists(username)) {
            if (loginModel.isPasswordCorrect(username, password)) {
                loginScreen.setErrorMessage("Correct");
                return;
            } else {
                loginScreen.setErrorMessage("Incorrect password");
                return;
            }
        }
        loginScreen.setErrorMessage("User does not exist");
    }



    private void switchToRegisterScreen() {
        RegisterScreen registerScreen = new RegisterScreen();
        RegisterModel registerModel = new RegisterModel(loginModel.getConnection());
        RegisterController registerController = new RegisterController(registerScreen, registerModel, sceneManager);
        sceneManager.switchScene(registerScreen, "Register Screen", 300, 400);
    }
}
