package server.model.market;

import java.util.ArrayList;

/**
 * Rappresenta la cronologia delle candele (klines) per un determinato asset.
 * Estende {@link ArrayList} per memorizzare sequenzialmente oggetti {@link Kline}.
 */
public class KlineHistory extends ArrayList<Kline> {

    /**
     * Costruisce una lista vuota di candele.
     */
    public KlineHistory() {
        super(); // Inizializza l'ArrayList interna
    }

    /**
     * Restituisce una rappresentazione testuale dell'intera cronologia delle candele.
     * Ogni kline viene convertita in stringa tramite il suo metodo {@code toString()}
     * ed è separata dal carattere '|'.
     *
     * @return stringa contenente tutte le kline della history
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Itera su tutte le candele presenti nella cronologia
        for (Kline kline : this) {
            sb.append(kline.toString())
              .append("|"); // aggiunge un separatore tra le candele
        }
        return sb.toString();
    }
}
