package server.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import server.model.db.DbConnector;
import server.model.db.UserDAO;
import server.model.db.UsersDAO;

public class UserServer {
    private final Connection connection;

    public UserServer() throws IOException, SQLException{
        this(DbConnector.getConnection());

    }

    public UserServer(Connection connection) {
        this.connection = connection;
    }

    public String handleGetWallet(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        UsersDAO usersDao = new UsersDAO();

        if (usersDao.userExists(username)) {
            UserDAO userDao = new UserDAO(username);
            return "OK;"+ userDao.getUser();
        }else{
            return "ERROR;User " + username + " does not exist";
        }
    }

}
