package server.model.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DbInitializer {
    private final Connection connection;
    private final HashMap<String, Double> coins;
    private final int historyDays = 50;

    public DbInitializer(HashMap<String, Double> coins) throws IOException, SQLException {
        this.connection = DbConnector.getConnection();
        this.coins = coins;
    }

    public void initializeDatabase() throws SQLException, IOException {
        System.out.println("Initializing Database.");

        System.out.println("Creating coins table");
        createCoinsTable();
        System.out.println("Creating users table");
        createUsersTable();

        for (String coin : coins.keySet()) {
            System.out.println("Creating coin : " + coin + "  table");
            addCoinToCoinsTable(coin);
            createCoinTable(coin);
            createOrderBookTable(coin);

            System.out.println("Populating the table with random data for: " + coin);
            CoinDAO coinDao = new CoinDAO(coin);
            coinDao.populateCoinTable(coins.get(coin), historyDays);
        }
        // createUsersTable();

        System.out.println("Database initialized successfully.");
    }

    public void initializeCoin(String coin, Double initialPrice) {

    }

    public void createCoinsTable() {

        String createCoinsTable = """
                CREATE TABLE IF NOT EXISTS Coins (
                    pair TEXT NOT NULL UNIQUE,
                    PRIMARY KEY (pair)
                    );
                    """;
        executeStatement(createCoinsTable);

    }

    public void addCoinToCoinsTable(String coin) {
        String insertCoin = """
                    INSERT OR IGNORE INTO Coins (pair)
                    VALUES (?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCoin)) {
            preparedStatement.setString(1, coin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCoinTable(String coin) {
        String coinTable = """
                    CREATE TABLE IF NOT EXISTS coin_%s (
                        date TEXT NOT NULL,
                        open REAL NOT NULL,
                        high REAL NOT NULL,
                        low REAL NOT NULL,
                        close REAL NOT NULL,
                        PRIMARY KEY (date)
                    );
                """.formatted(coin);

        executeStatement(coinTable);
    }

    public void createUsersTable() {
        String usersTable = """
                    CREATE TABLE IF NOT EXISTS Users (
                        username TEXT NOT NULL UNIQUE,
                        password TEXT
                    );
                """;
        executeStatement(usersTable);
    }

    public void createUserTable(Connection connection, String username) {
        String userTable = """
                    CREATE TABLE IF NOT EXISTS user_%s (
                        coin TEXT NOT NULL,
                        quantity REAL NOT NULL,
                    );
                """.formatted(username);

        executeStatement(userTable);
    }

    public void createOrderBookTable(String coinName) {
        String createCoinOrderBookTable = """
                    CREATE TABLE IF NOT EXISTS orderbooks_%s (
                        price REAL PRIMARY KEY,
                        quantity REAL NOT NULL,
                        is_bid BOOLEAN NOT NULL
                    );
                """.formatted(coinName);

        executeStatement(createCoinOrderBookTable);
    }

    private void executeStatement(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropTable(String tableName) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + tableName;
        executeStatement(dropTableQuery);
        System.out.println("Table '" + tableName + "' has been removed (if it existed).");
    }
}
