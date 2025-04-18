package server.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import server.model.db.CoinDAO;
import server.model.db.OrderBookDAO;
import server.model.market.Kline;

public class RandomPriceGeneratorService {
    private final ArrayList<CoinDAO> coinsDao;
    private final ArrayList<OrderBookDAO> orderBooksDao;
    private final Random random;
    private final ScheduledExecutorService scheduler;

    public RandomPriceGeneratorService(String[] coins) throws IOException, SQLException {
        this.coinsDao = new ArrayList<>();
        this.orderBooksDao = new ArrayList<>();
        for (String coin : coins) {
            this.coinsDao.add(new CoinDAO(coin));
            this.orderBooksDao.add(new OrderBookDAO(coin));
        }

        this.random = new Random();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    private void generateAndUpdatePrice(CoinDAO coinDao, OrderBookDAO orderBookDao) throws SQLException {
        double newPrice = generateAndUpdateCoinPrice(coinDao);
        generateAndUpdateOrderBookPrice(orderBookDao, newPrice);
    }

    private double generateAndUpdateCoinPrice(CoinDAO coinDao) throws SQLException {

        Kline currentKline = coinDao.getLastKline();
        double fluctuation = random.nextDouble() * 2 - 1;

        double currentOpen = currentKline.getOpen();
        double currentHigh = currentKline.getHigh();
        double currentLow = currentKline.getLow();
        double currentClose = currentKline.getClose();

        double newClose = currentClose * (1 + fluctuation / 100);
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

        return newClose;
    }

    private void generateAndUpdateOrderBookPrice(OrderBookDAO orderBookDao, double initialPrice) throws SQLException {
        orderBookDao.populateOrderBookTable(initialPrice, 10);

    }

    public void start(long interval) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                for (int i = 0; i < coinsDao.size(); i++) {
                    CoinDAO coinDao = coinsDao.get(i);
                    OrderBookDAO obDao = orderBooksDao.get(i);
                    generateAndUpdatePrice(coinDao, obDao);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 0, interval, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }

}
