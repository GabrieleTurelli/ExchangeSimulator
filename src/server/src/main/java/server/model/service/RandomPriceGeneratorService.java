package server.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import server.model.coin.Kline;
import server.model.db.CoinDAO;

public class RandomPriceGeneratorService {
    private final CoinDAO coinDao;
    private final Random random;
    private final ScheduledExecutorService scheduler;

    public RandomPriceGeneratorService(String coin) throws IOException, SQLException {
        this.coinDao = new CoinDAO(coin);
        this.random = new Random();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

    }

    private void generateAndUpdatePrice() throws SQLException {
        Kline currentKline = coinDao.getLastKline();

        double currentOpen = currentKline.getOpen();
        double currentHigh = currentKline.getHigh();
        double currentLow = currentKline.getLow();
        double currentClose = currentKline.getClose();

        double newClose = currentClose * (1 + (-1 + 2 * random.nextDouble()) / 1000);

        if (newClose > currentHigh) {
            currentHigh = newClose;
        } else if (newClose < currentLow) {
            currentLow = newClose;
        }

        Kline kline = new Kline(currentOpen, currentHigh, currentLow, newClose);

        try {
            coinDao.updateKline(kline);
            System.out.println("Updated kline: " + kline);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(long interval) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                generateAndUpdatePrice();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }, 0, interval, TimeUnit.SECONDS);

    }

    public void stop() {
        scheduler.shutdown();
    }

}
