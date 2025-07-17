/**
 * Classe che rappresenta un utente 
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.user;

import java.io.IOException;

public class User {
    private final String username;
    private Wallet wallet;

    public User(String username) throws IOException {
        this.username = username;
        this.wallet = new Wallet();
    }

    public User(String username, String walletData) throws IOException {
        this.username = username;
        this.wallet = new Wallet(walletData);
    }

    public String getUsername() {
        return username;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
