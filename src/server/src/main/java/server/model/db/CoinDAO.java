package server.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.sql.DataSource;
import server.model.market.Kline;
import server.model.market.KlineHistory;

public class CoinDAO {
    private final DataSource ds;
    private final String table;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CoinDAO(String coin, DataSource ds) {
        this.ds    = ds;
        this.table = "coin_" + coin.toUpperCase();
    }

    public void addData(LocalDate d, Kline k) throws SQLException {
        String sql = "INSERT OR REPLACE INTO " + table +
                     " (date, open, high, low, close) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.format(fmt));
            ps.setDouble(2, k.getOpen());
            ps.setDouble(3, k.getHigh());
            ps.setDouble(4, k.getLow());
            ps.setDouble(5, k.getClose());
            ps.executeUpdate();
        }
    }

    public void deleteAllData() throws SQLException {
        String sql = "DELETE FROM " + table;
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    public Kline getLastKline() throws SQLException {
        String sql = "SELECT date, open, high, low, close FROM " + table +
                     " WHERE date = (SELECT MAX(date) FROM " + table + ")";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Kline(
                  rs.getDouble("open"),
                  rs.getDouble("high"),
                  rs.getDouble("low"),
                  rs.getDouble("close")
                );
            }
        }
        return null;
    }

    public KlineHistory getKlineHistory() throws SQLException {
        String sql = "SELECT open, high, low, close FROM " + table;
        KlineHistory h = new KlineHistory();
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                h.add(new Kline(
                  rs.getDouble("open"),
                  rs.getDouble("high"),
                  rs.getDouble("low"),
                  rs.getDouble("close")
                ));
            }
        }
        return h;
    }

    public double getLastPrice() throws SQLException {
        String sql = "SELECT close FROM " + table +
                     " WHERE date = (SELECT MAX(date) FROM " + table + ")";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getDouble("close") : 0.0;
        }
    }

    public void populateCoinTable(double initPrice, int rows) throws SQLException {
        try (Connection c = ds.getConnection()) {
            c.setAutoCommit(false);
            if (!doesExist(c)) {
                createTable(c);
                batchFill(c, initPrice, rows, true);
            } else {
                LocalDate last = getLastDate(c);
                if (last == null) {
                    batchFill(c, initPrice, rows, true);
                } else {
                    batchFill(c, initPrice, rows, false);
                }
            }
            c.commit();
        }
    }

    private boolean doesExist(Connection c) throws SQLException {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, table);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void createTable(Connection c) throws SQLException {
        try (Statement s = c.createStatement()) {
            s.execute(String.format(
              "CREATE TABLE IF NOT EXISTS %s (" +
              "date TEXT NOT NULL, price REAL NOT NULL, PRIMARY KEY(date));",
              table));
        }
    }

    private LocalDate getLastDate(Connection c) throws SQLException {
        String sql = "SELECT MAX(date) AS ld FROM " + table;
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            String v = rs.next() ? rs.getString("ld") : null;
            return v != null ? LocalDate.parse(v, fmt) : null;
        }
    }

    private void batchFill(Connection c, double init, int rows, boolean fromStart) throws SQLException {
        LocalDate start = fromStart
            ? LocalDate.now().minusDays(rows)
            : getLastDate(c).plusDays(1);
        Random rnd = new Random();
        double price = init;
        String sql = "INSERT OR REPLACE INTO " + table +
                     " (date, open, high, low, close) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < rows; i++) {
                LocalDate d = start.plusDays(i);
                price = fillOne(ps, d, price, rnd);
            }
            ps.executeBatch();
        }
    }

    private double fillOne(PreparedStatement ps, LocalDate d, double prev, Random rnd) throws SQLException {
        double open = prev;
        double close = Math.round(open * (1 + (rnd.nextDouble()*6 - 3)/100) *100)/100;
        double high  = Math.round(Math.max(open, close)*(1 + rnd.nextDouble()*0.02)*100)/100;
        double low   = Math.round(Math.min(open, close)*(1 - rnd.nextDouble()*0.02)*100)/100;
        ps.setString(1, d.format(fmt));
        ps.setDouble(2, open);
        ps.setDouble(3, high);
        ps.setDouble(4, low);
        ps.setDouble(5, close);
        ps.addBatch();
        return close;
    }
}
