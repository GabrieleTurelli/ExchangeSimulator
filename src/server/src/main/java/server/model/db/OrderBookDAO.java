package server.model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.model.market.OrderBook;
import server.model.market.OrderBookLevel;

public class OrderBookDAO {
    private final Connection connection;
    private final String tableName;

    public OrderBookDAO(String coin) throws IOException, SQLException {
        this(coin, DbConnector.getConnection());
    }

    public OrderBookDAO(String coin, Connection connection) {
        this.tableName = "orderbook_" + coin;
        this.connection = connection;
    }

    public OrderBook getOrderBook() throws SQLException {
        String query = "SELECT * FROM " + tableName;
        OrderBook orderBook = new OrderBook();

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Double price = rs.getDouble("price");
                Double quantity = rs.getDouble("quantity");
                Boolean isBid = rs.getBoolean("isBid");
                orderBook.add(new OrderBookLevel(price, quantity, isBid));
            }

            return orderBook;

        } catch (SQLException e) {
            // TO FIXXXXXXXXXXXXX
            e.printStackTrace();
            throw e;

        }
    }

}
