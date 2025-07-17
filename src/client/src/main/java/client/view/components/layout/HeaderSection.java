package client.view.components.layout;

import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Sezione di intestazione dell'applicazione, che mostra logo, titolo centrale
 * e area account a destra, il tutto inserito in un background colorato e
 * ridimensionabile in base al layout a griglia.
 * 
 * @author Gabriele
 * @version 1.0
 */
public class HeaderSection extends BaseSection {

    /**
     * 
     * @param gridPane         il {@link GridPane} cui legare le dimensioni della
     *                         sezione
     * @param logoPath         il path dell'immagine del
     *                         logo da visualizzare
     * @param Title            il testo del titolo da mostrare accanto al logo
     * @param fillColor        il colore di riempimento dello sfondo della sezione
     * @param widthMultiplier  fattore moltiplicativo applicato alla larghezza
     *                         del {@code gridPane} per dimensionare il background
     * @param heightMultiplier fattore moltiplicativo applicato all'altezza del
     *                         {@code gridPane} per dimensionare il background
     */
    public HeaderSection(GridPane gridPane, String logoPath, String Title, Color fillColor, double widthMultiplier,
            double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);

        // Logo immagine
        ImageView logo = new ImageView(logoPath);
        logo.setFitHeight(20);
        logo.setPreserveRatio(true);

        // Testo titolo
        Text title = new Text(Title);
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Verdana", 18));

        // Contenitore per logo + titolo
        HBox titleContent = new HBox(10);
        titleContent.getChildren().addAll(logo, title);
        titleContent.setAlignment(Pos.CENTER);
        titleContent.setPadding(new Insets(0, 0, 0, 50));

        // Pannello per area account (vuoto di default)
        HBox accountContent = new HBox();
        accountContent.setAlignment(Pos.CENTER_RIGHT);
        accountContent.setPadding(new Insets(0, 20, 0, 0));

        // BorderPane per posizionare centro e destra
        BorderPane headerLayout = new BorderPane();
        headerLayout.setCenter(titleContent);
        headerLayout.setRight(accountContent);

        this.getChildren().add(headerLayout);
    }

    /**
     * Costruisce una sezione di header utilizzando il colore di sfondo di default
     * {@link Theme.COLOR#BACKGROUND}.
     * 
     * @param gridPane         il {@link GridPane} cui legare le dimensioni della
     *                         sezione
     * @param logoPath         il percorso (URI o file system) dell'immagine del
     *                         logo da visualizzare
     * @param Title            il testo del titolo da mostrare accanto al logo
     * @param widthMultiplier  fattore moltiplicativo applicato alla larghezza
     *                         del {@code gridPane}
     * @param heightMultiplier fattore moltiplicativo applicato all'altezza del
     *                         {@code gridPane}
     */
    public HeaderSection(GridPane gridPane, String logoPath, String Title, double widthMultiplier,
            double heightMultiplier) {
        this(gridPane, logoPath, Title, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }
}
