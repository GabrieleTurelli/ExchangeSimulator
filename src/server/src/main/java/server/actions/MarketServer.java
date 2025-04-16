package server.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import server.model.db.CoinDAO;
import server.model.db.CoinsDAO;
import server.model.market.Kline;
import server.model.market.KlineHistory;

public class MarketServer {


    public static String handleGetCoins() throws SQLException, IOException {
        CoinsDAO coinsDao;
        List<String> coinsList;

        try {
            coinsDao = new CoinsDAO();
            coinsList = coinsDao.getData();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return "ERROR;Failed to retrieve coins";
        }

        return "OK;" + coinsList.toString().replace(" ","").replace("[", "").replace("]", "");
    }
    public static String handleGetHistory(String request) throws SQLException, IOException {
        String coin;
        CoinDAO coinDAO;
        KlineHistory klineHistory;

        try {
            coin = request.split(" ")[1];
        } catch (IndexOutOfBoundsException e) {
            return "ERROR;Invalid request";
        }

        try {
            coinDAO = new CoinDAO(coin);
            klineHistory = coinDAO.getKlineHistory();
        } catch (IOException | SQLException e) {
            System.out.println("Coin not found in getLastPrice for coin :" + coin);
            e.printStackTrace();
            return "ERROR;Coin not found";
        }

        return "OK;" + klineHistory;
    }

    public static String handleGetKline(String request) throws SQLException, IOException {
        String coin;
        CoinDAO coinDAO;
        Kline kline;

        try {
            coin = request.split(" ")[1];
            System.out.println("Coin in getKline: " + coin);
        } catch (IndexOutOfBoundsException e) {
            return "ERROR;Invalid request";
        }

        try {
            coinDAO = new CoinDAO(coin);
            kline = coinDAO.getLastKline();
        } catch (IOException | SQLException e) {
            System.out.println("Coin not found in getLastPrice for coin :" + coin);
            e.printStackTrace();
            return "ERROR;Coin not found";
        }

        return "OK;" + kline;

    }

    public static String handleGetLastPrice(String request) throws SQLException, IOException {
        String coin;
        CoinDAO coinDAO;
        double coinPrice;
        try {
            coin = request.split(" ")[1];
        } catch (IndexOutOfBoundsException e) {
            return "ERROR;Invalid request";
        }

        try {
            coinDAO = new CoinDAO(coin);
            coinPrice = coinDAO.getLastPrice();
        } catch (IOException | SQLException e) {
            System.out.println("Coin not found in getLastPrice for coin :" + coin);
            e.printStackTrace();
            return "ERROR;Coin not found";
        }

        return "OK;" + coinPrice;

    }
}
