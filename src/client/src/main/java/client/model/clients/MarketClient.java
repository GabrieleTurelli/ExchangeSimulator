package client.model.clients;

import java.io.IOException;

import client.model.market.DailyMarketData;
import client.model.market.Kline;
import client.model.market.KlineHistory;
import client.model.market.OrderBookData;
import client.model.market.OrderBookLevelData;

public class MarketClient {

    public static String[] getCoins() {
        try {
            ClientConnection connection = new ClientConnection();
            System.out.println("Requesting coins list ");
            connection.sendRequest("\\get-coins");
            String response = connection.readResponse();
            System.out.println("Request sent : " + response);

            if (response.contains("OK")) {
                return response.split(";")[1].split(",");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static double getCoinPrice(String coin) {
        try {
            ClientConnection connection = new ClientConnection();
            System.out.println("Requesting price for " + coin);
            connection.sendRequest("\\get-last-price " + coin);
            String response = connection.readResponse();
            System.out.println("Request sent : " + response);

            if (response.contains("OK")) {
                double price = Double.parseDouble(response.split(";")[1]);
                return price;
            } else {
                return -1.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0;
        }

    }

    public static DailyMarketData getDailyMarketData(String coin) throws IOException, Exception {
        ClientConnection connection = new ClientConnection();
        System.out.println("Requesting price for " + coin);
        connection.sendRequest("\\get-daily-market-data " + coin);
        String response = connection.readResponse();
        System.out.println("Request sent : " + response);

        if (response.contains("OK")) {
            String marketDataResponse = response.split(";")[1];
            String[] marketDataParts = marketDataResponse.split(",");

            double open = Double.parseDouble(marketDataParts[0].split("=")[1]);
            double high = Double.parseDouble(marketDataParts[1].split("=")[1]);
            double low = Double.parseDouble(marketDataParts[2].split("=")[1]);
            double close = Double.parseDouble(marketDataParts[3].split("=")[1]);

            double dailychange = close * 100 / open - 100;
            return new DailyMarketData(close, dailychange, low, high);
        } else {
            System.out.println("Error in getDailyMarketData: " + response);
            return null;

        }
    }

    public static KlineHistory getHistory(String coin) throws IOException {
        ClientConnection connection = new ClientConnection();
        // System.out.println("Requesting price for " + coin);
        connection.sendRequest("\\get-history " + coin);
        String response = connection.readResponse();
        // System.out.println("Request sent : " + response);

        KlineHistory klineHistory = new KlineHistory();

        if (!response.contains("OK")) {
            System.out.println("Error in getHistory: " + response);
            return null;
        }

        // if (response.contains("OK")) {
        String klineHistoryResponse = response.split(";")[1];
        String[] klineHistoryParts = klineHistoryResponse.split("\\|");

        for (String kline : klineHistoryParts) {
            String[] klineParts = kline.split(",");
            double open = Double.parseDouble(klineParts[0].split("=")[1]);
            double high = Double.parseDouble(klineParts[1].split("=")[1]);
            double low = Double.parseDouble(klineParts[2].split("=")[1]);
            double close = Double.parseDouble(klineParts[3].split("=")[1]);

            klineHistory.add(new Kline(open, high, low, close));

        }
        return klineHistory;

        // } else {

        // System.out.println("Error in getHistory: " + response);
        // return null;
        // }

    }

    public static OrderBookData getOrderBook(String coin) throws IOException {
        ClientConnection connection = new ClientConnection();
        connection.sendRequest("\\get-order-book " + coin);
        String response = connection.readResponse();

        OrderBookData orderBook = new OrderBookData(coin);

        if (!response.contains("OK")) {
            System.out.println("Error in getOrderBook: " + response);
            return null;
        }

        String orderBookResponse = response.split(";")[1];
        String[] orderBookParts = orderBookResponse.split("\\|");

        for (String orderBookString : orderBookParts) {
            String[] orderBookData = orderBookString.replace(" ","").split(",");
            double price = Double.parseDouble(orderBookData[0].split("=")[1]);
            double quantity = Double.parseDouble(orderBookData[1].split("=")[1]);
            Boolean isBid = Boolean.parseBoolean(orderBookData[2].split("=")[1]);
            orderBook.add(new OrderBookLevelData(price, quantity, isBid));

        }
        return orderBook;

    }
}