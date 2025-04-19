package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class UserDAO {
    private final DataSource dataSource;
    private final String tableName;

    public UserDAO(String username, DataSource dataSource) {
        this.dataSource = dataSource;
        this.tableName = "user_" + username;
    }

    public void initializeUser() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            String ddl = """
                CREATE TABLE IF NOT EXISTS %s (
                  coin TEXT NOT NULL,
                  quantity REAL NOT NULL
                );
                """.formatted(tableName);
            try (PreparedStatement ps = conn.prepareStatement(ddl)) {
                ps.executeUpdate();
            }
            addCoin(conn, "USDT", 100_000);
            addCoin(conn, "BTC", 0);
            conn.commit();
        }
    }

    public void addCoin(String coin, double size) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            addCoin(conn, coin, size);
        }
    }

    private void addCoin(Connection conn, String coin, double size) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (coin, quantity) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, coin);
            ps.setDouble(2, size);
            ps.executeUpdate();
        }
    }

    public double getCoin(String coin) throws SQLException {
        String sql = "SELECT quantity FROM " + tableName + " WHERE coin = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, coin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("quantity");
                } else {
                    return 0.0;
                }
            }
        }
    }

    public String getUser() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT coin, quantity FROM " + tableName;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append(rs.getString("coin"))
                  .append("=")
                  .append(rs.getDouble("quantity"))
                  .append(",");
            }
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
