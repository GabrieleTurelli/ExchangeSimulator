package client.controller.exchangecontroller;

import client.model.user.User;
import client.view.screen.ExchangeScreen;
import client.view.utils.SceneManager;


public class ExchangeController {

    private final ExchangeScreen exchangeScreen;
    private final User user;

    public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user) {
        this.exchangeScreen = exchangeScreen;
        this.user = user;

        initialize();
    }

    private void initialize() {
        // Example: Use user data to populate the screen (e.g., trade panel or
        // positions)
        // exchangeScreen.populateUserData(user);
    }

    public ExchangeScreen getExchangeScreen() {
        return exchangeScreen;
    }
}
