/**
 * Utility per la gestione delle scene 
 * Permette di cambiare la scena visualizzata sullo stage principale (Passare da loginscreen a main screen, etc),
 * impostando root, titolo, dimensioni, ridimensionabilità e icona.
 *
 * Usage:
 *   SceneManager sceneManager = new SceneManager(stage);
 *   sceneManager.switchScene(rootNode, "Titolo", 800, 600, true);
 *
 * @author Gabriele Turelli 
 * @version 1.0
 */
package client.view.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SceneManager {
    /**
     * Stage principale su cui montare le scene.
     */
    private final Stage primaryStage;

    /**
     * Costruttore che prende in input lo stage principale dell'applicazione.
     *
     * @param stage stage fornito da JavaFX nel metodo start()
     */
    public SceneManager(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Cambia la scena corrente dello stage principale.
     * Crea una nuova Scene con il nodo specificato e applica titolo,
     * dimensioni, possibilità di ridimensionamento e icona.
     *
     * @param root nodo della nuova scena (layout JavaFX)
     * @param title titolo della finestra
     * @param width larghezza finestra in pixel
     * @param height altezza finestra in pixel
     * @param resizable true per permettere il ridimensionamento, false altrimenti (usato per bloccare la dimensione nel case del login screen)
     */
    public void switchScene(Parent root, String title, int width, int height, boolean resizable) {
        // Crea la scena con le dimensioni specificate
        Scene scene = new Scene(root, width, height);
        
        // Applica la scena e le proprietà allo stage
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.setResizable(resizable);
        
        // Imposta l'icona della finestra (risorsa logo.png nella cartella resources)
        primaryStage.getIcons().add(new Image("/logo.png"));
        
        // Mostra lo stage (utile se era nascosto)
        primaryStage.show();
    }
}
