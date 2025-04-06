package client.model.service;

import client.model.clients.UserClient;
import client.model.user.Wallet;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class WalletUpdateService extends ScheduledService<Wallet> {
    private final String username;

    public WalletUpdateService(Duration updateInterval, String username) {
        setPeriod(updateInterval);
        setRestartOnFailure(true);
        this.username = username;
    }

    @Override
    protected Task<Wallet> createTask() {
        return new Task<>() {
            @Override
            protected Wallet call() throws Exception {
                System.out.println("Fetching wallet");
                try {
                    Wallet wallet = UserClient.getWallet(username);
                    if (wallet == null) {
                        throw new Exception("Received null data from UserClient for wallet upate ");
                    }
                    System.out.println("Successfully fetched wallet:" + wallet);
                    updateMessage("Last update: " + java.time.LocalTime.now());
                    return wallet;
                } catch (Exception e) {
                    System.out.println("Failed to fetch wallet: " + e.getMessage());
                    updateMessage("Error fetching data: " + e.getMessage());
                    throw e;
                }
            }
        };
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("Wallet update succeeded");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("Wallet update failed,\n Exception:" + getException() != null
                ? getException().getMessage()
                : "Unknown");
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        System.out.println("Wallet update service cancelled");
    }
}