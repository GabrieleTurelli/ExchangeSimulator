package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Connection connection;
    private final String tableName;

    public UserDAO(String username, Connection connection) {
        this.connection = connection;
        this.tableName = "user_" + username;
    }

    public void initializeUser() throws SQLException {
        connection.setAutoCommit(false);
        try {
            String statement = String.format("""
                                       CREATE TABLE IF NOT EXISTS %s (
                                         coin TEXT NOT NULL UNIQUE PRIMARY KEY,
                                         quantity REAL NOT NULL
                                       );""",
                tableName
            );
            try (PreparedStatement ps = connection.prepareStatement(statement)) {
                ps.executeUpdate();
            }

            addCoin("USDT", 100_000);
            addCoin("BTC", 0);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void addCoin(String coin, double amount) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (coin, quantity) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, coin);
            ps.setDouble(2, amount);
            ps.executeUpdate();
        }
    }

    public void updateCoinQuantity(String coin, double amount) throws SQLException {
        String sql = "REPLACE INTO " + tableName + " (coin, quantity) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, coin);
            ps.setDouble(2, amount);
            ps.executeUpdate();
        }
    }

    public double getCoinQuantity(String coin) throws SQLException {
        String sql = "SELECT quantity FROM " + tableName + " WHERE coin = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, coin);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble("quantity") : 0.0;
            }
        }
    }

    public String getUser() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT coin, quantity FROM " + tableName;
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append(rs.getString("coin"))
                  .append("=")
                  .append(rs.getDouble("quantity"))
                  .append(",");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
