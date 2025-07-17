package client.view.components.layout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import client.model.market.Kline;
import client.model.market.KlineHistory;
import client.view.components.ui.chart.CandleStickChart;
import client.view.theme.Theme;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Sezione che mostra un grafico a candele (candlestick) dei prezzi storici.
 * <p>
 * Estende {@link BaseSection} fornendo uno sfondo e dimensionamento relativi
 * alle dimensioni di un {@link GridPane}, e contiene internamente un
 * {@link CandleStickChart} per il rendering dei dati.
 * </p>
 * 
 * @author Gabriele Turelli
 * @version 1.0
 */
public class ChartSection extends BaseSection {

    private CandleStickChart chart;

    /**
     * Crea una nuova sezione con grafico a candele, utilizzando un colore di
     * riempimento personalizzato.
     * 
     * @param gridPane         il {@link GridPane} cui legare le dimensioni della
     *                         sezione
     * @param fillColor        il colore di sfondo della sezione
     * @param widthMultiplier  fattore moltiplicativo applicato alla larghezza
     *                         del {@code gridPane}
     * @param heightMultiplier fattore moltiplicativo applicato all'altezza del
     *                         {@code gridPane}
     */
    public ChartSection(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);

        // Configurazione degli assi
        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(10);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickMarkVisible(false);

        // Inizializza il chart e lo aggiunge al pannello
        chart = new CandleStickChart(xAxis, yAxis);
        this.getChildren().add(chart);
    }

    /**
     * Crea una nuova sezione con grafico a candele, utilizzando il colore di
     * sfondo di default definito in {@link Theme.COLOR#BACKGROUND}.
     * 
     * @param gridPane         il {@link GridPane} cui legare le dimensioni della
     *                         sezione
     * @param widthMultiplier  fattore moltiplicativo applicato alla larghezza
     *                         del {@code gridPane}
     * @param heightMultiplier fattore moltiplicativo applicato all'altezza del
     *                         {@code gridPane}
     */
    public ChartSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        this(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }

    /**
     * Restituisce l'istanza interna del {@link CandleStickChart}.
     * 
     * @return il grafico a candele utilizzato in questa sezione
     */
    public CandleStickChart getChart() {
        return chart;
    }

    /**
     * Aggiorna il grafico con una nuova serie di dati storici.
     * <p>
     * Calcola le etichette in formato "MM-dd" a partire dalla data odierna meno
     * il numero di giorni storici, crea i punti dati impostando il valore di
     * chiusura e l'oggetto {@link Kline} come {@code extraValue}, quindi aggiunge
     * la serie al chart e adatta automaticamente l'intervallo dell'asse Y con
     * un padding specificato.
     * </p>
     * 
     * @param klineHistory la sequenza di {@link Kline} da visualizzare nel grafico
     */
    public void updateDisplay(KlineHistory klineHistory) {
        chart.clearData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        List<XYChart.Data<String, Number>> dataPoints = new ArrayList<>();
        List<Kline> klines = new ArrayList<>();

        // Costruisce i dati e le etichette per ogni kline
        for (int i = 0; i < klineHistory.size(); i++) {
            Kline kline = klineHistory.get(i);
            klines.add(kline);

            LocalDate date = LocalDate.now().minusDays(klineHistory.size() - i - 1);
            String label = date.format(formatter);
            XYChart.Data<String, Number> data = new XYChart.Data<>(label, kline.getClose());
            data.setExtraValue(kline);
            dataPoints.add(data);
        }

        // Crea la serie e la aggiunge al chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(dataPoints);
        chart.getData().add(series);

        // Adatta l'asse Y ai valori con padding
        chart.adjustYAxisWithPadding(klines, 2);
    }

}
