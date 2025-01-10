package model.user;

public class User {
    private final String userId;
    private final String name;
    private Wallet wallet;

    public User(String userId, String name){
        this.userId = userId;
        this.name = name;
        this.wallet = new Wallet();
    }
}
