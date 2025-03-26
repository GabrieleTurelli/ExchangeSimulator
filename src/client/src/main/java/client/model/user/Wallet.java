package client.model.user;

import java.util.HashMap;

public class Wallet extends HashMap<String, Double> {

    public Wallet() {
        super();
    }

    public double getCoin(String coin){
        return this.get(coin);
    }

    public double getUsdt(){
        double amount = 0; 
        
        for (String key: this.keySet()){
            if(key.equals("USDT")){
                // amount += this.get(key) * PREZZO CORRENTE;
            }
        }

        return amount;
    }
}
