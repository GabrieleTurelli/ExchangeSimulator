package client.view.components.layout;

import client.view.components.ui.tradepanel.TradePanel;
import client.view.theme.Theme;
import javafx.scene.layout.GridPane;

public class TradePanelSection extends BaseSection{
   public TradePanelSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
       super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
       this.getChildren().add(new TradePanel());
   }
}
