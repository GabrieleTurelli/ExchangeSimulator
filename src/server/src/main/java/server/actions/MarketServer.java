package server.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import server.model.db.CoinDAO;
import server.model.db.CoinsDAO;
import server.model.db.DbConnector;
import server.model.market.Kline;
import server.model.market.KlineHistory;

public class MarketServer {

    private final Connection connection;

    public MarketServer() throws SQLException, IOException {
        this(DbConnector.getConnection());
    }

    public MarketServer(Connection connection) {
        this.connection = connection;
    }

    public String handleGetCoins() {
        try {
            CoinsDAO coinsDao = new CoinsDAO(connection);
            List<String> coinsList = coinsDao.getData();
            return "OK;" + coinsList.toString()
                                     .replace(" ", "")
                                     .replace("[", "")
                                     .replace("]", "");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return "ERROR;Failed to retrieve coins";
        }
    }

    public String handleGetHistory(String request) {
        String[] parts = request.trim().split(" ", 2);
        if (parts.length < 2) {
            return "ERROR;Invalid request";
        }
        String coin = parts[1];

        try {
            CoinDAO coinDAO = new CoinDAO(coin, connection);
            KlineHistory history = coinDAO.getKlineHistory();
            return "OK;" + history;
        } catch (IOException | SQLException e) {
            System.err.println("Coin not found in getHistory for coin: " + coin);
            e.printStackTrace();
            return "ERROR;Coin not found";
        }
    }

    public String handleGetKline(String request) {
        String[] parts = request.trim().split(" ", 2);
        if (parts.length < 2) {
            return "ERROR;Invalid request";
        }
        String coin = parts[1];
        System.out.println("Coin in getKline: " + coin);

        try {
            CoinDAO coinDAO = new CoinDAO(coin, connection);
            Kline kline = coinDAO.getLastKline();
            return "OK;" + kline;
        } catch (IOException | SQLException e) {
            System.err.println("Coin not found in getKline for coin: " + coin);
            e.printStackTrace();
            return "ERROR;Coin not found";
        }
    }

    public String handleGetLastPrice(String request) {
        String[] parts = request.trim().split(" ", 2);
        if (parts.length < 2) {
            return "ERROR;Invalid request";
        }
        String coin = parts[1];

        try {
            CoinDAO coinDAO = new CoinDAO(coin, connection);
            double price = coinDAO.getLastPrice();
            return "OK;" + price;
        } catch (IOException | SQLException e) {
            System.err.println("Coin not found in getLastPrice for coin: " + coin);
            e.printStackTrace();
            return "ERROR;Coin not found";
        }
    }
}
