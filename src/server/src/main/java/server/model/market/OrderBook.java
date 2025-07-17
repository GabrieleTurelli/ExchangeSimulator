package server.model.market;

import java.util.ArrayList;

/**
 * Rappresenta il libro degli ordini (order book) per una specifica moneta.
 * Estende ArrayList di {@link OrderBookLevel}, permettendo di memorizzare
 * livelli di prezzo di acquisto e vendita.
 */
public class OrderBook extends ArrayList<OrderBookLevel> {
    /**
     * Simbolo della moneta a cui si riferisce questo order book
     */
    private final String coin;

    /**
     * Costruisce un nuovo order book per la moneta specificata.
     *
     * @param coin simbolo della moneta (es. "BTC", "ETH")
     */
    public OrderBook(String coin) {
        super(); // Inizializza la lista interna di livelli
        this.coin = coin;
    }

    /**
     * Restituisce una rappresentazione testuale dell'order book.
     * Ogni livello è convertito in stringa e separato dal carattere '|'.
     *
     * @return stringa contenente tutti i livelli dell'order book
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Itera su ciascun livello e lo aggiunge alla stringa
        for (OrderBookLevel level : this) {
            sb.append(level.toString())
              .append("|"); // separatore tra i livelli
        }
        return sb.toString();
    }

    /**
     * Ottiene il simbolo della moneta associata a questo order book.
     *
     * @return il simbolo della moneta
     */
    public String getCoin() {
        return this.coin;
    }
}
