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
import java.util.TreeMap;

import server.model.coin.Kline;

public class CoinDAO implements KeyValueDAO<LocalDate, Kline> {

    private final String coin;
    private final String tableName;
    private final Connection connection;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CoinDAO(String coin) throws SQLException, IOException {
        this.coin = coin;
        this.tableName = "coin_" + coin.toUpperCase();
        this.connection = DbConnector.getConnection();
    }

    @Override
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

    @Override
    public void deleteAllData() {
        String sql = "DELETE FROM " + tableName;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TreeMap<LocalDate, Double> getCoinPrice() throws SQLException {
        TreeMap<LocalDate, Double> coinData = new TreeMap<>();
        String query = "SELECT date, close FROM " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String dateStr = rs.getString("date");
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                Double price = rs.getDouble("close");
                coinData.put(date, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return coinData;
    }

    public Kline getDailyData() throws SQLException {
        String query = "SELECT date, open, high, low, close  FROM " + tableName;

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
        LocalDate initialDate = LocalDate.now().minusDays(rows);
        System.out.printf("Populating %s table since %s%n", tableName, initialDate);
        Random random = new Random();

        double previousClose = initialPrice;

        for (int i = 0; i <= rows; i++) {
            LocalDate date = initialDate.plusDays(i);

            double open = previousClose;

            double fluctuation = random.nextDouble() * 20 - 10;
            double close = open + fluctuation;
            close = Math.round(close * 100.0) / 100.0;

            double high = Math.max(open, close) + random.nextDouble() * 5;
            high = Math.round(high * 100.0) / 100.0;

            double low = Math.min(open, close) - random.nextDouble() * 5;
            low = Math.round(low * 100.0) / 100.0;
            if (low < 0) {
                low = 0;
            }

            if (high < Math.max(open, close)) {
                high = Math.max(open, close);
            }

            Kline kline = new Kline(open, high, low, close);
            addData(date, kline);

            previousClose = close;
        }
    }

    private void populateDataFromLastDate(LocalDate lastDate, double initialPrice) throws SQLException {
        LocalDate today = LocalDate.now();

        if (lastDate.equals(today)) {
            System.out.println("today is the date");
            return;
        }

        System.out.printf("Populating %s table from %s to %s%n", tableName, lastDate, today);
        Random random = new Random();

        double previousClose = getLastPrice();

        if (previousClose == 0.0) {
            previousClose = initialPrice;
        }

        for (LocalDate date = lastDate.plusDays(1); !date.isAfter(today); date = date.plusDays(1)) {
            double open = previousClose;

            double fluctuation = random.nextDouble() * 20 - 10;
            double close = open + fluctuation;
            close = Math.round(close * 100.0) / 100.0;

            double high = Math.max(open, close) + random.nextDouble() * 5;
            high = Math.round(high * 100.0) / 100.0;

            double low = Math.min(open, close) - random.nextDouble() * 5;
            low = Math.round(low * 100.0) / 100.0;
            if (low < 0) {
                low = 0;
            }
            if (high < Math.max(open, close)) {
                high = Math.max(open, close);
            }

            Kline kline = new Kline(open, high, low, close);
            addData(date, kline);

            previousClose = close;
        }
    }

    @Override
    public TreeMap<LocalDate, Kline> getData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
