package model.server;

import model.db.UserDAO;
import model.db.UsersDAO;
import model.user.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginServer extends UsersDAO {

    public static String handleLogin(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String password = request.split(" ")[2];
        if (userExists(username)){
            if (isPasswordCorrect(username, password)){
                UserDAO userDAO = new UserDAO(new User(username));
                String data = userDAO.getUser();

                return "OK;" + data;
            }
            return "ERROR;Password incorrect";
        }
        return "ERROR;User doesn't exist";
    }
}
