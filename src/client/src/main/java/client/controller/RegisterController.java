package client.controller;

import java.io.IOException;

import client.model.RegisterClient;
import client.view.screen.ExchangeScreen;
import client.view.screen.LoginScreen;
import client.view.screen.RegisterScreen;
import client.view.utils.SceneManager;
import javafx.scene.Parent;

public class RegisterController {
    private final RegisterScreen registerScreen;
    private final SceneManager sceneManager;

    public RegisterController(RegisterScreen registerScreen,  SceneManager sceneManager) {
        this.registerScreen = registerScreen;
        this.sceneManager = sceneManager;
        initialize();
    }

    private void initialize()  {
        registerScreen.getRegisterButton().setOnAction(event -> handleRegister());
        registerScreen.getLoginLink().setOnAction(event -> {
            try {
                switchToLoginScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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


    private void switchToLoginScreen() throws IOException {
        LoginController loginController;
        loginController = new LoginController(new LoginScreen(), sceneManager);
        switchToScreen(loginController.getLoginScreen(), "Login Screen", 300, 450, false);
    }

    private void switchToExchangeScreen(){
        switchToScreen(new ExchangeScreen(), "Exchange simulator", 1280, 720, true);

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
