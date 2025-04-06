package client.view.components.ui.chart;

import java.util.ArrayList;
import java.util.List;

import client.model.market.KlineHistory;
import client.view.theme.Theme;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

public class Chart extends LineChart<String, Number> {

    private XYChart.Series<String, Number> series;
    private KlineHistory klineHistory;

    public Chart(CategoryAxis xAxis, NumberAxis yAxis) {
        super(xAxis, yAxis);

        series = new XYChart.Series<>();
        this.setLegendVisible(false);
        this.setAnimated(false);
        this.getData().add(series);

        Platform.runLater(() -> applyPlotBackground(Theme.COLOR.CHART_BACKGROUND));
    }

    public void adjustYAxisWithPadding(List<Number> yValues, double percentage) {
        if (yValues.isEmpty())
            return;

        double min = yValues.stream().mapToDouble(Number::doubleValue).min().orElse(0);
        double max = yValues.stream().mapToDouble(Number::doubleValue).max().orElse(0);

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

    public void addDataPoint(String x, Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

    public void addAllDataPoints(List<String> xValues, List<Number> yValues) {
        if (xValues.size() != yValues.size()) {
            throw new IllegalArgumentException("X and Y lists must be the same size.");
        }

        List<XYChart.Data<String, Number>> dataPoints = new ArrayList<>(xValues.size());
        for (int i = 0; i < xValues.size(); i++) {
            dataPoints.add(new XYChart.Data<>(xValues.get(i), yValues.get(i)));
        }

        series.getData().addAll(dataPoints);
        this.adjustYAxisWithPadding(yValues, 2);
    }

    public void clearData() {
        series.getData().clear();
    }

    private void applyPlotBackground(Color color) {
        Node plotBackground = this.lookup(".chart-plot-background");
        if (plotBackground != null) {
            plotBackground.setStyle("-fx-background-color: " + toHexString(color) + ";");
        }
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public void setKlineHistory(KlineHistory klineHistory) {
        this.klineHistory = klineHistory;
    }

    public KlineHistory getHistory() {
        return this.klineHistory;
    }
}
