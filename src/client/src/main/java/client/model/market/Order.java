/**
 * Classe che rappresenta un ordine che verra inviato al server
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.market;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private String orderId;
    private OrderType orderType;
    private String coin;
    private double quantity;
    private double price;
    private double avgPrice;
    private OrderSide side;
    private Timestamp timestamp;

    public Order(String orderId, OrderType orderType, String coin, double quantity, double price, OrderSide side,
            double avgPrice) {
        this.orderId = UUID.randomUUID().toString();
        this.orderType = orderType;
        this.coin = coin;
        this.quantity = quantity;
        this.price = price;
        this.avgPrice = avgPrice;
        this.side = side;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return Double.compare(order.quantity, quantity) == 0
                && Double.compare(order.price, price) == 0
                && Objects.equals(orderId, order.orderId)
                && orderType == order.orderType
                && Objects.equals(coin, order.coin)
                && side == order.side
                && Objects.equals(timestamp, order.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderType, coin, quantity, price, side, timestamp);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderType=" + orderType +
                ", coin='" + coin + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", avgPrice=" + avgPrice +
                ", side=" + side +
                ", timestamp=" + timestamp +
                '}';
    }
}
