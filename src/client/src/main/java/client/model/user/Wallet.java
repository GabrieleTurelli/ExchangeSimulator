package client.model.user;

import java.util.HashMap;

import client.model.MarketClient;

public class Wallet extends HashMap<String, Double> {

    public Wallet() {
        super();
    }

    public double getCoin(String coin) {
        return this.get(coin);
    }

    public double getUsdt() {
        double amount = 0;

        for (String key : this.keySet()) {

            if (key.equals("USDT")) {
                amount += this.get(key);
                continue;
            }

            System.out.println(key);
            double coinPrice = MarketClient.getCoinPrice(key);
            System.out.println("Price: " + MarketClient.getCoinPrice(key));
            amount += this.get(key) * coinPrice;
        }

        System.out.println("Amount: " + amount);

        return amount;
    }
}
