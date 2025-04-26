package server.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import server.model.db.CoinDAO;
import server.model.db.UsersDAO;

public class MarketActionServer {
    private final Connection connection;

    public MarketActionServer(Connection connection){
        this.connection = connection;

    }
    public String handleBuyMarket(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String coin = request.split(" ")[2];

        UsersDAO usersDao = new UsersDAO(connection);
        CoinDAO coinDAO = new CoinDAO(coin,connection);

        return "";
    }

    public String handleBuyLimit(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String coin = request.split(" ")[2];
        Double price = Double.parseDouble(request.split(" ")[3]);

        UsersDAO usersDao = new UsersDAO(connection);
        CoinDAO coinDAO = new CoinDAO(coin,connection);

        return "";

    }

    public String handleSellMarket(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String coin = request.split(" ")[2];

        UsersDAO usersDao = new UsersDAO(connection);
        CoinDAO coinDAO = new CoinDAO(coin,connection);

        return "";

    }

    public String handleSellLimit(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String coin = request.split(" ")[2];
        Double price = Double.parseDouble(request.split(" ")[3]);

        UsersDAO usersDao = new UsersDAO(connection);
        CoinDAO coinDAO = new CoinDAO(coin,connection);

        return "";

    }
    
}
