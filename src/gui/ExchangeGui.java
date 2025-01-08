package gui;

import gui.ui.Section;
import gui.ui.SubHeader;
import gui.ui.Header;
import gui.ui.THEME;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExchangeGui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0));
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(60);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(5);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(5);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(60);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(30);
        gridPane.getRowConstraints().addAll(row1, row2, row3, row4);


        Header header = new Header(
               gridPane,
               "/assets/logo.png",
               "Exchange Simulator",
               Color.BLACK,
               1.0,
               0.1);
        SubHeader subHeader = new SubHeader(
                gridPane,
                Color.BLACK,
                1.0,
                0.05);

        Section subHeaderSection = new Section(gridPane, 1.0, 0.05);
        Section chartSection = new Section(gridPane, 0.6, 0.6);
        Section orderBookSection = new Section(gridPane, 0.2, 0.9);
        Section orderSection = new Section(gridPane, 0.2, 0.9);
        Section positionSection = new Section(gridPane, 0.6, 0.3);

        gridPane.add(header, 0, 0, 3, 1);
        gridPane.add(subHeader, 0, 1, 3, 1);
        gridPane.add(chartSection, 0, 2, 1, 1);
        gridPane.add(positionSection, 0, 3, 1, 1);
        gridPane.add(orderBookSection, 1, 2, 1, 2);
        gridPane.add(orderSection, 2, 2, 1, 2);

        Scene scene = new Scene(gridPane, 1280, 720);
        stage.setTitle("Exchange Simulator");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
