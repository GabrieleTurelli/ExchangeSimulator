package server.model.market;

import java.util.HashMap;

/**
 * Rappresenta una candela (kline) di prezzo, con valori di apertura, massima,
 * minima e chiusura. Estende HashMap per associare ogni tipo di valore al relativo
 * {@link KlineType}.
 */
public class Kline extends HashMap<KlineType, Double> {

    /**
     * Costruisce una nuova kline con i valori di open, high, low e close specificati.
     * 
     * @param open  prezzo di apertura
     * @param high  prezzo massimo
     * @param low   prezzo minimo
     * @param close prezzo di chiusura
     */
    public Kline(
            Double open,
            Double high,
            Double low,
            Double close) {
        super(); // Inizializza la mappa interna
        this.put(KlineType.OPEN, open);
        this.put(KlineType.HIGH, high);
        this.put(KlineType.LOW, low);
        this.put(KlineType.CLOSE, close);
    }

    /**
     * Restituisce il prezzo di apertura della candela.
     * 
     * @return prezzo open
     */
    public Double getOpen() {
        return this.get(KlineType.OPEN);
    }

    /**
     * Restituisce il prezzo massimo della candela.
     * 
     * @return prezzo high
     */
    public Double getHigh() {
        return this.get(KlineType.HIGH);
    }

    /**
     * Restituisce il prezzo minimo della candela.
     * 
     * @return prezzo low
     */
    public Double getLow() {
        return this.get(KlineType.LOW);
    }

    /**
     * Restituisce il prezzo di chiusura della candela.
     * 
     * @return prezzo close
     */
    public Double getClose() {
        return this.get(KlineType.CLOSE);
    }

    /**
     * Imposta un nuovo prezzo di apertura.
     * 
     * @param open nuovo valore open
     */
    public void setOpen(Double open) {
        this.put(KlineType.OPEN, open);
    }

    /**
     * Imposta un nuovo prezzo massimo.
     * 
     * @param high nuovo valore high
     */
    public void setHigh(Double high) {
        this.put(KlineType.HIGH, high);
    }

    /**
     * Imposta un nuovo prezzo minimo.
     * 
     * @param low nuovo valore low
     */
    public void setLow(Double low) {
        this.put(KlineType.LOW, low);
    }

    /**
     * Imposta un nuovo prezzo di chiusura.
     * 
     * @param close nuovo valore close
     */
    public void setClose(Double close) {
        this.put(KlineType.CLOSE, close);
    }

    /**
     * Restituisce una rappresentazione testuale della kline,
     * nel formato "open=X, high=Y, low=Z, close=W".
     * 
     * @return stringa di dettaglio dei valori
     */
    @Override
    public String toString() {
        return "open=" + this.getOpen() +
               ", high=" + this.getHigh() +
               ", low=" + this.getLow() +
               ", close=" + this.getClose();
    }
}
