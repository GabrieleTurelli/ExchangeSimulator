package client.model.user;

import java.util.HashMap;

import client.model.MarketClient;

public final class Wallet extends HashMap<String, Double> {
    private double amount;

    public Wallet() {
        super();
        calculateAmount();
    }

    @Override
    public Double put(String key, Double value) {
        Double result = super.put(key, value);
        calculateAmount();
        return result;
    }

    @Override
    public void putAll(java.util.Map<? extends String, ? extends Double> m) {
        super.putAll(m);
        calculateAmount();
    }

    @Override
    public Double remove(Object key) {
        Double result = super.remove(key);
        calculateAmount();
        return result;
    }

    public double getCoin(String coin) {
        return this.getOrDefault(coin, 0.0);
    }

    public double getAmount() {
        System.out.println("Getting the amount");
        System.out.println(this.amount);

        return this.amount;
    }

    public void calculateAmount() {
        amount = 0;
        System.out.println("Calculating the amount ");
        System.out.println(this.keySet());

        for (String key : this.keySet()) {
            if (key.equals("USDT")) {
                amount += this.get(key);
                continue;
            }

            double coinPrice = MarketClient.getCoinPrice(key);
            System.out.println(key + " Price: " + coinPrice);
            amount += this.get(key) * coinPrice;
        }

        System.out.println("Amount: " + amount);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
