package client.model.market;

public class OrderBookLevelData {
    private final Double price;
    private Double quantity;
    private Boolean isBid;
    private Double amount;

    public OrderBookLevelData(Double price, Double quantity, Boolean isBid) {
        this.price = price;
        this.quantity = quantity;
        this.isBid = isBid;
        this.amount = price * quantity;
    }

    public void updateLevel(double qty) {
        quantity += qty;
        amount = (quantity * price);
    }

    public Double getPrice() {
        return price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public Boolean getIsBid() {
        return isBid;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setIsBid(Boolean isBid) {
        this.isBid = isBid;
    }

    @Override
    public String toString() {
        return "price=" + this.getPrice() + ", quantity=" + this.getQuantity() + ", isBid="
                + this.getIsBid();

    }

}
