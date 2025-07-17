package server.model.market;

public enum Side {
    BUY("Buy"),
    SELL("Sell");

    private final String value;

    Side(String value) {
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
    public static Side fromString(String s) {
        for (Side side : values()) {
            if (side.value.equalsIgnoreCase(s)) {
                return side;
            }
        }
        throw new IllegalArgumentException("Unknown Side: " + s);
    }
}
