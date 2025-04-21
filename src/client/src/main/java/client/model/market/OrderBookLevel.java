package client.model.market;

public class OrderBookLevel {
    private final Double price;
    private Double quantity;
    private Boolean isBid;

    public OrderBookLevel(Double price, Double quantity, Boolean isBid) {
        this.price = price;
        this.quantity = quantity;
        this.isBid = isBid;
    }

    public Double getPrice() {
        return price;
    }

    public Double getQuantity() {
        return quantity;
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
    public String toString(){
        return "price=" + this.getPrice() + ", quantity=" + this.getQuantity() + ", isBid="
                + this.getIsBid();

    }

}
