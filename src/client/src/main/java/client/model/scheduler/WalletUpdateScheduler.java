package client.model.scheduler;

import client.model.clients.UserClient;
import client.model.user.Wallet;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class WalletUpdateScheduler extends BaseUpdateScheduler<Wallet> {

    private final String username;

    public WalletUpdateScheduler(Duration updateInterval, String username) {
        super("Wallet for " + username, updateInterval);
        this.username = username;
    }

    @Override
    protected Task<Wallet> createTask() {
        return new Task<>() {
            @Override
            protected Wallet call() throws Exception {
                System.out.println("Fetching wallet for " + username);
                try {
                    Wallet wallet = UserClient.getWallet(username);
                    if (wallet == null) {
                        throw new Exception("Null data from UserClient for wallet");
                    }
                    updateMessage("Last update: " + java.time.LocalTime.now());
                    return wallet;
                } catch (Exception e) {
                    updateMessage("Error fetching data: " + e.getMessage());
                    throw e;
                }
            }
        };
    }
}
