package client.view.components.layout;

import java.text.DecimalFormat;

import client.model.market.DailyMarketData;
import client.view.components.ui.CoinMenu;
import client.view.components.ui.PriceLabel;
import client.view.components.ui.StatBlock;
import client.view.components.ui.VerticalSeparator;
import client.view.theme.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Sezione secondaria dell'interfaccia che mostra il dropdown menu
 * il prezzo attuale e statistiche di mercato delle ultime 24 ore.
 * 
 * @author Gabriele Turelli
 * @version 1.0
 */
public class SubHeaderSection extends BaseSection {
    private final PriceLabel priceLabel;
    private final StatBlock changeStatBlock;
    private final StatBlock lowStatBlock;
    private final StatBlock highStatBlock;
    private final CoinMenu coinMenu;

    private final DecimalFormat priceFormat = new DecimalFormat("#,##0.00");
    private final DecimalFormat percentFormat = new DecimalFormat("+#,##0.00'%';-#,##0.00'%'");

    /**
     * Costruisce la sub-header con il selettore di coppia, prezzo,
     * cambio percentuale e minimi/massimi 24h.
     * 
     * @param gridPane         il {@link GridPane} cui legare le dimensioni
     * @param widthMultiplier  fattore moltiplicativo applicato alla larghezza
     *                         del {@code gridPane}
     * @param heightMultiplier fattore moltiplicativo applicato all'altezza
     *                         del {@code gridPane}
     * @param coin             il simbolo della moneta (es. "BTC") da mostrare
     *                         nel {@link CoinMenu} con USDT
     */
    public SubHeaderSection(GridPane gridPane, double widthMultiplier, double heightMultiplier, String coin) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);

        this.priceLabel = new PriceLabel("-.--");
        this.changeStatBlock = new StatBlock("24h Change", "-.-- %", Theme.COLOR.TEXT_PRIMARY);
        this.lowStatBlock = new StatBlock("24h Low", "-.--", Theme.COLOR.TEXT_PRIMARY);
        this.highStatBlock = new StatBlock("24h High", "-.--", Theme.COLOR.TEXT_PRIMARY);

        HBox subHeaderContent = new HBox(10);
        subHeaderContent.setSpacing(10);
        subHeaderContent.setPadding(new Insets(10));
        subHeaderContent.setAlignment(Pos.CENTER_LEFT);

        this.coinMenu = new CoinMenu(coin + "/USDT");

        subHeaderContent.getChildren().addAll(
                this.coinMenu,
                new VerticalSeparator(20),
                createSpacer(10),
                priceLabel,
                createSpacer(40),
                this.changeStatBlock,
                createSpacer(10),
                this.lowStatBlock,
                createSpacer(10),
                this.highStatBlock);

        this.getChildren().add(subHeaderContent);
    }

    /**
     * Crea uno spacer orizzontale con larghezza prefissata.
     *
     * @param width la larghezza preferita del {@link Region} spacer
     * @return un {@link Region} usato come spazio vuoto nel layout
     */
    private Region createSpacer(double width) {
        Region spacer = new Region();
        spacer.setPrefWidth(width);
        return spacer;
    }

    /**
     * Aggiorna la visualizzazione dei dati di mercato.
     * Imposta prezzo attuale, variazione percentuale 24h,
     * minimo e massimo delle ultime 24h. In caso di dati null,
     * imposta testo di errore.
     *
     * @param data l'oggetto {@link DailyMarketData} contenente i dati di mercato
     */
    public void updateDisplay(DailyMarketData data) {
        if (data == null) {
            priceLabel.setText("Error");
            changeStatBlock.setStatBlock("24h Change", "Error", Theme.COLOR.RED);
            lowStatBlock.setStatBlock("24h Low", "Error", Theme.COLOR.TEXT_PRIMARY);
            highStatBlock.setStatBlock("24h High", "Error", Theme.COLOR.TEXT_PRIMARY);
            return;
        }

        priceLabel.setText(priceFormat.format(data.getPrice()));

        double changeValue = data.getDailyChange();
        Color changeColor = changeValue >= 0 ? Theme.COLOR.GREEN : Theme.COLOR.RED;
        changeStatBlock.setStatBlock("24h Change", percentFormat.format(changeValue), changeColor);

        lowStatBlock.setStatBlock("24h Low", priceFormat.format(data.getDailyLow()), Theme.COLOR.TEXT_PRIMARY);
        highStatBlock.setStatBlock("24h High", priceFormat.format(data.getDailyHigh()), Theme.COLOR.TEXT_PRIMARY);
    }

    /**
     * Restituisce il {@link CoinMenu} utilizzato per la selezione della coppia.
     *
     * @return il componente {@link CoinMenu}
     */
    public CoinMenu getCoinMenu() {
        return this.coinMenu;
    }

}
