package model.server;

import model.db.DbInitializer;
import model.db.UserDAO;
import model.db.UsersDAO;
import model.user.User;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterServer {

    public static String handleRegistration(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String password = request.split(" ")[2];

        if (UsersDAO.userExists(username)){
            return "ERROR;Username already taken";
        }
        UsersDAO.addUser(username, password);
        UserDAO userDao = new UserDAO(new User(username));
        userDao.initializeUser();
        return  "OK;New user created";
    }
}
