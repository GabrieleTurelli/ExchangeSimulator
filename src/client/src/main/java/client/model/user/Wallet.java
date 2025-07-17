/**
 * Rappresenta un portafoglio di coin e ne calcola il valore totale
 * in base ai prezzi correnti di mercato.
 * 
 * Estende {@link HashMap} per mappare ogni simbolo di coin su una quantità,
 * mantiene un campo {@code amount} che rappresenta il valore nella moneta collaterale (USDT)
 * calcolato in base ai prezzi ottenuti da {@link MarketClient}.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */

package client.model.user;

import java.util.HashMap;
import java.util.Map;

import client.model.clients.MarketClient;

public final class Wallet extends HashMap<String, Double> {

    /**
     * Valore complessivo del portafoglio, espresso nella valuta base (USDT).
     */
    private double amount;

    /**
     * Costruttore vuoto che inizializza un portafoglio senza monete.
     * Invoca il calcolo iniziale dell'importo (che sarà 0).
     */
    public Wallet() {
        super();
        calculateAmount();
    }

    /**
     * Costruisce un portafoglio a partire da una stringa formattata.
     * La stringa deve contenere coppie "simbolo=quantità" separate da virgole,
     * ad esempio "BTC=0.5,ETH=2,USDT=100".
     *
     * @param walletData stringa contenente i dati iniziali del portafoglio
     */
    public Wallet(String walletData) {
        super();
        createWalletFromString(walletData);
        calculateAmount();
    }

    /**
     * Aggiunge o sostituisce la quantità per una criptovaluta e ricalcola
     * l'importo.
     *
     * @param key   simbolo della criptovaluta (es. "BTC")
     * @param value quantità posseduta
     * @return la quantità precedentemente associata a quel simbolo, o {@code null}
     */
    @Override
    public Double put(String key, Double value) {
        Double previous = super.put(key, value);
        // Ricalcola il valore totale del portafoglio dopo l'aggiornamento
        calculateAmount();
        return previous;
    }

    /**
     * Aggiunge tutte le voci da un'altra mappa e ricalcola l'importo.
     *
     * @param m mappa contenente simboli e quantità da aggiungere
     */
    @Override
    public void putAll(Map<? extends String, ? extends Double> coinList) {
        super.putAll(coinList);
        // Ricalcola il valore totale dopo aver aggiunto tutte le coin
        calculateAmount();
    }

    /**
     * Rimuove una coin dal portafoglio e ricalcola il valore totale.
     *
     * @param key simbolo della coin da rimuovere
     * @return la quantità rimossa, o {@code null} se non presente
     */
    @Override
    public Double remove(Object key) {
        Double removed = super.remove(key);
        // Ricalcola il valore totale dopo la rimozione
        calculateAmount();
        return removed;
    }

    /**
     * Restituisce la quantità posseduta di una specifica criptovaluta.
     *
     * @param coin simbolo della criptovaluta
     * @return quantità posseduta, o 0.0 se non presente
     */
    public double getCoin(String coin) {
        return this.getOrDefault(coin, 0.0);
    }

    /**
     * Restituisce il valore totale del portafoglio.
     *
     * @return valore complessivo del portafoglio in USDT
     */
    public double getAmount() {
        // System.out.println("Getting the total amount: " + this.amount);
        return this.amount;
    }

    /**
     * Calcola il valore complessivo del portafoglio.
     * Per ogni coin diversa da USDT, recupera il prezzo corrente
     * tramite {@link MarketClient#getCoinPrice(String)} e somma
     * quantità * prezzo. USDT è considerato 1:1.
     */
    public void calculateAmount() {
        amount = 0;
        // Debug: elenco delle chiavi nel portafoglio
        System.out.println("Calculating amount for coins: " + this.keySet());

        for (String key : this.keySet()) {
            if ("USDT".equals(key)) {
                // USDT è moneta base, valore 1:1
                amount += this.get(key);
            } else {
                // Recupera il prezzo di mercato per la coin 
                double coinPrice = MarketClient.getCoinPrice(key);
                System.out.println(key + " Price: " + coinPrice);
                amount += this.get(key) * coinPrice;
            }
        }
        // Debug: mostra importo calcolato
        System.out.println("Total amount: " + amount);
    }

    /**
     * Imposta manualmente il valore complessivo del portafoglio.
     * <p>
     * Normalmente usato con cautela: non ricalcola le quantità interne.
     * </p>
     *
     * @param amount nuovo valore complessivo in USDT
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Costruisce il portafoglio a partire da una stringa di dati.
     * <p>
     * Rimuove spazi bianchi, suddivide per virgole e poi per '=' per
     * estrarre simbolo e quantità.
     * </p>
     *
     * @param string stringa formattata contenente i dati del portafoglio
     */
    private void createWalletFromString(String string) {
        // Rimuove eventuali spazi e split delle coppie
        string = string.replaceAll("\\s+", "");
        String[] coins = string.split(",");

        for (String coin : coins) {
            // Debug: mostra la coppia simbolo=quantità
            System.out.println("Parsing wallet entry: " + coin);
            String[] parts = coin.split("=");
            this.put(parts[0], Double.valueOf(parts[1]));
        }
    }

    /**
     * Rappresentazione in stringa del portafoglio.
     * <p>
     * Elenca tutte le criptovalute e la quantità, quindi aggiunge la voce "amount".
     * </p>
     *
     * @return stringa formattata con simboli, quantità e importo totale
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : this.keySet()) {
            sb.append(key)
                    .append("=")
                    .append(this.get(key))
                    .append(",");
        }
        sb.append("amount=").append(this.getAmount());
        return sb.toString();
    }
}
