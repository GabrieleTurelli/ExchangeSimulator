import model.db.DbConnector;
import model.db.DbInitializer;
import view.ExchangeGui;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
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
    }
}
