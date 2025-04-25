package client.model.market;

public final class OrderBookLevelData {
    private final Double price;
    private final Double quantity;
    private final Boolean isBid;
    private final Double amount;

    public OrderBookLevelData(Double price, Double quantity, Boolean isBid) {
        this.price = price;
        this.quantity = quantity;
        this.isBid = isBid;
        this.amount = price * quantity;
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

    public Boolean isBid() {
        return isBid;
    }

    @Override
    public String toString() {
        return "price=" + this.getPrice() + ", quantity=" + this.getQuantity() + ", isBid="
                + this.isBid();

    }

}
