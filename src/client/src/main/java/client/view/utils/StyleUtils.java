package client.view.utils;


public class StyleUtils {

    public static String generateStyle(String backgroundColor, int borderRadius) {
        return String.format("-fx-background-color: %s; -fx-cursor: hand; -fx-border-radius: %d; -fx-background-radius: %d;",
                backgroundColor, borderRadius, borderRadius);
    }

    public static String generateTextStyle(int fontSize, String textColor) {
        return String.format("-fx-font-size: %dpx; -fx-text-fill: %s;", fontSize, textColor);
    }

    public static String combineStyles(String... styles) {
        StringBuilder combinedStyle = new StringBuilder();
        for (String style : styles) {
            combinedStyle.append(style).append(" ");
        }
        return combinedStyle.toString().trim();
    }

    public static String rgbToHex(int red, int green, int blue) {
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}
