package client.model.user;

import java.io.IOException;

import client.model.ClientConnection;

public class User {
    private final String username;
    private Wallet wallet;
    private final ClientConnection connection;

    public User(String username) throws IOException {
        this.username = username;
        this.wallet = new Wallet();
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

    public void createWalletFromString(String string) {
        string = string.replaceAll("\\s+", "");
        String[] coins = string.split(",");

        for (String coin : coins) {
            wallet.put(coin.split("=")[0], Double.valueOf(coin.split("=")[1]));
        }
    }
}
