/**
 * Rappresenta un singolo livello all'interno di un order book,
 * contenente un prezzo, una quantità e il lato dell'order (bid/ask).
 * @author Gabriele Turelli
 * @version 1.0
 * 
 */
package server.model.market;

public class OrderBookLevel {
    /** Prezzo dell'ordine */
    private final Double price;
    /** Quantità dell'ordine */
    private Double quantity;
    /** True se è un ordine di acquisto (bid), false se di vendita (ask) */
    private Boolean isBid;

    /**
     * Costruisce un nuovo livello di order book.
     *
     * @param price    prezzo a cui è posto l'ordine
     * @param quantity quantità disponibile a quel prezzo
     * @param isBid    true per bid (acquisto), false per ask (vendita)
     */
    public OrderBookLevel(Double price, Double quantity, Boolean isBid) {
        this.price = price;
        this.quantity = quantity;
        this.isBid = isBid;
    }

    /**
     * Restituisce il prezzo dell'ordine.
     *
     * @return prezzo dell'order book level
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Restituisce la quantità dell'ordine.
     *
     * @return quantità disponibile
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * Indica se il livello è un bid (true) o un ask (false).
     *
     * @return booleano che indica il lato dell'ordine
     */
    public Boolean getIsBid() {
        return isBid;
    }

    /**
     * Imposta una nuova quantità per questo livello.
     *
     * @param quantity nuova quantità da assegnare
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    /**
     * Imposta il lato dell'ordine (bid/ask).
     *
     * @param isBid true per bid, false per ask
     */
    public void setIsBid(Boolean isBid) {
        this.isBid = isBid;
    }

    /**
     * Restituisce una rappresentazione testuale del livello,
     * con prezzo, quantità e flag isBid separati da virgole.
     *
     * @return stringa descrittiva del livello di order book
     */
    @Override
    public String toString() {
        return "price=" + this.getPrice() +
               ", quantity=" + this.getQuantity() +
               ", isBid=" + this.getIsBid();
    }
}
