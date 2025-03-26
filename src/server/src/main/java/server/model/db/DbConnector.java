package server.model.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static final String DB_DIRECTORY = "src/server/db";
    private static final String DB_PATH = DB_DIRECTORY + "/exchange.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection getConnection() throws IOException, SQLException {
        checkDbExists();
        return DriverManager.getConnection(URL);
    }

    private static void checkDbExists() throws IOException {
        File dbDirectory = new File(DB_DIRECTORY);
        System.out.println("Database directory: " + dbDirectory.getAbsolutePath());
        if (!dbDirectory.exists()) {
            boolean dirCreated = dbDirectory.mkdirs();
            if (dirCreated) {
                System.out.println("Database directory created: " + DB_DIRECTORY);
            }
        }

        File dbFile = new File(DB_PATH);
        if (!dbFile.exists()) {
            boolean fileCreated = dbFile.createNewFile();
            if (fileCreated) {
                System.out.println("Database file created: " + DB_PATH);
            }
        }
    }
}
