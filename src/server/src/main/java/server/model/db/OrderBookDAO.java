package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.model.market.OrderBook;
import server.model.market.OrderBookLevel;

public class OrderBookDAO {
    private final Connection connection;
    private final String tableName;
    private final double priceStep = 0.2;

    public OrderBookDAO(String coin, Connection connection) {
        this.connection = connection;
        this.tableName = "orderbook_" + coin;
    }

    public OrderBook getOrderBook() throws SQLException {
        String sql = "SELECT price, quantity, isBid FROM " + tableName;
        OrderBook orderBook = new OrderBook(tableName.replace("orderbook_", ""));
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orderBook.add(new OrderBookLevel(
                        rs.getDouble("price"),
                        rs.getDouble("quantity"),
                        rs.getBoolean("isBid")));
            }
        }
        return orderBook;
    }

    private OrderBook getPartialOrderBook(boolean isBid) throws SQLException {
        String sql = "SELECT price, quantity, isBid FROM " + tableName + " WHERE isBid = "
                + Integer.valueOf(isBid ? 1 : 0);
        OrderBook orderBook = new OrderBook(tableName.replace("orderbook_", ""));
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orderBook.add(new OrderBookLevel(
                        rs.getDouble("price"),
                        rs.getDouble("quantity"),
                        rs.getBoolean("isBid")));
            }
        }
        return orderBook;
    }

    public OrderBook getBidOrderBook() throws SQLException {
        return getPartialOrderBook(true);
    }

    public OrderBook getAskOrderBook() throws SQLException {
        return getPartialOrderBook(false);
    }

    public OrderBookLevel getOrderBookLevel(double priceTarget) throws SQLException {
        String sql = "SELECT price, quantity, isBid FROM " + tableName + " WHERE price = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, priceTarget);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new OrderBookLevel(
                            rs.getDouble("price"),
                            rs.getDouble("quantity"),
                            rs.getBoolean("isBid"));
                }
            }
        }
        return null;
    }

    public void updateOrderBookLevel(OrderBookLevel lvl) throws SQLException {
        String sql = "UPDATE " + tableName + " SET quantity = ?, isBid = ? WHERE price = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, lvl.getQuantity());
            ps.setBoolean(2, lvl.getIsBid());
            ps.setDouble(3, lvl.getPrice());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                insertOrderBookLevel(lvl);
            }
        }
    }

    public void insertOrderBookLevel(OrderBookLevel lvl) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (price, quantity, isBid) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, lvl.getPrice());
            ps.setDouble(2, lvl.getQuantity());
            ps.setBoolean(3, lvl.getIsBid());
            ps.executeUpdate();
        }
    }

    public void deleteOrderBookLevel(double price) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE price = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.executeUpdate();
        }
    }

    public void insertOrderBook(OrderBook orderBook) throws SQLException {
        String sql = "INSERT OR REPLACE INTO " + tableName + " (price, quantity, isBid) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (OrderBookLevel lvl : orderBook) {
                System.out.println("Inserting order book level: " + lvl);
                if (lvl.getQuantity() <= 0) {
                    deleteOrderBookLevel(lvl.getPrice());
                    continue;
                }
                ps.setDouble(1, lvl.getPrice());
                ps.setDouble(2, lvl.getQuantity());
                ps.setBoolean(3, lvl.getIsBid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void replaceOrderBook(OrderBook orderBook) throws SQLException {
        String deleteSql = "DELETE FROM " + tableName;
        String insertSql = "INSERT OR REPLACE INTO " + tableName + " (price, quantity, isBid) VALUES (?, ?, ?)";

        connection.setAutoCommit(false);
        try (Statement deleteStmt = connection.createStatement();
                PreparedStatement insertPs = connection.prepareStatement(insertSql)) {

            deleteStmt.executeUpdate(deleteSql);

            for (OrderBookLevel lvl : orderBook) {
                insertPs.setDouble(1, lvl.getPrice());
                insertPs.setDouble(2, lvl.getQuantity());
                insertPs.setBoolean(3, lvl.getIsBid());
                insertPs.addBatch();
            }
            insertPs.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void populateOrderBookTable(double initialPrice, int rows) throws SQLException {
        OrderBook orderBook = new OrderBook(tableName.replace("orderbook_", ""));
        double bidPrice = initialPrice - priceStep;
        double askPrice = initialPrice + priceStep;

        for (int i = 0; i < rows; i++) {
            bidPrice = Math.max(Math.round((bidPrice - priceStep) * 100.0) / 100.0, 0);
            askPrice = Math.max(Math.round((askPrice + priceStep) * 100.0) / 100.0, 0);
            double bidQuantity = Math.round(Math.random() * 100);
            double askQuantity = Math.round(Math.random() * 100);

            orderBook.add(new OrderBookLevel(bidPrice, bidQuantity, true));
            orderBook.add(new OrderBookLevel(askPrice, askQuantity, false));
        }

        replaceOrderBook(orderBook);
    }
}
