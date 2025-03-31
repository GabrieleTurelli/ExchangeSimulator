package client.view.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SceneManager {
    private static SceneManager instance;
    private final Stage primaryStage;

    public SceneManager(Stage stage) {
        this.primaryStage = stage;
    }

    public static void initialize(Stage stage) {
        instance = new SceneManager(stage);
    }

    public static SceneManager getInstance() {
        return instance;
    }

    public void switchScene(Parent root, String title, int width, int height, boolean resizable) {
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.setResizable(resizable);
        primaryStage.getIcons().add(new Image("/logo.png"));
        primaryStage.show();
    }

}
