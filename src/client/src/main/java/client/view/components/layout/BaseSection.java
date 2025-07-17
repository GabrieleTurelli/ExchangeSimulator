package client.view.components.layout;

import client.view.theme.Theme;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Gabriele Turelli
 * @version 1.0
 */
public class BaseSection extends StackPane {

    /**
     * Costruisce una sezione con colore di riempimento personalizzato.
     * Viene creato un {@link Rectangle} che si ridimensiona automaticamente in
     * base alla larghezza e all’altezza del {@code gridPane}, scalate dai
     * rispettivi moltiplicatori.
     * 
     * @param gridPane         il contenitore {@link GridPane} a cui legare le
     *                         dimensioni della sezione
     * @param fillColor        il colore di riempimento del background
     * @param widthMultiplier  il fattore moltiplicativo applicato alla larghezza
     *                         del {@code gridPane} per calcolare la larghezza del
     *                         background (es. 0.5 = metà larghezza)
     * @param heightMultiplier il fattore moltiplicativo applicato all’altezza del
     *                         {@code gridPane} per calcolare l’altezza del
     *                         background (es. 0.3 = 30% di altezza)
     */
    public BaseSection(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        Rectangle background = new Rectangle();
        background.setStrokeWidth(0.1);
        background.setStroke(Theme.COLOR.BORDER);
        background.setFill(fillColor);
        background.widthProperty().bind(gridPane.widthProperty().multiply(widthMultiplier));
        background.heightProperty().bind(gridPane.heightProperty().multiply(heightMultiplier));
        this.getChildren().addAll(background);
    }

    /**
     * Costruisce una sezione utilizzando il colore di sfondo di default
     * definito in {@link Theme.COLOR#BACKGROUND}.
     * È equivalente a chiamare
     * 
     * @param gridPane         il contenitore {@link GridPane} a cui legare le
     *                         dimensioni della sezione
     * @param widthMultiplier  il fattore moltiplicativo applicato alla larghezza
     *                         del {@code gridPane}
     * @param heightMultiplier il fattore moltiplicativo applicato all’altezza del
     *                         {@code gridPane}
     */
    public BaseSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        this(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }
}
