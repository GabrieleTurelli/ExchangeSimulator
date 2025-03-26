package server.model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.model.user.User;

public class UserDAO {
    private final User user;
    private final String tableName;
    private final Connection connection;

    public UserDAO(User user) throws SQLException, IOException {
        this.user = user;
        this.tableName = "user_" + user.getUsername();
        this.connection = DbConnector.getConnection();
    }

    public void initializeUser() throws SQLException {
        createUserTable();
        addCoin("USDT", 100000);
        addCoin("BTC", 0);
        addCoin("ETH", 0);
    }

    public void addCoin(String coin, double size) throws SQLException {
        String query = """
                INSERT INTO %s
                (coin, quantity) VALUES (?, ?)
                """.formatted(tableName);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, coin);
            preparedStatement.setDouble(2, size);
            preparedStatement.executeUpdate();
        }
    }

    public double getCoin(String coin) {
        String query = "SELECT quantity FROM " + tableName + " WHERE coin = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, coin);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("quantity");
                } else {
                    System.out.println("Coin not found: " + coin);
                    return 0.0;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving the coin: " + e.getMessage());
        }

        return 0.0;
    }

    public void createUserTable() throws SQLException {
        String userTable = """
                    CREATE TABLE IF NOT EXISTS %s (
                        coin TEXT NOT NULL,
                        quantity REAL NOT NULL
                    );
                """.formatted(tableName);

        try (PreparedStatement preparedStatement = connection.prepareStatement(userTable)) {
            preparedStatement.executeUpdate();
        }
    }

    public String getUser() throws SQLException {
        StringBuilder resultBuilder = new StringBuilder();
        String query = "SELECT coin, quantity FROM " + tableName + ";";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                String coinName = result.getString("coin");
                double quantity = result.getDouble("quantity");
                resultBuilder.append(coinName)
                        .append("=").append(quantity).append(",");
            }
        }

        System.out.println("User data: " + resultBuilder.toString().trim());
        return resultBuilder.toString().trim();
    }

}
