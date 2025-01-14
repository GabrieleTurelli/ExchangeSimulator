package model.client;

import model.db.UsersDAO;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginModel extends UsersDAO {
    public LoginModel(Connection connection) {
        super(connection);
    }
}
