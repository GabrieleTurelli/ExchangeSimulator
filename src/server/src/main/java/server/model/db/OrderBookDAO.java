package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import server.model.market.OrderBook;
import server.model.market.OrderBookLevel;

public class OrderBookDAO {
    private final DataSource dataSource;
    private final String tableName;
    private final double priceStep = 0.2;

    public OrderBookDAO(String coin, DataSource dataSource) {
        this.dataSource = dataSource;
        this.tableName = "orderbook_" + coin;
    }

    public OrderBook getOrderBook() throws SQLException {
        String sql = "SELECT price, quantity, isBid FROM " + tableName;
        OrderBook orderBook = new OrderBook(tableName.replace("orderbook_", ""));
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderBookLevel lvl = new OrderBookLevel(
                        rs.getDouble("price"),
                        rs.getDouble("quantity"),
                        rs.getBoolean("isBid"));
                orderBook.add(lvl);
            }
        }
        return orderBook;
    }

    public OrderBookLevel getOrderBookLevel(double priceTarget) throws SQLException {
        String sql = "SELECT price, quantity, isBid FROM " + tableName + " WHERE price = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, lvl.getPrice());
            ps.setDouble(2, lvl.getQuantity());
            ps.setBoolean(3, lvl.getIsBid());
            ps.executeUpdate();
        }
    }

    public void insertOrderBook(OrderBook orderBook) throws SQLException {
        String sql = "INSERT OR REPLACE INTO " + tableName + " (price, quantity, isBid) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            for (OrderBookLevel lvl : orderBook) {
                ps.setDouble(1, lvl.getPrice());
                ps.setDouble(2, lvl.getQuantity());
                ps.setBoolean(3, lvl.getIsBid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void populateOrderBookTable(Double initialPrice, int rows) throws SQLException {
        OrderBook orderBook = new OrderBook(tableName.replace("orderbook_", ""));
        double bidPrice = initialPrice - 0.2;
        double askPrice = initialPrice + 0.2;
        double askQuantity;
        double bidQuantity;

        for (int i = 0; i < rows; i++) {
            bidPrice = Math.max(Math.round((bidPrice - priceStep) * 100.0) / 100.0, 0);
            askPrice = Math.max(Math.round((askPrice + priceStep) * 100.0) / 100.0, 0);
            bidQuantity = Math.round(Math.random() * 100);
            askQuantity = Math.round(Math.random() * 100);

            orderBook.add(new OrderBookLevel(bidPrice, bidQuantity, true));
            orderBook.add(new OrderBookLevel(askPrice, askQuantity, false));
        }

        insertOrderBook(orderBook);
    }
}