package model.db;

import java.sql.Connection;
import java.sql.Statement;

public class DbInitializer {

    public static void initializeDatabase() {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS Users (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                email TEXT
            );
        """;

        String createCoinsTable = """
            CREATE TABLE IF NOT EXISTS Coins (
                coin_id INTEGER PRIMARY KEY AUTOINCREMENT,
                coin_name TEXT NOT NULL UNIQUE,
                ticker TEXT NOT NULL UNIQUE
            );
        """;

        String createUserCoinsTable = """
            CREATE TABLE IF NOT EXISTS UserCoins (
                user_coin_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                coin_id INTEGER NOT NULL,
                quantity REAL NOT NULL,
                FOREIGN KEY (user_id) REFERENCES Users(user_id),
                FOREIGN KEY (coin_id) REFERENCES Coins(coin_id)
            );
        """;

        try (Connection connection = DbConnector.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createUsersTable);
            statement.execute(createCoinsTable);
            statement.execute(createUserCoinsTable);

            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
