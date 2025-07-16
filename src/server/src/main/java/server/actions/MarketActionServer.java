package server.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import server.model.db.CoinDAO;
import server.model.db.OrderBookDAO;
import server.model.db.UserDAO;
import server.model.db.UsersDAO;
import server.model.market.OrderBook;
import server.model.market.OrderBookLevel;

public class MarketActionServer {
    private final Connection connection;

    public MarketActionServer(Connection connection) {
        this.connection = connection;
    }

    public String handleBuyMarket(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String coin = request.split(" ")[2];
        Double purchaseQuantity = Double.valueOf(request.split(" ")[3]);

        UserDAO userDao = new UserDAO(username, connection);
        Double userQuantity = userDao.getCoinQuantity("USDT");
        OrderBookDAO orderBookDao = new OrderBookDAO(coin, connection);
        OrderBook bidOrderBook = orderBookDao.getBidOrderBook();

        if (!isQuantityValid(userQuantity, purchaseQuantity)) {
            return "ERROR;Not enough available USDT in your wallet";
        }

        double quantityLeft = consumeQuantity(purchaseQuantity, bidOrderBook);
        if (quantityLeft == 0.0) {
            return "OK;Order totally filled";
        }

        return "OK;Order partially filled";
    }

    public String handleBuyLimit(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String coin = request.split(" ")[2];
        Double price = Double.parseDouble(request.split(" ")[3]);

        UserDAO userDao = new UserDAO(username, connection);
        CoinDAO coinDAO = new CoinDAO(coin, connection);

        return "";

    }

    public String handleSellMarket(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String coin = request.split(" ")[2];
        Double purchaseQuantity = Double.valueOf(request.split(" ")[3]);

        UserDAO userDao = new UserDAO(username, connection);
        Double userQuantity = userDao.getCoinQuantity("USDT");
        OrderBookDAO orderBookDao = new OrderBookDAO(coin, connection);
        OrderBook askOrderBook = orderBookDao.getAskOrderBook();

        if (!isQuantityValid(userQuantity, purchaseQuantity)) {
            return "ERROR;Not enough available USDT in your wallet";
        }

        double quantityLeft = consumeQuantity(purchaseQuantity, askOrderBook);
        if (quantityLeft == 0.0) {
            return "OK;Order totally filled";
        }

        return "OK;Order partially filled";

    }

    public String handleSellLimit(String request) throws SQLException, IOException {
        String username = request.split(" ")[1];
        String coin = request.split(" ")[2];
        Double price = Double.parseDouble(request.split(" ")[3]);

        UsersDAO usersDao = new UsersDAO(connection);
        CoinDAO coinDAO = new CoinDAO(coin, connection);

        return "";

    }

    private boolean isQuantityValid(Double userQuantity, Double purchaseQuantity) {
        return userQuantity >= purchaseQuantity;
    }

    private double consumeQuantity(double purchaseQuantity, OrderBook orderBook) {
        double quantityLeft = purchaseQuantity;

        for (OrderBookLevel level : orderBook) {
            System.out.println("Bid Order Book level: " + level);
            if (quantityLeft >= level.getQuantity()) {
                quantityLeft -= level.getQuantity();
                level.setQuantity(0.0);
            } else {
                level.setQuantity(level.getQuantity() - purchaseQuantity);
                quantityLeft = 0.0;
                break;
            }
            System.out.println("Quantity left: " + quantityLeft);
        }
        return quantityLeft;
    }

    // private List<String> getOrderDetails(String request, boolean isLimit) {
    //     List<String> orderDetails = new ArrayList<>();
    //     String[] parts = request.split(" ");

    //     orderDetails.add(parts[1]);
    //     orderDetails.add(parts[2]);
    //     orderDetails.add(parts[3]);

    //     if (isLimit) {
    //         orderDetails.add(parts[4]);

    //     }

    //     return orderDetails;

    // }

}
