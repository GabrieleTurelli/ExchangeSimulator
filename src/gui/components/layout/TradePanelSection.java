package gui.components.layout;

import gui.components.ui.tradepanel.TradePanel;
import gui.theme.Theme;
import javafx.scene.layout.GridPane;

public class TradePanelSection extends BaseSection{
   public TradePanelSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
       super(gridPane, Theme.COLOR.SURFACE, widthMultiplier, heightMultiplier);
       this.getChildren().add(new TradePanel());
   }
}
