package model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void populateTable(double initialPrice, int rows) {
        LocalDate initialDate = LocalDate.now().minusDays(rows);
        Random random = new Random();

        for (int i = 0; i <= rows; i++) {
            LocalDate date = initialDate.plusDays(i);
            double priceFluctuation = random.nextDouble() * 20 - 10;
            double price = initialPrice + priceFluctuation;
            price = Math.round(price * 100.0) / 100.0;
            addData(date, price);
        }
    }
}
