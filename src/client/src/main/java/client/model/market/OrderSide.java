/**
 * Enum che rappresenta i lati di un ordine (acquisto o vendita).
 * Utilizzato per distinguere tra ordini di acquisto e di vendita.
 * 
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.market;

public enum OrderSide {
    BUY("Buy"),
    SELL("Sell");

    private final String value;

    OrderSide(String value) {
        this.value = value;
    }

    /**
     * Restituisce la stringa della side da salvare nel DB o mostrare all’utente.
     */
    public String getValue() {
        return value;
    }

    /**
     * Per chiamare side.toString()
     * 
     * @return il valore della stringa associata all'enum
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Converte una stringa in un'istanza di Side.
     * 
     * @param s la stringa da convertire in Side
     * @return l'istanza di Side corrispondente
     */
    public static OrderSide fromString(String s) {
        for (OrderSide side : values()) {
            if (side.value.equalsIgnoreCase(s)) {
                return side;
            }
        }
        throw new IllegalArgumentException("Unknown Side: " + s);
    }
}
