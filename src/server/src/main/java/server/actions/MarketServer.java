package server.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.TreeMap;

import server.model.db.CoinDAO;

public class MarketServer {

    public static String handleGetData(String request) throws SQLException, IOException {
        String coin;
        CoinDAO coinDAO;
        TreeMap<LocalDate, Double> coinData;

        try {
            coin = request.split(" ")[1];
        } catch (IndexOutOfBoundsException e) {
            return "ERROR;Invalid request";
        }

        try {
            coinDAO = new CoinDAO(coin);
            coinData = coinDAO.getCoinData();
        } catch (IOException | SQLException e) {
            System.out.println("Coin not found in getLastPrice for coin :" + coin);
            e.printStackTrace();
            return "ERROR;Coin not found";
        }

        return "OK;" + coinData;

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
