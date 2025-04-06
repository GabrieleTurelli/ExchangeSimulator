package server.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import server.model.db.CoinDAO;
import server.model.market.Kline;

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
        double fluctuation = random.nextDouble() * 2 - 1; 
        
        
        double currentOpen = currentKline.getOpen();
        double currentHigh = currentKline.getHigh();
        double currentLow = currentKline.getLow();
        double currentClose = currentKline.getClose();
        
        double newClose = currentClose + fluctuation;
        newClose = Math.round(newClose * 100.0) / 100.0;

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
