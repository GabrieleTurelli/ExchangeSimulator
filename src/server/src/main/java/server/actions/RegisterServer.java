package server.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import server.model.db.DbConnector;
import server.model.db.UserDAO;
import server.model.db.UsersDAO;
import server.model.user.User;

public class RegisterServer {
    private final Connection connection;

    public RegisterServer() throws IOException, SQLException {
        this(DbConnector.getConnection());

    }

    public RegisterServer(Connection connection) {
        this.connection = connection;
    }

    public String handleRegistration(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String password = request.split(" ")[2];
        UsersDAO usersDao = new UsersDAO(connection);

        if (usersDao.userExists(username)) {
            return "ERROR;Username already taken";
        }
        usersDao.addUser(username, password);
        UserDAO userDao = new UserDAO(new User(username));
        userDao.initializeUser();
        return "OK;New user created";
    }
}
