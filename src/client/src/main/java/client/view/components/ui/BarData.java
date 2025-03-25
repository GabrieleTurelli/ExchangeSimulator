package client.view.components.ui;

import java.util.Date;

public class BarData {
    private final Date dateTime;
    private final double open;
    private final double high;
    private final double low;
    private final double close;

    public BarData(Date dateTime, double open, double high, double low, double close) {
        this.dateTime = dateTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }
}
