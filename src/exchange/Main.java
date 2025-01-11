import model.db.CoinDAO;
import model.db.CoinsDAO;
import model.db.DbConnector;
import model.db.DbInitializer;
import view.ExchangeGui;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
//        ExchangeGui.launch(ExchangeGui.class, args);
        ArrayList<String> coins = new ArrayList<>(Arrays.asList("BTCUSDT", "ETHUSDT", "XRPUSDT"));

        try {
            DbInitializer.initializeDatabase(coins);

            try (var connection = DbConnector.getConnection()) {
                DbInitializer.createUsersTable(connection);
                DbInitializer.createUserTable(connection, "JohnDoe");
            }

            System.out.println("All tables initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error during database initialization: " + e.getMessage());
            e.printStackTrace();
        }


        CoinDAO btcdao = new CoinDAO("BTCUSDT");
        btcdao.deleteAllCoinData();
        btcdao.populateTable(90000.0, 20);
        TreeMap<LocalDate, Double> data = btcdao.getCoinData();
        System.out.println(data);

    }
}
