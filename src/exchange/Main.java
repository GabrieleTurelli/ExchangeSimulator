//import view.ExchangeGui;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Objects;
//
//public class Main {
//    public static void main(String[] args) throws  Exception{
////        ExchangeGui.launch(ExchangeGui.class, args);
//    }
//}
import model.db.DbConnector;
import model.db.DbInitializer;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");

        try {
            DbInitializer.initializeDatabase();
            DbConnector.getConnection() ;
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("File handling error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
