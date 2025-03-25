package client.model.db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class DbInitializer {

    public static void initializeDatabase(ArrayList<String> coins) {
        try (Connection connection = DbConnector.getConnection()) {
            createCoinsTables(connection);

            System.out.println("Initializing Database.");
            for (String coin : coins) {
                createCoinTable(connection, coin);
                createOrderBookTable(connection, coin);
            }

            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createCoinsTables(Connection connection) {

        String createCoinsTable = """
            CREATE TABLE IF NOT EXISTS Coins (
                pair TEXT NOT NULL UNIQUE,
                PRIMARY KEY (pair)
            );
        """;
        executeStatement(connection, createCoinsTable);

    }
    public static void createCoinTable(Connection connection, String coin) {
        String coinTable = """
        CREATE TABLE IF NOT EXISTS coin_%s (
            date TEXT NOT NULL,
            price REAL NOT NULL,
            PRIMARY KEY (date)
        );
    """.formatted(coin);

        executeStatement(connection, coinTable);
    }

    public static void createUsersTable(Connection connection){
        String usersTable = """
            CREATE TABLE IF NOT EXISTS Users (
                username TEXT NOT NULL UNIQUE,
                password TEXT
            );
        """;
        executeStatement(connection, usersTable);
    }
    public static void createUserTable(Connection connection, String username) {
        String userTable = """
            CREATE TABLE IF NOT EXISTS user_%s (
                coin TEXT NOT NULL,
                quantity REAL NOT NULL,
            );
        """.formatted(username);

        executeStatement(connection, userTable);
    }

    public static void createOrderBookTable(Connection connection, String coinName) {
        String createCoinOrderBookTable = """
        CREATE TABLE IF NOT EXISTS orderbooks_%s (
            price REAL PRIMARY KEY,
            quantity REAL NOT NULL,
            is_bid BOOLEAN NOT NULL
        );
    """.formatted(coinName);

        executeStatement(connection, createCoinOrderBookTable);
    }

    static void executeStatement(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void dropTable(Connection connection, String tableName) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + tableName;
        executeStatement(connection, dropTableQuery);
        System.out.println("Table '" + tableName + "' has been removed (if it existed).");
    }
}
