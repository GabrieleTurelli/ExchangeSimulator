package client.model.user;

import java.io.IOException;

import client.model.clients.ClientConnection;

public class User {
    private final String username;
    private Wallet wallet;
    private final ClientConnection connection;

    public User(String username) throws IOException {
        this.username = username;
        this.wallet = new Wallet();
        this.connection = new ClientConnection();
    }

    public User(String username, String walletData) throws IOException {
        this.username = username;
        this.wallet = new Wallet(walletData);
        this.connection = new ClientConnection();
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
