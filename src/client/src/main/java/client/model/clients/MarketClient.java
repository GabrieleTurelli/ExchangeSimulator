package client.model.clients;

import java.io.IOException;

import client.model.market.DailyMarketData;
import client.model.market.Kline;
import client.model.market.KlineHistory;

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
            System.out.println("Error in getDailyMarketData: " + response);
            return null;

        }
    }

    public static KlineHistory getHistory(String coin) throws IOException {
        ClientConnection connection = new ClientConnection();
        System.out.println("Requesting price for " + coin);
        connection.sendRequest("\\get-daily-market-data " + coin);
        String response = connection.readResponse();
        System.out.println("Request sent : " + response);

        KlineHistory klineHistory = new KlineHistory();

        if (response.contains("OK")) {
            String klineHistoryResponse = response.split(";")[1];
            String[] klineHistoryParts = klineHistoryResponse.split("|");

            for (String kline : klineHistoryParts) {
                String[] klineParts = kline.split(",");
                double open = Double.parseDouble(klineParts[0].split("=")[1]);
                double high = Double.parseDouble(klineParts[1].split("=")[1]);
                double low = Double.parseDouble(klineParts[2].split("=")[1]);
                double close = Double.parseDouble(klineParts[3].split("=")[1]);

                klineHistory.add(new Kline(open, high, low, close));

            }

            return klineHistory;
        } else {
            System.out.println("Error in getHistory: " + response);
            return null;
        }

    }
}