package client.model.service;

import javafx.concurrent.ScheduledService;
import javafx.util.Duration;

public abstract class BaseUpdateService<T> extends ScheduledService<T> {

    private final String label;

    protected BaseUpdateService(String label, Duration updateInterval) {
        this.label = label;
        setPeriod(updateInterval);
        setRestartOnFailure(true);
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println(label + " update succeeded");
    }

    @Override
    protected void failed() {
        super.failed();
        Throwable ex = getException();
        System.out.println(label + " update failed\nException: " + (ex != null ? ex.getMessage() : "Unknown"));
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        System.out.println(label + " update service cancelled");
    }
}
