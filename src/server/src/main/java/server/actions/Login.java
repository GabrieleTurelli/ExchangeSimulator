package server.actions;

import java.io.IOException;
import java.sql.SQLException;

import client.model.db.UserDAO;
import client.model.db.UsersDAO;
import client.model.user.User;

public class Login extends UsersDAO {

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
