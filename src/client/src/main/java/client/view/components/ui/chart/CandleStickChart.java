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

public class CandleStickChart extends XYChart<String, Number> {

    public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis) {
        super(xAxis, yAxis);
        setLegendVisible(false);
        setAnimated(false);
        setData(FXCollections.observableArrayList());
        Platform.runLater(() -> applyPlotBackground(Theme.COLOR.CHART_BACKGROUND));

    }

    @Override
    protected void layoutPlotChildren() {
        getPlotChildren().clear();

        for (Series<String, Number> series : getData()) {
            for (Data<String, Number> item : series.getData()) {
                double x = getXAxis().getDisplayPosition(item.getXValue());
                Kline kline = (Kline) item.getExtraValue();
                if (kline == null)
                    continue;

                double open = getYAxis().getDisplayPosition(kline.getOpen());
                double close = getYAxis().getDisplayPosition(kline.getClose());
                double high = getYAxis().getDisplayPosition(kline.getHigh());
                double low = getYAxis().getDisplayPosition(kline.getLow());
                Color color = kline.getClose() >= kline.getOpen() ? Theme.COLOR.GREEN : Theme.COLOR.RED;

                double candleWidth = 10;

                Line wick = new Line(x, high, x , low);
                wick.setFill(color);
                wick.setStroke(color);
                wick.setStrokeWidth(1);

                double bodyHeight = Math.abs(close - open);
                Rectangle body = new Rectangle(candleWidth, bodyHeight);
                body.setX(x - candleWidth / 2);
                body.setY(Math.min(open, close));
                Paint fillColor = color;
                body.setFill(fillColor);

                getPlotChildren().addAll(wick, body);
            }
        }

    }

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

    public void clearData() {
        if (getData() == null)
            return;
        getData().clear();
        getPlotChildren().clear();
    }

    private void applyPlotBackground(Color color) {
        Node plotBackground = this.lookup(".chart-plot-background");
        if (plotBackground != null) {
            plotBackground.setStyle("-fx-background-color: " + toHexString(color) + ";");
        }
    
        this.lookupAll(".chart-vertical-grid-lines").forEach(node ->
            node.setStyle("-fx-stroke: #444444; -fx-stroke-width: 0.5;")
        );
        this.lookupAll(".chart-horizontal-grid-lines").forEach(node ->
            node.setStyle("-fx-stroke: #444444; -fx-stroke-width: 0.5;")
        );
    }
    

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

}