package client.view.components.ui.chart;

import java.util.List;

import client.model.market.Kline;
import client.view.theme.Theme;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Grafico candlestick per la visualizzazione dei prezzi storici.
 * Estende {@link XYChart} con asse X String timestamp e asse Y numerico.
 * Ogni elemento della serie contiene un oggetto {@link Kline} come extraValue,
 * da cui vengono ricavate apertura, chiusura, massimo e minimo per disegnare
 * ogni candela composta da un "wick" (linea) e un "body" (rettangolo).
 * Inoltre, viene disegnata una linea orizzontale al valore di chiusura
 * dell'ultima candela e si offre un metodo per regolare manualmente il range
 * dell'asse Y con padding.
 * </p>
 * 
 * @author Gabriele Turelli
 * @version 1.0
 */
public class CandleStickChart extends XYChart<String, Number> {

    /**
     * Costruisce un CandlestickChart con gli assi forniti.
     * 
     * @param xAxis asse X categoriale per le date/label
     * @param yAxis asse Y numerico per i prezzi
     */
    public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis) {
        super(xAxis, yAxis);
        setLegendVisible(false);
        setAnimated(false);
        setData(FXCollections.observableArrayList());
        // Applica lo sfondo del plot su thread JavaFX
        Platform.runLater(() -> applyPlotBackground(Theme.COLOR.CHART_BACKGROUND));
    }

    /**
     * Layout di ogni candela: ricalcola e disegna wick e body per ogni dato
     * presente nella serie, e infine disegna una linea orizzontale al valore
     * di chiusura dell'ultima candela.
     */
    @Override
    protected void layoutPlotChildren() {
        getPlotChildren().clear();
        double open;
        double close = 0;
        double high;
        double low;
        Color color = Theme.COLOR.GREEN;

        for (Series<String, Number> series : getData()) {
            for (Data<String, Number> item : series.getData()) {
                double x = getXAxis().getDisplayPosition(item.getXValue());
                Kline kline = (Kline) item.getExtraValue();
                if (kline == null)
                    continue;

                open = getYAxis().getDisplayPosition(kline.getOpen());
                close = getYAxis().getDisplayPosition(kline.getClose());
                high = getYAxis().getDisplayPosition(kline.getHigh());
                low = getYAxis().getDisplayPosition(kline.getLow());
                color = kline.getClose() >= kline.getOpen() ? Theme.COLOR.GREEN : Theme.COLOR.RED;

                double candleWidth = 10;

                // Wick (linea verticale)
                Line wick = new Line(x, high, x, low);
                wick.setFill(color);
                wick.setStroke(color);
                wick.setStrokeWidth(1);

                // Body (rettangolo)
                double bodyHeight = Math.abs(close - open);
                Rectangle body = new Rectangle(candleWidth, bodyHeight);
                body.setX(x - candleWidth / 2);
                body.setY(Math.min(open, close));
                Paint fillColor = color;
                body.setFill(fillColor);

                getPlotChildren().addAll(wick, body);
            }
        }
        // Linea orizzontale all'ultimo prezzo di chiusura
        Line hLine = new Line();
        hLine.setStrokeWidth(1);
        hLine.setStroke(color);
        hLine.setStartX(0);
        hLine.setStartY(close);
        hLine.setEndX(getWidth());
        hLine.setEndY(close);
        getPlotChildren().add(hLine);
    }

    /**
     * Regola dinamicamente il range dell'asse Y con un padding percentuale sui minimi
     * e massimi storici forniti.
     * 
     * @param klines     lista di {@link Kline} da cui estrarre low e high
     * @param percentage percentuale di padding da applicare al range (es. 2 = 2%)
     */
    public void adjustYAxisWithPadding(List<Kline> klines, double percentage) {
        if (klines == null || klines.isEmpty())
            return;

        double min = klines.stream().mapToDouble(Kline::getLow).min().orElse(0);
        double max = klines.stream().mapToDouble(Kline::getHigh).max().orElse(0);

        double padding = (max - min) * (percentage / 100.0);
        double lowerBound = min - padding;
        double upperBound = max + padding;

        NumberAxis yAxis = (NumberAxis) getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(lowerBound);
        yAxis.setUpperBound(upperBound);
        yAxis.setTickUnit((upperBound - lowerBound) / 10);

        // Formatter per rimuovere decimali e mostrare solo interi
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, null, "") {
            @Override
            public String toString(Number object) {
                return String.format("%d", object.intValue());
            }
        });
    }

    @Override
    protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
    }

    @Override
    protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series) {
    }

    @Override
    protected void dataItemChanged(Data<String, Number> item) {
    }

    @Override
    protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
    }

    @Override
    protected void seriesRemoved(Series<String, Number> series) {
    }

    /**
     * Rimuove tutti i dati e i nodi grafici dal chart.
     */
    public void clearData() {
        if (getData() == null)
            return;
        getData().clear();
        getPlotChildren().clear();
    }

    /**
     * Applica lo stile di sfondo e delle griglie al plot del chart.
     * 
     * @param color colore di background
     */
    private void applyPlotBackground(Color color) {
        Node plotBackground = this.lookup(".chart-plot-background");
        if (plotBackground != null) {
            plotBackground.setStyle("-fx-background-color: " + toHexString(color) + ";");
        }

        this.lookupAll(".chart-vertical-grid-lines")
                .forEach(node -> node.setStyle("-fx-stroke: #444444; -fx-stroke-width: 0.5;"));
        this.lookupAll(".chart-horizontal-grid-lines")
                .forEach(node -> node.setStyle("-fx-stroke: #444444; -fx-stroke-width: 0.5;"));
    }

    /**
     * Converte un {@link Color} JavaFX in stringa esadecimale #RRGGBB.
     * 
     * @param color il colore da convertire
     * @return stringa esadecimale rappresentante il colore
     */
    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
