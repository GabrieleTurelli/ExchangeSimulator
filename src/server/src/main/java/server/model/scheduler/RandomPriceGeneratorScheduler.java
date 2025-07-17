/**
 * Scheduler che genera periodicamente prezzi casuali per una serie di monete e aggiorna
 * sia la tabella dei kline che l'order book associato.
 * Utilizza un {@link ScheduledExecutorService} per eseguire il task a intervalli regolari.
 * @author Gabriele Turelli
 * @version 1.0
 */
package server.model.scheduler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import server.model.db.CoinDAO;
import server.model.db.OrderBookDAO;
import server.model.market.Kline;

public class RandomPriceGeneratorScheduler {
    private final ArrayList<CoinDAO> coinsDao;
    private final ArrayList<OrderBookDAO> orderBooksDao;
    private final Random random;
    private final ScheduledExecutorService scheduler;

    /**
     * Costruisce un nuovo scheduler per coin
     * 
     * @param coins      array di coin da gestire
     * @param connection connessione al database da utilizzare per i DAO
     * @throws IOException  se si verifica un errore di I/O nella creazione dei DAO
     * @throws SQLException se si verifica un errore SQL nella creazione dei DAO
     */
    public RandomPriceGeneratorScheduler(String[] coins, Connection connection) throws IOException, SQLException {
        this.coinsDao = new ArrayList<>();
        this.orderBooksDao = new ArrayList<>();
        for (String coin : coins) {
            // Inizializza DAO per ogni coin
            this.coinsDao.add(new CoinDAO(coin, connection));
            this.orderBooksDao.add(new OrderBookDAO(coin, connection));
        }

        this.random = new Random();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Genera e aggiorna il prezzo e l'orderbook di una coin
     * 
     * @param coinDao      DAO per accedere alla tabella kline
     * @param orderBookDao DAO per accedere alla tabella order book
     * @throws SQLException se si verifica un errore nell'aggiornamento del database
     */
    private void generateAndUpdatePrice(CoinDAO coinDao, OrderBookDAO orderBookDao) throws SQLException {
        // Genera nuovo prezzo close e aggiorna kline
        double newPrice = generateAndUpdateCoinPrice(coinDao);
        // Popola order book con il nuovo prezzo
        generateAndUpdateOrderBookPrice(orderBookDao, newPrice);
    }

    /**
     * Calcola un nuovo prezzo di chiusura random, aggiorna i valori di high e
     * low se necessari e salva il nuovo kline nel database.
     * 
     * @param coinDao DAO per leggere e scrivere il kline corrente
     * @return il nuovo prezzo di chiusura calcolato
     * @throws SQLException se l'update del kline fallisce
     */
    private double generateAndUpdateCoinPrice(CoinDAO coinDao) throws SQLException {
        // Ottiene l'ultima kline (quella attuale)
        Kline currentKline = coinDao.getLastKline();
        // Fluttuazione percentuale casuale tra -0.1% e +0.1%
        double fluctuation = random.nextDouble() * 2 - 1;

        double currentOpen = currentKline.getOpen();
        double currentHigh = currentKline.getHigh();
        double currentLow = currentKline.getLow();
        double currentClose = currentKline.getClose();

        // Calcolo del nuovo prezzo di chiusura (prezzo corrente se la candela e' ancora
        // aperta)
        double newClose = currentClose * (1 + fluctuation / 100);
        newClose = Math.round(newClose * 100.0) / 100.0;

        // Aggiorna high e low se il nuovo prezzo fa registrare un nuovo massimo o
        // minimo
        if (newClose > currentHigh) {
            currentHigh = newClose;
        } else if (newClose < currentLow) {
            currentLow = newClose;
        }

        Kline kline = new Kline(currentOpen, currentHigh, currentLow, newClose);

        try {
            // Scrive il kline aggiornato nel database
            coinDao.updateKline(kline);
            System.out.println("Updated kline: " + kline);
        } catch (Exception e) {
            // Gestione delle eccezioni di update
            System.err.println("Error updating kline: " + e.getMessage());
        }

        return newClose;
    }

    /**
     * Popola la tabella dell'order book con ordini di acquisto e vendita attorno al
     * prezzo fornito (solo 10 livelli per avere meno calcoli da fare).
     * 
     * @param orderBookDao DAO per popolare l'order book
     * @param initialPrice prezzo di riferimento per gli ordini di mercato
     * @throws SQLException se la scrittura nel database fallisce
     */
    private void generateAndUpdateOrderBookPrice(OrderBookDAO orderBookDao, double initialPrice) throws SQLException {
        // Genera 10 livelli di prezzo nell'order book
        orderBookDao.populateOrderBookTable(initialPrice, 10);
    }

    /**
     * Avvia lo scheduler per eseguire il task di generazione prezzi ad intervalli
     * regolari.
     * 
     * @param interval intervallo di esecuzione in secondi
     */
    public void start(long interval) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                for (int i = 0; i < coinsDao.size(); i++) {
                    CoinDAO coinDao = coinsDao.get(i);
                    OrderBookDAO obDao = orderBooksDao.get(i);
                    generateAndUpdatePrice(coinDao, obDao);
                }
            } catch (SQLException e) {
                System.err.println("Error generating random price: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        },
                0,
                interval,
                TimeUnit.SECONDS);
    }

    /**
     * Ferma lo scheduler e interrompe ulteriori esecuzioni.
     */
    public void stop() {
        scheduler.shutdown();
    }
}