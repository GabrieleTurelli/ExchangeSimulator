package client.view.components.ui;

import client.view.theme.Theme;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class IconButton extends Button{ 
   
    public IconButton(String iconPath, int size){
        super();
        ImageView icon = new ImageView(iconPath);
        icon.setFitHeight(size);
        icon.setPreserveRatio(true);
        this.setGraphic(icon);
        this.updateStyle(Theme.HEX_COLOR.BACKGROUND, 25);
        this.setOnMouseEntered( event ->
                this.updateStyle(Theme.HEX_COLOR.SURFACE, 25)
        );
        this.setOnMouseExited( event ->
                this.updateStyle(Theme.HEX_COLOR.BACKGROUND, 25)
        );
    }
    
    public final void updateStyle(String backgroundColor, int radius){
        String style = String.format(
                    "-fx-background-color: %s; -fx-cursor: hand; " +
                    "-fx-background-radius: %d;", backgroundColor, radius
                );
        this.setStyle(style);    
    }
}
