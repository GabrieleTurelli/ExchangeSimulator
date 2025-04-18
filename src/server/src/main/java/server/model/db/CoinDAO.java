package server.model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;

import server.model.market.Kline;
import server.model.market.KlineHistory;

public class CoinDAO {

    private final String tableName;
    private final Connection connection;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CoinDAO(String coin) throws SQLException, IOException {
        this(coin, DbConnector.getConnection());
    }

    public CoinDAO(String coin, Connection connection) throws SQLException, IOException {
        this.tableName = "coin_" + coin.toUpperCase();
        this.connection = connection;
    }

    public void addData(LocalDate date, Kline kline) {
        String sql = "INSERT OR REPLACE INTO " + tableName + " (date, open, high, low, close) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, date.format(dateFormatter));
            stmt.setDouble(2, kline.getOpen());
            stmt.setDouble(3, kline.getHigh());
            stmt.setDouble(4, kline.getLow());
            stmt.setDouble(5, kline.getClose());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCoinsData(HashMap<LocalDate, Kline> coinData) {
        coinData.forEach(this::addData);
    }

    public void deleteAllData() {
        String sql = "DELETE FROM " + tableName;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Kline getLastKline() throws SQLException {
        String query = "SELECT date, open, high, low, close  FROM " + tableName
                + " WHERE date = (SELECT MAX(date) FROM " + tableName + ")";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                String dateStr = rs.getString("date");
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                Double open = rs.getDouble("open");
                Double high = rs.getDouble("high");
                Double low = rs.getDouble("low");
                Double close = rs.getDouble("close");
                return new Kline(open, high, low, close);
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateKline(Kline kline) {
        String query = "UPDATE " + tableName
                + " SET open = ?, high = ?, low = ?, close = ? WHERE date = (SELECT MAX(date) FROM " + tableName + ")";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, kline.getOpen());
            statement.setDouble(2, kline.getHigh());
            statement.setDouble(3, kline.getLow());
            statement.setDouble(4, kline.getClose());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public KlineHistory getKlineHistory() throws SQLException {
        String query = "SELECT *  FROM " + tableName;
        KlineHistory klineHistory = new KlineHistory();

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Double open = rs.getDouble("open");
                Double high = rs.getDouble("high");
                Double low = rs.getDouble("low");
                Double close = rs.getDouble("close");
                klineHistory.add(new Kline(open, high, low, close));
            }
            return klineHistory;

        } catch (SQLException e) {

            // TO FIXXXXXXXXX
            e.printStackTrace();
            throw e;
        }
    }

    public double getLastPrice() throws SQLException {
        double coinPrice = 0.0;
        String query = "SELECT close FROM " + tableName + " WHERE date = (SELECT MAX(date) FROM " + tableName + ")";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                coinPrice = resultSet.getDouble("close");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return coinPrice;
    }

    public void populateCoinTable(double initialPrice, int rows) {
        try {
            if (!doesTableExist()) {
                System.out.println("Table does not exist, creating it.");
                createTable();
                populateDataFromStart(initialPrice, rows);
            } else {
                System.out.println("Table already exists, checking for last date.");
                LocalDate lastDate = getLastDate();
                if (lastDate != null) {
                    populateDataFromLastDate(lastDate, initialPrice);
                } else {
                    System.out.println("No data found in the table, populating from start.");
                    populateDataFromStart(initialPrice, rows);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean doesTableExist() throws SQLException {
        String checkTableQuery = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
        try (PreparedStatement stmt = connection.prepareStatement(checkTableQuery)) {
            stmt.setString(1, tableName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void createTable() throws SQLException {
        String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS %s (
                        date TEXT NOT NULL,
                        price REAL NOT NULL,
                        PRIMARY KEY (date)
                    );
                """.formatted(tableName);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableQuery);
        }
    }

    private LocalDate getLastDate() {
        String getLastDateQuery = "SELECT MAX(date) AS last_date FROM " + tableName;
        try (PreparedStatement stmt = connection.prepareStatement(getLastDateQuery);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastDateStr = rs.getString("last_date");
                return lastDateStr != null ? LocalDate.parse(lastDateStr, dateFormatter) : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void populateDataFromStart(double initialPrice, int rows) {
        LocalDate start = LocalDate.now().minusDays(rows);
        Random rnd = new Random();
        double price = initialPrice;

        String sql = "INSERT OR REPLACE INTO " + tableName +
                " (date, open, high, low, close) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            for (int i = 0; i < rows; i++) {
                LocalDate date = start.plusDays(i);
                price = addKlineBatch(ps, date, price, rnd);
            }

            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
        }
    }

    private void populateDataFromLastDate(LocalDate lastDate, double fallbackPrice) {
        LocalDate today = LocalDate.now();
        if (lastDate.isEqual(today))
            return;

        Random rnd = new Random();
        double price = (getLastPriceSafely(fallbackPrice));

        String sql = "INSERT OR REPLACE INTO " + tableName +
                " (date, open, high, low, close) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            for (LocalDate d = lastDate.plusDays(1); !d.isAfter(today); d = d.plusDays(1)) {
                price = addKlineBatch(ps, d, price, rnd);
            }

            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
        }
    }

    private double addKlineBatch(PreparedStatement ps, LocalDate date,
            double previousClose, Random rnd) throws SQLException {

        double open = previousClose;
        double close = round2(open * (1 + (rnd.nextDouble() * 6 - 3) / 100));
        double high = round2(Math.max(open, close) * (1 + rnd.nextDouble() * 0.02));
        double low = round2(Math.min(open, close) * (1 - rnd.nextDouble() * 0.02));
        low = Math.max(low, 0);

        ps.setString(1, date.format(dateFormatter));
        ps.setDouble(2, open);
        ps.setDouble(3, high);
        ps.setDouble(4, low);
        ps.setDouble(5, close);
        ps.addBatch();

        return close;
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private double getLastPriceSafely(double fallback) {
        try {
            return getLastPrice();
        } catch (SQLException e) {
            return fallback;
        }
    }
}