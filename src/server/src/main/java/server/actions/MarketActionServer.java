package server.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import server.model.db.OrderBookDAO;
import server.model.db.UserDAO;
import server.model.market.OrderBook;
import server.model.market.OrderBookLevel;

public class MarketActionServer {
    private final Connection connection;

    public MarketActionServer(Connection connection) {
        this.connection = connection;
    }

    public String handleBuyMarket(String request) throws SQLException, IOException {
        String[] parts = request.split(" ");
        String username = parts[1];
        String coin = parts[2];
        double amount = Double.parseDouble(parts[3]);
        return executeMarketOrder(username, coin, amount, true);
    }

    public String handleSellMarket(String request) throws SQLException, IOException {
        String[] parts = request.split(" ");
        String username = parts[1];
        String coin = parts[2];
        double amount = Double.parseDouble(parts[3]);
        return executeMarketOrder(username, coin, amount, false);
    }

    public String handleBuyLimit(String request) {
        throw new UnsupportedOperationException("Buy limit orders not implemented yet");
    }

    public String handleSellLimit(String request) {
        throw new UnsupportedOperationException("Sell limit orders not implemented yet");
    }

    /**
     * Esegue un ordine di mercato che sia buy o sell
     * 
     * @param username L"utente che piazza l'ordine
     * @param coin     Moneta su cui eseguire l'ordine
     * @param amount   Quantita della moneta da comprare o vendere
     * @param isBuy    {@code true} se l'ordine e' di tipo buy, {@code false} se e'
     *                 sell
     */
    private String executeMarketOrder(String username, String coin, double amount, boolean isBuy)
            throws SQLException, IOException {
        UserDAO userDao = new UserDAO(username, connection);
        OrderBookDAO orderBookDao = new OrderBookDAO(coin, connection);

        // Determina se si ha nel wallet abbbastanza coin per eseguire l'operazione
        double baseBalance; // USDT balance if buying, coin balance if selling
        if (isBuy) {
            baseBalance = userDao.getCoinQuantity("USDT");
            if (baseBalance < amount) {
                return "ERROR;Not enough available USDT in your wallet";
            }
        } else {
            baseBalance = userDao.getCoinQuantity(coin);
            if (baseBalance < amount) {
                return String.format("ERROR;Not enough available %s in your wallet", coin);
            }
        }

        OrderBook book = isBuy ? orderBookDao.getAskOrderBook() : orderBookDao.getBidOrderBook();

        double remaining = amount;
        double totalAsset = 0.0;
        double totalUsdt = 0.0;

        for (OrderBookLevel level : book) {
            if (remaining <= 0)
                break;
            double levelQty = level.getQuantity();
            double price = level.getPrice();

            if (isBuy) {
                double cost = levelQty * price;
                if (remaining >= cost) {
                    totalAsset += levelQty;
                    totalUsdt += cost;
                    remaining -= cost;
                    level.setQuantity(0.0);
                } else {
                    double qty = remaining / price;
                    totalAsset += qty;
                    totalUsdt += remaining;
                    level.setQuantity(levelQty - qty);
                    remaining = 0;
                }
            } else {
                if (remaining >= levelQty) {
                    totalAsset += levelQty;
                    totalUsdt += levelQty * price;
                    remaining -= levelQty;
                    level.setQuantity(0.0);
                } else {
                    totalAsset += remaining;
                    totalUsdt += remaining * price;
                    level.setQuantity(levelQty - remaining);
                    remaining = 0;
                }
            }
        }

        // salva l'orderbook aggiornato
        System.out.println(book);
        orderBookDao.insertOrderBook(book);

        if (isBuy) {
            userDao.updateCoinQuantity("USDT", userDao.getCoinQuantity("USDT") - totalUsdt);
            userDao.updateCoinQuantity(coin, userDao.getCoinQuantity(coin) + totalAsset);
        } else {
            userDao.updateCoinQuantity(coin, userDao.getCoinQuantity(coin) - totalAsset);
            userDao.updateCoinQuantity("USDT", userDao.getCoinQuantity("USDT") + totalUsdt);
        }

        double traded = amount - remaining;
        double avgPrice = totalAsset > 0 ? totalUsdt / totalAsset : 0.0;
        String fillStatus = remaining > 0 ? "Order partially filled" : "Order totally filled";

        if (isBuy) {
            return String.format("OK;%s;spent=%.8f;bought=%.8f;avgPrice=%.8f", fillStatus, traded, totalAsset,
                    avgPrice);
        } else {
            return String.format("OK;%s;sold=%.8f;gained=%.8f;avgPrice=%.8f", fillStatus, totalAsset, totalUsdt,
                    avgPrice);
        }
    }
}
