package client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import client.controller.LoginController;
import client.view.screen.LoginScreen;
import client.view.utils.SceneManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Thread serverThread;
    private final ArrayList<String> coins = new ArrayList<String>(Arrays.asList("BTC"));

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        SceneManager sceneManager = new SceneManager(primaryStage);
        LoginScreen loginScreen = new LoginScreen();
        new LoginController(loginScreen, sceneManager);

        primaryStage.setScene(new Scene(loginScreen, 300, 450));
        primaryStage.setTitle("Login Screen");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> stopServer());
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        stopServer();
        super.stop();
    }

    private void stopServer() {
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
