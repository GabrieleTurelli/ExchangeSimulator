package client.controller.exchangecontroller;

import client.model.user.User;
import client.view.screen.ExchangeScreen;
import client.view.utils.SceneManager;


public class ExchangeController {

    private final ExchangeScreen exchangeScreen;
    private final User user;

    public ExchangeController(ExchangeScreen exchangeScreen, SceneManager sceneManager, User user) {
        System.out.println("ex 1");
        this.exchangeScreen = exchangeScreen;
        System.out.println("ex 2");
        this.user = user;

        // initialize();
    }

    // private void initialize() {
    //     exchangeScreen.getTradePanelSection().getTradePanel().get
    // }
        
    public ExchangeScreen getExchangeScreen() {
        return exchangeScreen;
    }
}
