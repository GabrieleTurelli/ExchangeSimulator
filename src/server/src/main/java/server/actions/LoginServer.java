package server.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import server.model.db.UsersDAO;

public class LoginServer {
    private final Connection connection;

    public LoginServer(Connection connection) {
        this.connection = connection;
        // this.dataSource = ds;
    }

    public String handleLogin(String request) throws SQLException, IOException {


        String username = request.split(" ")[1];
        String password = request.split(" ")[2];
        UsersDAO usersDAO = new UsersDAO(connection);
        if (usersDAO.userExists(username)) {
            if (usersDAO.isPasswordCorrect(username, password)) {
                return "OK;AUTHENTICATED";
            }
            return "ERROR;Password incorrect";
        }
        return "ERROR;User doesn't exist";
    }
}
