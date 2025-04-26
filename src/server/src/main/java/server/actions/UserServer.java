package server.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import server.model.db.UserDAO;
import server.model.db.UsersDAO;

public class UserServer {
    private final Connection connection;


    public UserServer(Connection connection) {
        this.connection = connection;
    }

    public String handleGetWallet(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        UsersDAO usersDao = new UsersDAO(connection);

        if (usersDao.userExists(username)) {
            UserDAO userDao = new UserDAO(username, connection);
            return "OK;"+ userDao.getUser();
        }else{
            return "ERROR;User " + username + " does not exist";
        }
    }

}
