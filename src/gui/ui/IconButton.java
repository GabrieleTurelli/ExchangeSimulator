package gui.ui;

import javafx.scene.control.Button;

import javafx.scene.image.ImageView;

public class IconButton extends Button{ 
   
    public IconButton(String iconPath, int size){
        super();
        ImageView icon = new ImageView(iconPath);
        icon.setFitHeight(size);
        icon.setPreserveRatio(true);
        this.setGraphic(icon);
        this.updateStyle(THEME.HEX_COLOR.BACKGROUND, 25);
        this.setOnMouseEntered( event ->
                this.updateStyle(THEME.HEX_COLOR.HOVER, 25)
        );
        this.setOnMouseExited( event ->
                this.updateStyle(THEME.HEX_COLOR.BACKGROUND, 25)
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
