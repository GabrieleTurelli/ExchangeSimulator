package client.model.user;


public class User {
    private final String username;
    private Wallet wallet;

    public User( String username){
        this.username = username;
        this.wallet = new Wallet();
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

    public void createWalletFromString(String string){
        string = string.replaceAll("\\s+", "");
        String[] coins = string.split(",");

        for(String coin: coins){
            wallet.put(coin.split("=")[0], Double.parseDouble(coin.split("=")[1]));
        }
    }
}
