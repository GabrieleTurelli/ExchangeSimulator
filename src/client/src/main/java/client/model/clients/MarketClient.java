/**
 * Classe che gestisce la richiesta di login al server.
 *
 * @author Gabriele Turelli
 * @version 1.0
 */
package client.model.clients;

import java.io.IOException;

import client.model.market.DailyMarketData;
import client.model.market.Kline;
import client.model.market.KlineHistory;
import client.model.market.OrderBookData;
import client.model.market.OrderBookLevelData;

public class MarketClient {

    /**
     * Invia una richiesta al server per ottenere la lista delle coin disponibili.
     * 
     * @return un array di stringhe contenente i nomi delle coin, oppure null in
     *         caso di errore.
     */
    public static String[] getCoins() {
        try {
            try (ClientConnection connection = new ClientConnection()) {
                System.out.println("Requesting coins list ");
                connection.sendRequest("\\get-coins");
                String response = connection.readResponse();
                System.out.println("Request sent : " + response);

                if (response.contains("OK")) {
                    return response.split(";")[1].split(",");
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while getting coins: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.err.println("Error while parsing coins: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while getting coins: " + e.getMessage());
            return null;
        }

    }

    /**
     * Invia una richiesta al server per ottenere il prezzo attuale della coin
     * specificata
     * 
     * @param coin la coin di cui si vuole ottenere il prezzo
     * @return il prezzo attuale della coin, oppure -1.0 in caso di errore
     */
    public static double getCoinPrice(String coin) {
        try {
            try (ClientConnection connection = new ClientConnection()) {
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
            }
        } catch (IOException e) {
            System.err.println("Error while getting coin price: " + e.getMessage());
            return -1.0;
        } catch (NumberFormatException e) {
            System.err.println("Error while parsing coin price: " + e.getMessage());
            return -1.0;
        } catch (Exception e) {
            System.err.println("Unexpected error while getting coin price: " + e.getMessage());
            return -1.0;
        }

    }

    /**
     * Invia una richiesta al server per ottenere i dati di mercato giornalieri
     * della coin specificata.
     * 
     * @param coin la coin di cui si vogliono ottenere i dati di mercato giornalieri
     * @return un oggetto {@link DailyMarketData} contenente i dati di mercato,
     *         oppure null in caso di errore
     */
    public static DailyMarketData getDailyMarketData(String coin) {
        try (ClientConnection connection = new ClientConnection()) {
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
        } catch (NumberFormatException e) {
            System.err.println("Error while parsing daily market data: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Error while getting daily market data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Invia una richiesta al server per ottenere la cronologia dei prezzi della
     * coin
     * specificata.
     * 
     * @param coin la coin di cui si vuole ottenere la cronologia dei prezzi
     * @return un oggetto {@link KlineHistory} contenente la cronologia dei prezzi,
     *         oppure null in caso di errore
     */
    public static KlineHistory getHistory(String coin) {
        try (ClientConnection connection = new ClientConnection()) {
            connection.sendRequest("\\get-history " + coin);
            String response = connection.readResponse();

            KlineHistory klineHistory = new KlineHistory();

            if (!response.contains("OK")) {
                System.out.println("Error in getHistory: " + response);
                return null;
            }

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
        } catch (NumberFormatException e) {
            return null;
        } catch (IOException e) {
            System.err.println("Error while getting history: " + e.getMessage());
            return null;
        }
    }

    /**
     * Invia una richiesta al server per ottenere i dati dell'order book della coin
     * specificata.
     * 
     * @param coin la coin di cui si vogliono ottenere i dati dell'order book
     * @return un oggetto {@link OrderBookData} contenente i dati dell'order book,
     *         oppure null in caso di errore
     */
    public static OrderBookData getOrderBook(String coin) {
        OrderBookData orderBook;
        try (ClientConnection connection = new ClientConnection()) {
            connection.sendRequest("\\get-orderbook " + coin);
            String response = connection.readResponse();
            orderBook = new OrderBookData(coin);
            if (!response.contains("OK")) {
                System.out.println("Error in getOrderBook: " + response);
                return null;
            }
            String orderBookResponse = response.split(";")[1];
            String[] orderBookParts = orderBookResponse.split("\\|");
            for (String orderBookString : orderBookParts) {
                String[] orderBookData = orderBookString.replace(" ", "").split(",");
                double price = Double.parseDouble(orderBookData[0].split("=")[1]);
                double quantity = Double.parseDouble(orderBookData[1].split("=")[1]);
                Boolean isBid = Boolean.valueOf(orderBookData[2].split("=")[1]);
                orderBook.add(new OrderBookLevelData(price, quantity, isBid));

            }
        } catch (IOException e) {
            System.err.println("Error while getting order book: " + e.getMessage());
            return null;
        }
        return orderBook;

    }
}