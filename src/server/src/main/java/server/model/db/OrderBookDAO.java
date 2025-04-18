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
    private final String coin;
    private final String tableName;
    private final Double priceStep = 0.2;

    public OrderBookDAO(String coin) throws IOException, SQLException {
        this(coin, DbConnector.getConnection());
    }

    public OrderBookDAO(String coin, Connection connection) {
        this.coin = coin;
        this.tableName = "orderbook_" + coin;
        this.connection = connection;
    }

    public OrderBook getOrderBook() throws SQLException {
        String query = "SELECT * FROM " + tableName;
        OrderBook orderBook = new OrderBook(coin);

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
            e.printStackTrace();
            throw e;

        }
    }

    public OrderBookLevel getOrderBookLevel(Double priceTarget) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE price = ?";
        Double price;
        Double quantity;
        Boolean isBid;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, priceTarget);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("price");
                    quantity = rs.getDouble("quantity");
                    isBid = rs.getBoolean("isBid");
                    return new OrderBookLevel(price, quantity, isBid);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateOrderBookLevel(OrderBookLevel orderBookLevel) throws SQLException {

        String query = "UPDATE " + tableName + " SET quantity = ?, isBid = ? WHERE price = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, orderBookLevel.getQuantity());
            statement.setBoolean(2, orderBookLevel.getIsBid());
            statement.setDouble(3, orderBookLevel.getPrice());
            int updatedRows = statement.executeUpdate();

            if (updatedRows == 0) {
                insertOrderBookLevel(orderBookLevel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void insertOrderBookLevel(OrderBookLevel orderBookLevel) throws SQLException {
        String query = "INSERT INTO " + tableName + " (price, quantity, isBid) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, orderBookLevel.getPrice());
            statement.setDouble(2, orderBookLevel.getQuantity());
            statement.setBoolean(3, orderBookLevel.getIsBid());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertOrderBook(OrderBook orderBook) {
        String query = "INSERT INTO " + tableName + " (price, quantity, isBid) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (OrderBookLevel orderBookLevel : orderBook) {
                statement.setDouble(1, orderBookLevel.getPrice());
                statement.setDouble(2, orderBookLevel.getQuantity());
                statement.setBoolean(3, orderBookLevel.getIsBid());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void populateOrderBookTable(Double initialPrice, int rows) {
        OrderBook orderBook = new OrderBook(coin);
        double bidPrice = initialPrice - 0.2;
        double askPrice = initialPrice + 0.2;
        double askQuantity;
        double bidQuantity;

        for (int i = 0; i < rows; i++) {
            bidPrice = Math.round((bidPrice - priceStep) * 100.0) / 100.0;
            askPrice = Math.round((askPrice + priceStep) * 100.0) / 100.0;
            bidQuantity = Math.round(Math.random() * 100);
            askQuantity = Math.round(Math.random() * 100);

            orderBook.add(new OrderBookLevel(bidPrice, bidQuantity, true));
            orderBook.add(new OrderBookLevel(askPrice, askQuantity, false));
        }

        insertOrderBook(orderBook);
    }

}
