package client.view.components.layout;

import java.util.ArrayList;
import java.util.List;

import client.model.market.Kline;
import client.model.market.KlineHistory;
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
        this.getChildren().add(chart);
    }

    public ChartSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        this(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }

    public Chart getChart() {
        return chart;
    }

    public void updateDisplay(KlineHistory klineHistory) {
        chart.clearData();
        List<String> xValues = new ArrayList<>();
        List<Number> yValues = new ArrayList<>();
        for (int i = 0; i < klineHistory.size(); i++) {
            Kline kline = klineHistory.get(i);
            xValues.add(String.valueOf(i));
            yValues.add(kline.getClose());
        }

        chart.addAllDataPoints(xValues, yValues);
    }

}
