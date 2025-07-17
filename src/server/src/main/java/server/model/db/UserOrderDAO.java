package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import server.model.market.Order;

public class UserOrderDAO {
    private final Connection connection;
    private final String tableName;

    public UserOrderDAO(String username, Connection connection) {
        this.connection = connection;
        this.tableName = "user_order_" + username;
    }

    public void initializeUserOrders() throws SQLException {
        connection.setAutoCommit(false);
        try {
            String statement = String.format(
                    """
                    CREATE TABLE IF NOT EXISTS %s (
                      order_id TEXT PRIMARY KEY,
                      order_type TEXT NOT NULL,
                      coin TEXT NOT NULL,
                      quantity REAL NOT NULL,
                      price REAL NOT NULL,
                      side TEXT NOT NULL,
                      timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
                    );""",
                    tableName);

            try (PreparedStatement ps = connection.prepareStatement(statement)) {
                ps.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO " + tableName
                    + " (order_id, order_type, coin, quantity, price, side, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getOrderType().toString());
            ps.setString(3, order.getCoin());
            ps.setDouble(4, order.getQuantity());
            ps.setDouble(5, order.getPrice());
            ps.setString(6, order.getSide().toString());
            ps.setTimestamp(7, order.getTimestamp());
            ps.executeUpdate();
        }
    }
}
