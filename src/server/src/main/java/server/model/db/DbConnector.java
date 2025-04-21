package server.model.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DbConnector {
    private static final String DB_DIR  = "db";
    private static final String DB_FILE = "exchange.db";
    private static final String URL = "jdbc:sqlite:" + DB_DIR + "/" + DB_FILE
        + "?journal_mode=WAL&cache=shared&busy_timeout=5000";

    private static final HikariDataSource DS;
    static {
        try {
            ensureDbExists();
        } catch (IOException e) {
            throw new RuntimeException("Could not create DB file", e);
        }
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(URL);
        cfg.setMaximumPoolSize(5);
        cfg.setMinimumIdle(1);
        cfg.setPoolName("SqlitePool");
        DS = new HikariDataSource(cfg);
    }

    public static DataSource getDataSource() {
        return DS;
    }

    public static Connection getConnection() throws SQLException {
        Connection connection =  DS.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    private static void ensureDbExists() throws IOException {
        File dir = new File(DB_DIR);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory " + DB_DIR);
        }
        File db = new File(dir, DB_FILE);
        if (!db.exists() && !db.createNewFile()) {
            throw new IOException("Failed to create file " + db.getPath());
        }
    }
}

