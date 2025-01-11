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

public class CoinDAO {

    private String coin;
    private String tableName;
    private Connection connection;

    public CoinDAO(String coin) throws SQLException, IOException {
        this.coin = coin;
        this.tableName = "coin_" + coin.toUpperCase();
        this.connection = DbConnector.getConnection();
    }

    public void addCoinsData(HashMap<String, Double> coinData){
        coinData.forEach(this::addCoinData);
    }

    public void addCoinData(String date, Double price) {
        String sql = "INSERT OR REPLACE INTO " + tableName + " (date, price) VALUES (?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, date);
            stmt.setDouble(2, price);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteAllCoinData() {
        String sql = "DELETE FROM " + tableName;

        try{
            PreparedStatement stmt = connection.prepareStatement(sql);

            int rowsAffected = stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TreeMap<LocalDate, Double> getCoinData() {
        TreeMap<LocalDate, Double> coinData = new TreeMap<>();
        String sql = "SELECT date, price FROM " + tableName;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String dateStr = rs.getString("date");
                LocalDate date = LocalDate.parse(dateStr, formatter);
                Double price = rs.getDouble("price");
                coinData.put(date, price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return coinData;
    }

    public void populateTable(double initialPrice, int rows) {
        LocalDate initialDate = LocalDate.now().minusDays(rows);
        Random random = new Random();

        for (int i = 0; i <= rows; i++) {
            LocalDate date = initialDate.plusDays(i);
            String dateStr = date.toString();
            double priceFluctuation = random.nextDouble() * 20 - 10;
            double price = initialPrice + priceFluctuation;
            price = (double) Math.round(price * 100) / 100;
            addCoinData(dateStr, price);
        }
    }
}
