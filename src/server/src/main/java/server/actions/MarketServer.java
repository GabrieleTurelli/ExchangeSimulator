package server.actions;

import java.io.IOException;
import java.sql.SQLException;

import server.model.coin.Kline;
import server.model.db.CoinDAO;

public class MarketServer {

    public static String handleGetKline(String request) throws SQLException, IOException {
        String coin;
        CoinDAO coinDAO;
        Kline kline;

        try {
            coin = request.split(" ")[1];
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
