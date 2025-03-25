package client.view.components.layout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import client.view.components.ui.BarData;
import client.view.components.ui.chart.Chart;
import client.view.theme.Theme;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ChartSection extends BaseSection {

    private Chart chart;

    public ChartSection(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(10);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickMarkVisible(false);

        chart = new Chart(xAxis, yAxis);
        populateChartWithDummyData();
        this.getChildren().add(chart);
    }

    public ChartSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        this(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }

    public Chart getChart() {
        return chart;
    }

    private void populateChartWithDummyData() {
        List<BarData> data = generateDummyData();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for (BarData bar : data) {
            chart.addDataPoint(sdf.format(bar.getDateTime()), bar.getClose());
        }
    }

    private List<BarData> generateDummyData() {
        List<BarData> bars = new ArrayList<>();
        Date now = new Date();
        for (int i = 0; i < 20; i++) {
            bars.add(new BarData(new Date(now.getTime() + i * 60000), 200 + i, 205 + i, 195 + i, 202 + i));
        }
        return bars;
    }
}
