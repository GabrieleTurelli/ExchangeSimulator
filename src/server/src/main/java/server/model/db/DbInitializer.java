package server.model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DbInitializer {
    private final Connection connection;
    private final Map<String, Double> coins;
    private final int historyDays = 50;
    private final int orderBookLevelsNumber = 10;

    public DbInitializer(Map<String, Double> coins, Connection connection) {
        this.coins = coins;
        this.connection = connection;
    }

    public void initializeDatabase() throws SQLException, IOException {
        createCoinsTable();
        createUsersTable();

        connection.setAutoCommit(false);

        try {

            for (String coin : coins.keySet()) {
                addCoinToCoinsTable(coin);
                createCoinTable(coin);
                createOrderBookTable(coin);

                new CoinDAO(coin, connection)
                        .populateCoinTable(coins.get(coin), historyDays);

                new OrderBookDAO(coin, connection)
                        .populateOrderBookTable(coins.get(coin), orderBookLevelsNumber);
            }

        } catch (SQLException e) {

            try {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }

            } catch (SQLException e2) {
                System.err.println("Rollback failed: " + e2.getMessage());

            }
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void createCoinsTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS Coins (
                  pair TEXT NOT NULL UNIQUE,
                  PRIMARY KEY(pair)
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addCoinToCoinsTable(String coin) throws SQLException {
        String sql = "INSERT OR IGNORE INTO Coins (pair) VALUES ('" + coin + "');";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public void createCoinTable(String coin) throws SQLException {
        String sql = String.format("""
                CREATE TABLE IF NOT EXISTS coin_%s (
                  date  TEXT NOT NULL,
                  open  REAL NOT NULL,
                  high  REAL NOT NULL,
                  low   REAL NOT NULL,
                  close REAL NOT NULL,
                  PRIMARY KEY(date)
                );
                """, coin);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createUsersTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS Users (
                  username TEXT NOT NULL UNIQUE,
                  password TEXT
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createUserTable(String username) throws SQLException {
        String sql = String.format("""
                CREATE TABLE IF NOT EXISTS user_%s (
                  coin     TEXT NOT NULL,
                  quantity REAL NOT NULL
                );
                """, username);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createOrderBookTable(String coinName) throws SQLException {
        String sql = String.format("""
                CREATE TABLE IF NOT EXISTS orderbook_%s (
                  price    REAL PRIMARY KEY,
                  quantity REAL NOT NULL,
                  isBid    BOOLEAN NOT NULL
                );
                """, coinName);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void dropTable(String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}
