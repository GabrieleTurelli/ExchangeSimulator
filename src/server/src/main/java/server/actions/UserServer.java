package server.actions;

import java.io.IOException;
import java.sql.SQLException;

import server.model.db.UserDAO;
import server.model.db.UsersDAO;

public class UserServer {

    public static String handleGetWallet(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];

        if (!UsersDAO.userExists(username)) {
            UserDAO userDao = new UserDAO(username);
            return "OK;"+ userDao.getUser();
        }else{
            return "ERROR;User does not exist";
        }
    }

}
