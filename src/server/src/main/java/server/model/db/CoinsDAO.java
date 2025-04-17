package server.model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoinsDAO implements SingleValueDAO<String> {

    private final String tableName = "Coins";
    private final Connection connection;

    public CoinsDAO() throws SQLException, IOException {
        this.connection = DbConnector.getConnection();
    }

    public CoinsDAO(Connection connection) throws SQLException, IOException {
        this.connection = connection; 
    }

    @Override
    public void addData(String coinPair) {
        String sql = "INSERT OR IGNORE INTO " + tableName + "(pair) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, coinPair);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getData() {
        ArrayList<String> coins = new ArrayList<String>();
        String sql = "SELECT pair FROM " + tableName;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String coinPair = rs.getString("pair");
                coins.add(coinPair);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coins;
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


    public void deleteCoinPair(String coinPair) {
        String sql = "DELETE FROM " + tableName+ " WHERE pair = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, coinPair);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
