package server.actions;

import java.io.IOException;
import java.sql.SQLException;

import server.model.db.UsersDAO;

public class LoginServer {

    public static String handleLogin(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String password = request.split(" ")[2];
        if (UsersDAO.userExists(username)) {
            if (UsersDAO.isPasswordCorrect(username, password)) {
                return "OK;AUTHENTICATED";
            }
            return "ERROR;Password incorrect";
        }
        return "ERROR;User doesn't exist";
    }
}
