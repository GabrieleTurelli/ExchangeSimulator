package client.model.clients;

import client.model.user.Wallet;

public class UserClient {

    public static Wallet getWallet() {
        return new Wallet();
    }
}
