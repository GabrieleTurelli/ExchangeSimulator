package client.view.components.ui.orderbook;

public class OrderBookLevelData {
    private double price;
    private double size;
    private double sum;

    public OrderBookLevelData(double price, double size) {
        this.price = price;
        this.size = size;
        this.sum = size * price;
    }

    public void updateLevel(double qty){
        size += qty;
        sum += (size * price);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
