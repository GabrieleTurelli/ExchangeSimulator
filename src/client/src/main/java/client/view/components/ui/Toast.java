package client.view.components.ui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast {

    private static final double DEFAULT_PADDING = 5.0;

    /**
     * Mostra un toast in alto a destra con padding dall'edge della Stage.
     *
     * @param ownerStage la Stage su cui ancorare il toast
     * @param message    il testo da mostrare
     * @param duration   durata visibile (in secondi)
     */
    public static void show(Stage ownerStage, String message, double duration) {
        show(ownerStage, message, duration, DEFAULT_PADDING);
    }

    /**
     * toast di 3 secondi in alto a destra.
     */
    public static void show(Stage ownerStage, String message) {
        show(ownerStage, message, 2.0, DEFAULT_PADDING);
    }

    /**
     * posiziona il toast in alto a destra della Stage con il padding
     * dato.
     */
    private static void show(Stage ownerStage, String message, double duration, double padding) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);

        Label label = new Label(message);
        label.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-padding: 10 20 10 20;" +
                        "-fx-background-color: rgb(15, 15, 15);" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.5);" +
                        "-fx-border-radius: 10;");

        StackPane root = new StackPane(label);
        root.setPadding(new Insets(10));
        popup.getContent().add(root);

        // Forza il layout per calcolare le dimensioni
        root.applyCss();
        root.layout();
        double popupWidth = root.getWidth();

        // Calcola posizione: in alto a destra con padding
        double x = ownerStage.getX() + ownerStage.getWidth() - 2 * popupWidth - padding;
        double y = ownerStage.getY() + ownerStage.getHeight() - padding;
        popup.show(ownerStage, x, y);

        // Dopo `duration` secondi, fade out e nascondi
        PauseTransition wait = new PauseTransition(Duration.seconds(duration));
        wait.setOnFinished(e -> {
            FadeTransition fade = new FadeTransition(Duration.seconds(0.5), root);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e2 -> popup.hide());
            fade.play();
        });
        wait.play();
    }
}
