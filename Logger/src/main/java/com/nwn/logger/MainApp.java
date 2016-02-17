package com.nwn.logger;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A program meant to cycle NWN logs. 
 * This program can be used to parse NWN logs into formatted
 * HTML pages. It also renames the original log file, allowing it
 * to be saved. Various options are available to make the parsed
 * log easier to read.
 * 
 * version 1.0
 * @author Caitlin
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Logger");
        stage.setScene(scene);
        setDimensions(stage, 590, 490);
        stage.show();
    }

    public void setDimensions(Stage stage, int height, int width) {
        stage.setMaxHeight(height);
        stage.setMinHeight(height);
        stage.setMaxWidth(width);
        stage.setMinWidth(width);

        stage.setHeight(height);
        stage.setWidth(width);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
