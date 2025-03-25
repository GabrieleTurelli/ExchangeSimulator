// import java.io.Console;

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import server.Server;
// import utils.SceneManager;

// import java.io.IOException;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.Arrays;

// import client.view.screen.LoginScreen;

// import client.controller.LoginController;
// import client.model.LoginClient;
// import client.model.db.CoinDAO;
// import client.model.db.DbConnector;
// import client.model.db.DbInitializer;
// import client.model.db.UsersDAO;
// import client.model.user.User;

// import static javafx.application.Platform.exit;

// //public class Main {
// //    public static void main(String[] args) throws SQLException, IOException {
// ////        ExchangeGui.launch(ExchangeGui.class, args);
// //            LoginScreen.launch(LoginScreen.class, args);
// //        ArrayList<String> coins = new ArrayList<>(Arrays.asList("BTCUSDT", "ETHUSDT", "XRPUSDT"));
// //
// //        try {
// //            DbInitializer.initializeDatabase(coins);
// //
// //            try (var connection = DbConnector.getConnection()) {
// //                DbInitializer.createUsersTable(connection);
// //                DbInitializer.createUserTable(connection, "JohnDoe");
// //            }
// //
// //            System.out.println("All tables initialized successfully.");
// //        } catch (Exception e) {
// //            System.err.println("Error during database initialization: " + e.getMessage());
// //            e.printStackTrace();
// //        }


// //        CoinDAO btcDao = new CoinDAO("BTCUSDT");
// //        btcDao.deleteAllData();
// //        btcDao.populateTable(90000.0, 20);
// //        TreeMap<LocalDate, Double> data = btcDao.getData();
// //        System.out.println(data);
// //
// //        CoinsDAO coinsDao = new CoinsDAO();
// //        coinsDao.addData("BTCUSDT");
// //        coinsDao.addData("ETHUSDT");
// //        coinsDao.addData("XRPUSDT");


// //    }
// //}

// public class Main extends Application {
//     private static Thread serverThread;
//     private final ArrayList<String> coins = new ArrayList<String>(Arrays.asList("BTC", "ETH"));

//     @Override
//     public void start(Stage primaryStage) throws IOException, SQLException {
//         User user = new User("lillo");
//         user.createWalletFromString("BTC=10.0000,ETH=10.0");
//         DbInitializer.initializeDatabase(coins);
//         CoinDAO btcDAO = new CoinDAO("BTC");
//         CoinDAO ethDAO = new CoinDAO("ETH");
//         btcDAO.populateCoinTable(90.000, 100);
//         ethDAO.populateCoinTable(3000, 100);

//         serverThread = new Thread(() -> {
//             try {
//                 Server.main(new String[]{});
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         });
//         serverThread.setDaemon(true);
//         serverThread.start();

//         SceneManager sceneManager = new SceneManager(primaryStage);
//         LoginScreen loginScreen = new LoginScreen();
//         new LoginController(loginScreen, sceneManager);

//         primaryStage.setScene(new Scene(loginScreen, 300, 400));
//         primaryStage.setTitle("Login Screen");
//         primaryStage.setResizable(false);
//         primaryStage.setOnCloseRequest(event -> stopServer());
//         primaryStage.show();
//         System.out.print("ciao");
//     }

//     @Override
//     public void stop() throws Exception {
//         stopServer();
//         super.stop();
//     }

//     private void stopServer() {
//         if (serverThread != null && serverThread.isAlive()) {
//             serverThread.interrupt(); 
//         }
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }
