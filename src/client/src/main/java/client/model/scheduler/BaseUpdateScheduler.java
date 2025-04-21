package client.model.scheduler;

import javafx.concurrent.ScheduledService;
import javafx.util.Duration;

public abstract class BaseUpdateScheduler<T> extends ScheduledService<T> {

    private final String label;

    protected BaseUpdateScheduler(String label, Duration updateInterval) {
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
