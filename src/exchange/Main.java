import model.client.LoginModel;
import model.db.DbConnector;
import utils.SceneManager;
import view.screen.LoginScreen;

//public class Main {
//    public static void main(String[] args) throws SQLException, IOException {
////        ExchangeGui.launch(ExchangeGui.class, args);
//            LoginScreen.launch(LoginScreen.class, args);
//        ArrayList<String> coins = new ArrayList<>(Arrays.asList("BTCUSDT", "ETHUSDT", "XRPUSDT"));
//
//        try {
//            DbInitializer.initializeDatabase(coins);
//
//            try (var connection = DbConnector.getConnection()) {
//                DbInitializer.createUsersTable(connection);
//                DbInitializer.createUserTable(connection, "JohnDoe");
//            }
//
//            System.out.println("All tables initialized successfully.");
//        } catch (Exception e) {
//            System.err.println("Error during database initialization: " + e.getMessage());
//            e.printStackTrace();
//        }


//        CoinDAO btcDao = new CoinDAO("BTCUSDT");
//        btcDao.deleteAllData();
//        btcDao.populateTable(90000.0, 20);
//        TreeMap<LocalDate, Double> data = btcDao.getData();
//        System.out.println(data);
//
//        CoinsDAO coinsDao = new CoinsDAO();
//        coinsDao.addData("BTCUSDT");
//        coinsDao.addData("ETHUSDT");
//        coinsDao.addData("XRPUSDT");


//    }
//}

//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import view.screen.ExchangeScreen;
//import view.screen.LoginScreen;
//
//public class Main extends Application {
//
//    private Stage primaryStage;
//
//    @Override
//    public void start(Stage stage) {
//        this.primaryStage = stage;
//        showLoginScreen();
//        primaryStage.setTitle("Exchange Simulator");
//        primaryStage.show();
//    }
//
//    private void showLoginScreen() {
//        LoginScreen loginScreen = new LoginScreen();
//
//        loginScreen.getLoginButton().setOnAction(event -> {
////            boolean isLoginSuccessful = simulateLogin(
////                    loginScreen.getUsernameField().getText(),
////                    loginScreen.getPasswordField().getText()
////            );
//
////            if (isLoginSuccessful) {
////                showExchangeScreen();
////            } else {
////                loginScreen.setErrorMessage("Invalid username or password.");
////            }
//        });
//
//        primaryStage.setScene(new Scene(loginScreen, 300, 400));
//    }
//
//    private void showExchangeScreen() {
//        ExchangeScreen exchangeScreen = new ExchangeScreen();
//        primaryStage.setScene(new Scene(exchangeScreen, 1280, 720));
//    }
//
//    private boolean simulateLogin(String username, String password) {
//        return "admin".equals(username) && "password".equals(password);
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.LoginController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        SceneManager sceneManager = new SceneManager(primaryStage);
        Connection dbConnection = DbConnector.getConnection();

        LoginScreen loginScreen = new LoginScreen();
        LoginModel loginModel = new LoginModel(dbConnection);

        new LoginController(loginScreen, loginModel, sceneManager);
        primaryStage.setScene(new Scene(loginScreen, 300, 400));
        primaryStage.setTitle("Login Screen");
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
