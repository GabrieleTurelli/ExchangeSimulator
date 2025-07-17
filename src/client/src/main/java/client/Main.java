/**
 * Classe principale dell'applicazione JavaFX.
 * Avvia la GUI di login e gestisce la terminazione del thread di client.
 * 
 * @author Gabriele Turelli 
 * @version 1.0
 */
package client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.controller.LoginController;
import client.view.screen.LoginScreen;
import client.view.utils.SceneManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    // Executor per gestire eventuali task di rete o client
    private ExecutorService executor;

    /**
     * Metodo invocato da JavaFX per avviare l'interfaccia.
     * 
     * @param primaryStage Stage principale su cui montare le scene
     * @throws IOException  se si verificano errori di I/O
     * @throws SQLException se si verificano errori di accesso al database
     */
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        // Inizializza thread-pool con un singolo thread per il client
        executor = Executors.newSingleThreadExecutor();

        // Configura il gestore delle scene
        SceneManager sceneManager = new SceneManager(primaryStage);
        LoginScreen loginScreen = new LoginScreen();
        // @SuppressWarnings("unused")
        new LoginController(loginScreen, sceneManager);

        // mostra la scena di login
        primaryStage.setScene(new Scene(loginScreen, 300, 450));
        primaryStage.setTitle("Login Screen");
        primaryStage.setResizable(false);

        // quando viene chiuso lo stage, viene fermato anche la connessione del client
        primaryStage.setOnCloseRequest(event -> stopClient());
        primaryStage.show();

        logger.info("Applicazione avviata correttamente.");
    }

    /**
     * Metodo invocato alla chiusura dell'applicazione.
     * Si assicura che il client (se attivo) venga interrotto.
     */
    @Override
    public void stop() {
        // Termina il thread del client se attivo
        stopClient();
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Applicazione terminata.");
    }

    /**
     * Interrompe l'esecuzione del client se presente.
     */
    private void stopClient() {
        // Se l'executor è attivo, lo interrompe
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
            logger.debug("Client stop signal inviato.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
