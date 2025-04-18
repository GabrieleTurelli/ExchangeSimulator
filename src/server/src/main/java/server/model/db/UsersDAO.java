package server.model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsersDAO {

    private final Connection connection;


    public UsersDAO() throws SQLException, IOException {
        this.connection =  DbConnector.getConnection();

    }

    public UsersDAO(Connection connection){
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

    public void removeUser(String username) {
        String dropTableQuery = "DROP TABLE IF EXISTS user_" + username;
        String deleteUserQuery = "DELETE FROM Users WHERE username = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement dropTableStatement = connection.prepareStatement(dropTableQuery)) {
                dropTableStatement.executeUpdate();
            }

            try (PreparedStatement deleteUserStatement = connection.prepareStatement(deleteUserQuery)) {
                deleteUserStatement.setString(1, username);
                deleteUserStatement.executeUpdate();
            }

            connection.commit();

        } catch (Exception e) {
            System.out.println("Failed to remove user: " + e.getMessage());
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
        String query = "SELECT COUNT(*) FROM Users WHERE LOWER(username) = LOWER(?)";
    
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("Checking username: '" + username + "'");
            statement.setString(1, username);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    System.out.println("User count: " + count);
                    return count > 0;
                } else {
                    System.out.println("No result from query.");
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
