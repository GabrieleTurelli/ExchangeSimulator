package controller;

import model.client.LoginModel;
import model.client.RegisterModel;
import view.screen.LoginScreen;
import view.screen.RegisterScreen;
import utils.SceneManager;

public class RegisterController {
    private final RegisterScreen registerScreen;
    private final RegisterModel registerModel;
    private final SceneManager sceneManager;

    public RegisterController(RegisterScreen registerScreen, RegisterModel registerModel, SceneManager sceneManager) {
        this.registerScreen = registerScreen;
        this.registerModel = registerModel;
        this.sceneManager = sceneManager;
        initialize();
    }

    private void initialize() {
        registerScreen.getRegisterButton().setOnAction(event -> handleRegister());
        registerScreen.getLoginLink().setOnAction(event -> switchToLoginScreen());
    }

    private void handleRegister(){
        String username = registerScreen.getUsernameEntry();
        String password = registerScreen.getPasswordEntry();
        String passwordConfirm = registerScreen.getConfirmPasswordEntry();

        if (username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()){
            registerScreen.setErrorMessage("Input cannot be empty");
            return;
        }

        if (!password.equals(passwordConfirm)){
            registerScreen.setErrorMessage("Password doesn't match");
            return;
        }

        if (registerModel.userExists(username)){
            registerScreen.setErrorMessage("Username already taken");
            return;
        }

        registerModel.addUser(username, password);

    }

    private void switchToLoginScreen() {
        LoginScreen loginScreen = new LoginScreen();
        LoginModel loginModel = new LoginModel(registerModel.getConnection());
        LoginController loginController = new LoginController(loginScreen, loginModel, sceneManager);
        sceneManager.switchScene(loginScreen, "Login Screen", 300, 400);
    }
}
