/**
 * Classe che rappresenta i dati di mercato giornalieri.
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.market;

public class DailyMarketData {

    private double price;
    private double dailyChange;
    private double dailyLow;
    private double dailyHigh;

    public DailyMarketData(double price, double dailyChange, double dailyLow, double dailyHigh) {
        this.price = price;
        this.dailyChange = dailyChange;
        this.dailyLow = dailyLow;
        this.dailyHigh = dailyHigh;
    }

    public DailyMarketData() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDailyChange() {
        return dailyChange;
    }

    public void setDailyChange(double dailyChange) {
        this.dailyChange = dailyChange;
    }

    public double getDailyLow() {
        return dailyLow;
    }

    public void setDailyLow(double dailyLow) {
        this.dailyLow = dailyLow;
    }

    public double getDailyHigh() {
        return dailyHigh;
    }

    public void setDailyHigh(double dailyHigh) {
        this.dailyHigh = dailyHigh;
    }

    @Override
    public String toString() {
        return "MarketData{" +
                "price=" + price +
                ", dailyChange=" + dailyChange +
                " dailyLow=" + dailyLow +
                ", dailyHigh=" + dailyHigh +
                '}';
    }

}
