package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    private final Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addUser(String username, String password) {
        String query = """
            INSERT INTO Users (username, password)
            VALUES (?, ?);
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add user");
            return false;
        }
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String query = "SELECT username FROM Users";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            System.out.println("User doesn't exists");
        }

        return users;
    }

    public boolean userExists(String username) {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while checking if the user exists: " + e.getMessage());
        }

        return false;
    }


    public boolean isPasswordCorrect(String username, String password) {
        String query = "SELECT password FROM Users WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return storedPassword.equals(password);
            }
        } catch (Exception e) {
            System.out.println("Password incorrect");
        }

        return false;
    }

    public Connection getConnection(){
        return connection;
    }
}
