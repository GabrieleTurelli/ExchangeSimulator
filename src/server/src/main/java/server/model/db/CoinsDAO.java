package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoinsDAO implements SingleValueDAO<String> {

    private final Connection connection;
    private static final String TABLE_NAME = "Coins";

    public CoinsDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addData(String coinPair) {
        String sql = "INSERT OR IGNORE INTO " + TABLE_NAME + " (pair) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, coinPair);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add coin pair: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> getData() {
        ArrayList<String> coins = new ArrayList<>();
        String sql = "SELECT pair FROM " + TABLE_NAME;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                coins.add(rs.getString("pair"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch coin pairs: " + e.getMessage());
        }
        return coins;
    }

    @Override
    public void deleteAllData() {
        String sql = "DELETE FROM " + TABLE_NAME;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete all coin pairs: " + e.getMessage());
        }
    }

    public void deleteCoinPair(String coinPair) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE pair = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, coinPair);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete coin pair '" + coinPair + "': " + e.getMessage());
        }
    }
}
