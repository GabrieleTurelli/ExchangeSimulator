package controller;

import javafx.scene.Parent;
import model.client.RegisterClient;
import view.screen.ExchangeScreen;
import view.screen.LoginScreen;
import view.screen.RegisterScreen;
import utils.SceneManager;

public class RegisterController {
    private final RegisterScreen registerScreen;
    private final SceneManager sceneManager;

    public RegisterController(RegisterScreen registerScreen,  SceneManager sceneManager) {
        this.registerScreen = registerScreen;
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

        if(password.length() < 8){
            registerScreen.setErrorMessage("At least 8 character");
            return;
        }

        String response = RegisterClient.sendRegisterRequest(username, password);
        String responseStatus = response.split(";")[0];
        String responseMessage = response.split(";")[1];

        if (responseStatus.equals("ERROR")){
            registerScreen.setErrorMessage(responseMessage);
            return;
        }
        switchToExchangeScreen();
    }


    private void switchToLoginScreen() {
        LoginController loginController = new LoginController(new LoginScreen(), sceneManager);
        switchToScreen(loginController.getLoginScreen(), "Login Screen", 300, 400, false);
    }

    private void switchToExchangeScreen(){
        switchToScreen(new ExchangeScreen(), "Exchange Screen", 1280, 720, true);

    }

    private void switchToScreen(Parent screen, String title, int width, int height, boolean resizable){

        sceneManager.switchScene(screen, title,width,height, resizable);

    }
    public RegisterScreen getRegisterScreen() {
        return registerScreen;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
