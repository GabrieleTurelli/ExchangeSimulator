package model.client;

import model.db.UsersDAO;

import java.sql.Connection;

public class RegisterModel extends UsersDAO {
    public RegisterModel(Connection connection) {
        super(connection);
    }
}
