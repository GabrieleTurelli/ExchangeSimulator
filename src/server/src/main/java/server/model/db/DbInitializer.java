package server.model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.sql.DataSource;

public class DbInitializer {
    private final DataSource dataSource;
    private final Map<String, Double> coins;
    private final int historyDays          = 50;
    private final int orderBookLevelsNumber = 10;

    public DbInitializer(Map<String, Double> coins, DataSource dataSource) {
        this.coins        = coins;
        this.dataSource   = dataSource;
    }

    public void initializeDatabase() throws SQLException, IOException {
        createCoinsTable();
        createUsersTable();
        for (String coin : coins.keySet()) {
            addCoinToCoinsTable(coin);
            createCoinTable(coin);
            createOrderBookTable(coin);

            new CoinDAO(coin, dataSource)
                .populateCoinTable(coins.get(coin), historyDays);

            new OrderBookDAO(coin, dataSource)
                .populateOrderBookTable(coins.get(coin), orderBookLevelsNumber);
        }
    }

    public void createCoinsTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Coins ("
                   + "pair TEXT NOT NULL UNIQUE, PRIMARY KEY(pair));";
        try (Connection conn = dataSource.getConnection();
             Statement stmt   = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addCoinToCoinsTable(String coin) throws SQLException {
        String sql = "INSERT OR IGNORE INTO Coins (pair) VALUES (?);";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, coin);
            ps.executeUpdate();
        }
    }

    public void createCoinTable(String coin) throws SQLException {
        String sql = String.format(
            "CREATE TABLE IF NOT EXISTS coin_%s ("
          + "date TEXT NOT NULL, open REAL NOT NULL, high REAL NOT NULL,"
          + "low REAL NOT NULL, close REAL NOT NULL, PRIMARY KEY(date));",
            coin);
        try (Connection conn = dataSource.getConnection();
             Statement stmt   = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Users ("
                   + "username TEXT NOT NULL UNIQUE, password TEXT);";
        try (Connection conn = dataSource.getConnection();
             Statement stmt   = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createUserTable(String username) throws SQLException {
        String sql = String.format(
            "CREATE TABLE IF NOT EXISTS user_%s ("
          + "coin TEXT NOT NULL, quantity REAL NOT NULL);",
            username);
        try (Connection conn = dataSource.getConnection();
             Statement stmt   = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createOrderBookTable(String coinName) throws SQLException {
        String sql = String.format(
            "CREATE TABLE IF NOT EXISTS orderbook_%s ("
          + "price REAL PRIMARY KEY, quantity REAL NOT NULL, isBid BOOLEAN NOT NULL);",
            coinName);
        try (Connection conn = dataSource.getConnection();
             Statement stmt   = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void dropTable(String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Connection conn = dataSource.getConnection();
             Statement stmt   = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
