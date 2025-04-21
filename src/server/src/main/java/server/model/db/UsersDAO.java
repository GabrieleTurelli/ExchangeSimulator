package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    private final Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addUser(String username, String password) {
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to add user: " + e.getMessage());
            return false;
        }
    }

    public boolean removeUser(String username) {
        String dropTableSql = "DROP TABLE IF EXISTS user_" + username;
        String deleteUserSql = "DELETE FROM Users WHERE username = ?";
        try (PreparedStatement dropStmt = connection.prepareStatement(dropTableSql);
             PreparedStatement delStmt = connection.prepareStatement(deleteUserSql)) {

            connection.setAutoCommit(false);
            dropStmt.executeUpdate();
            delStmt.setString(1, username);
            delStmt.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to remove user: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback failed: " + rollbackEx.getMessage());
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Could not reset auto-commit: " + ex.getMessage());
            }
        }
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT username FROM Users";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch users: " + e.getMessage());
        }
        return users;
    }

    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE LOWER(username)=LOWER(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
        }
        return false;
    }

    public boolean isPasswordCorrect(String username, String password) {
        String sql = "SELECT password FROM Users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return password.equals(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error validating password: " + e.getMessage());
        }
        return false;
    }
}
