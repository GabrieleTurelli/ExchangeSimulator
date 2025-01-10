package view.components.layout;

import javafx.scene.layout.GridPane;
import view.components.ui.tradepanel.TradePanel;
import view.theme.Theme;

public class TradePanelSection extends BaseSection{
   public TradePanelSection(GridPane gridPane, double widthMultiplier, double heightMultiplier) {
       super(gridPane, Theme.COLOR.BACKGROUND, widthMultiplier, heightMultiplier);
       this.getChildren().add(new TradePanel());
   }
}
