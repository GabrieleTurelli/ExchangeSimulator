package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import server.model.market.OrderBook;
import server.model.market.OrderBookLevel;

/**
 * DAO for user operations using a DataSource.
 */
public class UsersDAO {
    private final DataSource dataSource;

    public UsersDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addUser(String username, String password) {
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        String dropTableSql   = "DROP TABLE IF EXISTS user_" + username;
        String deleteUserSql  = "DELETE FROM Users WHERE username = ?";
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement dropStmt = conn.prepareStatement(dropTableSql);
                 PreparedStatement delStmt  = conn.prepareStatement(deleteUserSql)) {
                dropStmt.executeUpdate();
                delStmt.setString(1, username);
                delStmt.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to remove user: " + e.getMessage());
            return false;
        }
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT username FROM Users";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
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
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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

