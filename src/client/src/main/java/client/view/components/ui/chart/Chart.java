package client.view.components.ui.chart;

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

    public Chart(CategoryAxis xAxis, NumberAxis yAxis) {
        super(xAxis, yAxis);

        series = new XYChart.Series<>();
        this.setLegendVisible(false);
        this.getData().add(series);

        Platform.runLater(() -> applyPlotBackground(Theme.COLOR.CHART_BACKGROUND)) ;
    }

    public void addDataPoint(String x, Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
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
}
