package client.view.components.layout;

import client.view.components.ui.tradepanel.TradePanel;
import client.view.theme.Theme;
import javafx.scene.layout.GridPane;

public class TradePanelSection extends BaseSection {
    private TradePanel tradePanel;

    public TradePanelSection(GridPane gridPane, double widthMultiplier, double heightMultiplier, String coin,
            double usdtAvailable, double coinAvailable) {
        super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
        this.tradePanel = new TradePanel(coin, usdtAvailable, coinAvailable);
        this.getChildren().add(tradePanel);
    }

    public TradePanel getTradePanel() {
        return tradePanel;
    }
}
