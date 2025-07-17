/**
 * Classe che rappresenta una richiesta di ordine da inviare al server
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.market;

public class OrderRequest {
    public final String coin;
    public final OrderType type;
    public final OrderSide side;
    public final double amount;
    public final double price;

    public OrderRequest(String coin, OrderType type, OrderSide side, double amount, double price) {
        this.coin = coin;
        this.type = type;
        this.side = side;
        this.amount = amount;
        this.price = price;
    }

    public String getCoin() {
        return coin;
    }

    public OrderType getType() {
        return type;
    }

    public OrderSide getSide() {
        return side;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    
}
