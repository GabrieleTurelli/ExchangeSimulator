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

public class ChartSection extends BaseSection {

    private CandleStickChart chart;

    public ChartSection(GridPane gridPane, Color fillColor, double widthMultiplier, double heightMultiplier) {
        super(gridPane, fillColor, widthMultiplier, heightMultiplier);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(10);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickMarkVisible(false);

        chart = new CandleStickChart(xAxis, yAxis);
        this.getChildren().add(chart);
    }

    public ChartSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
        this(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
    }

    public CandleStickChart getChart() {
        return chart;
    }

    public void updateDisplay(KlineHistory klineHistory) {
        chart.clearData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        List<XYChart.Data<String, Number>> dataPoints = new ArrayList<>();
        List<Kline> klines = new ArrayList<>();

        for (int i = 0; i < klineHistory.size(); i++) {
            Kline kline = klineHistory.get(i);
            klines.add(kline);

            LocalDate date = LocalDate.now().minusDays(klineHistory.size() - i - 1);
            String label = date.format(formatter);
            XYChart.Data<String, Number> data = new XYChart.Data<>(label, kline.getClose());
            data.setExtraValue(kline);
            dataPoints.add(data);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(dataPoints);
        chart.getData().add(series);

        chart.adjustYAxisWithPadding(klines, 2);
    }

}
