/**
 * Rappresenta un utente con uno username e un portafoglio (wallet).
 * Il wallet contiene le quantità di ciascuna moneta.
 */
package server.model.user;

public class User {
    /** Username univoco dell'utente */
    private final String username;
    /** Portafoglio dell'utente che contiene le quantità di ogni coin */
    private Wallet wallet;

    /**
     * Costruisce un nuovo utente con lo username specificato e un wallet vuoto.
     *
     * @param username lo username univoco dell'utente
     */
    public User(String username) {
        this.username = username;
        this.wallet = new Wallet();
    }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return lo username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Restituisce il portafoglio (wallet) dell'utente.
     *
     * @return il wallet corrente dell'utente
     */
    public Wallet getWallet() {
        return wallet;
    }

    /**
     * Imposta un nuovo wallet per l'utente.
     *
     * @param wallet il wallet da associare all'utente
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    /**
     * Inizializza il wallet dell'utente a partire da una stringa formattata.
     * Il formato atteso è: "COIN1=valore1,COIN2=valore2,..."
     * Spazi vengono ignorati.
     *
     * @param string stringa di input contenente monete e quantità
     */
    public void createWalletFromString(String string) {
        // Rimuove eventuali spazi nella stringa
        string = string.replaceAll("\\s+", "");
        // Divide in coppie "moneta=quantita"
        String[] coins = string.split(",");

        for (String coin : coins) {
            // Per ogni coppia, split su '=' per ottenere nome moneta e quantità
            String[] parts = coin.split("=");
            String coinSymbol = parts[0];
            double amount = Double.parseDouble(parts[1]);
            // Aggiunge nel wallet
            wallet.put(coinSymbol, amount);
        }
    }
}
