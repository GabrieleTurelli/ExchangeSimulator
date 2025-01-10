package exchange.gui.components.layout;

import javafx.scene.layout.GridPane;
import exchange.gui.components.ui.tradepanel.TradePanel;
import exchange.gui.theme.Theme;

public class TradePanelSection extends BaseSection{
   public TradePanelSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
       super(gridPane, Theme.COLOR.SURFACE, widthMultiplier, heightMultiplier);
       this.getChildren().add(new TradePanel());
   }
}
