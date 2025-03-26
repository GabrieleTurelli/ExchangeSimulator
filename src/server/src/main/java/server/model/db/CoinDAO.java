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

public class CoinDAO implements KeyValueDAO<LocalDate, Double> {

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
    public void addData(LocalDate date, Double price) {
        String sql = "INSERT OR REPLACE INTO " + tableName + " (date, price) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, date.format(dateFormatter));
            stmt.setDouble(2, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCoinsData(HashMap<LocalDate, Double> coinData) {
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

    @Override
    public TreeMap<LocalDate, Double> getData() {
        TreeMap<LocalDate, Double> coinData = new TreeMap<>();
        String sql = "SELECT date, price FROM " + tableName;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String dateStr = rs.getString("date");
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                Double price = rs.getDouble("price");
                coinData.put(date, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coinData;
    }

    public void populateCoinTable(double initialPrice, int rows) {
        try {
            if (!doesTableExist()) {
                createTable();
                populateDataFromStart(initialPrice, rows);
            } else {
                LocalDate lastDate = getLastDate();
                if (lastDate != null) {
                    populateDataFromLastDate(lastDate, initialPrice);
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

        System.out.printf("Populating %s table since %s ",tableName, initialDate);

        Random random = new Random();

        for (int i = 0; i <= rows; i++) {
            LocalDate date = initialDate.plusDays(i);
            double priceFluctuation = random.nextDouble() * 20 - 10;
            double price = initialPrice + priceFluctuation;
            price = Math.round(price * 100.0) / 100.0;
            addData(date, price);
        }
    }

    private void populateDataFromLastDate(LocalDate lastDate, double initialPrice) {

        LocalDate today = LocalDate.now();
        Random random = new Random();
        LocalDate startDate = lastDate.plusDays(1);

        if (lastDate.equals(today)) {return;}

        System.out.printf("Populating %s table from %s to %s\n",tableName, lastDate, today);

        for (LocalDate date = startDate; !date.isAfter(today); date = date.plusDays(1)) {
            double priceFluctuation = random.nextDouble() * 20 - 10;
            double price = initialPrice + priceFluctuation;
            price = Math.round(price * 100.0) / 100.0;
            addData(date, price);
        }
    }

}
