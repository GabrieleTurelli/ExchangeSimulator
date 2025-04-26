package server.model.scheduler;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class RandomGeneratorScheduler<K> {
    private final ScheduledExecutorService scheduler;
    protected final Random random;
    private final List<K> daos;


    public RandomGeneratorScheduler(Function<String, K> factory, String[] coins) {
        this.daos      = Arrays.stream(coins)
                               .map(factory)
                               .collect(Collectors.toList());
        this.random    = new Random();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    protected abstract void generateAndUpdate(K dao) throws Exception;

    public void start(long interval) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                for (K dao : daos) {
                    generateAndUpdate(dao);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, interval, TimeUnit.SECONDS);

    };

    public void stop() {
        scheduler.shutdown();
    };
};