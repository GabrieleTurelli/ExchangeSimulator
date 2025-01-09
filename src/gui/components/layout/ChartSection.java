package gui.components.layout;

import gui.components.ui.BarData;
import gui.components.ui.chart.Chart; // Assuming Chart is your LineChart
import gui.theme.Theme;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartSection extends BaseSection {

    private Chart chart;

    public ChartSection(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
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
        for (int i = 0; i < 10; i++) {
            bars.add(new BarData(new Date(now.getTime() + i * 60000), 200 + i, 205 + i, 195 + i, 202 + i));
        }
        return bars;
    }
}
