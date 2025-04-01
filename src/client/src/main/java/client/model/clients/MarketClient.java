package client.model.clients;

import java.io.IOException;

import client.model.market.DailyMarketData;

public class MarketClient {

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

            // meant like percentage from the open price to the current price

            double dailychange = open * 100 / close;
            return new DailyMarketData(close, dailychange, low, high);
        } else {
            throw new Exception("TODO Exception to fix");
        }

    }
}