package client.view.theme;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Theme {

    public static class COLOR {
        public static final Color PRIMARY = Color.web("#F3622D");
        public static final Color PRIMARY_VARIANT = Color.web("#E3521D");
        public static final Color SECONDARY = Color.web("#03DAC6");
        public static final Color TEXT_PRIMARY = Color.web("#FFFFFF");
        public static final Color TEXT_SECONDARY = Color.web("#B3B3B3");
        public static final Color TEXT_TERTIARY = Color.web("#404040");
        public static final Color BACKGROUND = Color.web("#161a1e");
        public static final Color SURFACE = Color.web("#1E2329");
        public static final Color BORDER = Color.web("#787878");
        public static final Color ERROR = Color.web("#CF6679");
        public static final Color ON_PRIMARY = Color.web("#000000");
        public static final Color ON_SECONDARY = Color.web("#000000");
        public static final Color ON_BACKGROUND = Color.web("#FFFFFF");
        public static final Color ON_SURFACE = Color.web("#8F8F8F");
        public static final Color ON_ERROR = Color.web("#000000");
        public static final Color GREEN = Color.web("#28a745");
        public static final Color RED = Color.web("#dc3545");
        public static final Color CHART_BACKGROUND = Color.web("#2e2e2e");
    }

    public static class HEX_COLOR {
        public static final String PRIMARY = "#F3622D";
        public static final String PRIMARY_VARIANT = "#3700B3";
        public static final String SECONDARY = "#03DAC6";
        public static final String TEXT_PRIMARY = "#FFFFFF";
        public static final String TEXT_SECONDARY = "#B3B3B3";
        public static final String TEXT_TERTIARY = "#404040";
        public static final String BACKGROUND = "#161a1e";
        public static final String SURFACE = "#1E2329";
        public static final String BORDER = "#787878";
        public static final String ERROR = "#CF6679";
        public static final String ON_PRIMARY = "#000000";
        public static final String ON_SECONDARY = "#000000";
        public static final String ON_BACKGROUND = "#FFFFFF";
        public static final String ON_SURFACE = "#8F8F8F";
        public static final String ON_ERROR = "#000000";
        public static final String GREEN = "#28a745";
        public static final String DARK_GREEN = "#218838";
        public static final String RED = "#dc3545";
        public static final String DARK_RED = "#c82333";
        public static final String CHART_BACKGROUND = "#2e2e2e";
    }

    public static class FONT_SIZE {
        public static final int EXTRA_LARGE = 24;
        public static final int LARGE = 20;
        public static final int MEDIUM = 16;
        public static final int SMALL = 12;
        public static final int EXTRA_SMALL = 10;
    }

    public static class FONT_WEIGHT {
        public static final FontWeight BOLD = FontWeight.BOLD;
        public static final FontWeight SEMI_BOLD = FontWeight.SEMI_BOLD;
        public static final FontWeight NORMAL = FontWeight.NORMAL;
        public static final FontWeight LIGHT = FontWeight.LIGHT;
    }

    public static class FONT_STYLE {
        public static final Font TITLE = Font.font("Arial", FontWeight.BOLD, FONT_SIZE.EXTRA_LARGE);
        public static final Font SUBTITLE = Font.font("Arial", FontWeight.SEMI_BOLD, FONT_SIZE.MEDIUM);
        public static final Font BODY = Font.font("Arial", FontWeight.NORMAL, FONT_SIZE.MEDIUM);
        public static final Font CAPTION = Font.font("Arial", FontWeight.NORMAL, FONT_SIZE.SMALL);
        public static final Font ITALIC = Font.font("Arial", FontPosture.ITALIC, FONT_SIZE.MEDIUM);
    }

    public static class SPACING {
        public static final int SMALL = 5;
        public static final int MEDIUM = 10;
        public static final int LARGE = 20;
        public static final int EXTRA_LARGE = 30;
    }

    public static class BORDER_RADIUS {
        public static final double SMALL = 5.0;
        public static final double MEDIUM = 10.0;
        public static final double LARGE = 15.0;
    }

    public static class SHADOW {
        public static final String BOX_SHADOW_LIGHT = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0.5, 0, 0);";
        public static final String BOX_SHADOW_HEAVY = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 20, 0.8, 0, 0);";
    }
}
