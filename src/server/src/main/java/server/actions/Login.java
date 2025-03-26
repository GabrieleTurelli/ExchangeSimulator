package server.actions;

import java.io.IOException;
import java.sql.SQLException;

import server.model.db.UserDAO;
import server.model.db.UsersDAO;
import server.model.user.User;

public class Login {

    public static String handleLogin(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String password = request.split(" ")[2];
        if (UsersDAO.userExists(username)) {
            if (UsersDAO.isPasswordCorrect(username, password)) {
                UserDAO userDAO = new UserDAO(new User(username));
                String data = userDAO.getUser();

                return "OK;" + data;
            }
            return "ERROR;Password incorrect";
        }
        return "ERROR;User doesn't exist";
    }
}
