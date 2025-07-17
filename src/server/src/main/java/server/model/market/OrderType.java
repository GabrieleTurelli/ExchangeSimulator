package server.model.market;


public enum OrderType {
    MARKET("market"),
    LIMIT("limit");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    /**
     * Restituisce la stringa da salvare nel DB o mostrare all’utente.
     */
    public String getValue() {
        return value;
    }

    /**
     * Per chiamare orderType.toString()
     * 
     * @return il valore della stringa associata all'enum
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Converte una stringa in un'istanza di OrderType.
     * 
     * @param s la stringa da convertire in OrderType
     * @return l'istanza di OrderType corrispondente
     */
    public static OrderType fromString(String s) {
        for (OrderType ot : values()) {
            if (ot.value.equalsIgnoreCase(s)) {
                return ot;
            }
        }
        throw new IllegalArgumentException("Unknown OrderType: " + s);
    }
}
