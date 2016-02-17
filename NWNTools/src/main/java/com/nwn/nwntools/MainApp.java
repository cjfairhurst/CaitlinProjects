package com.nwn.nwntools;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("NWNTools");
        stage.setScene(scene);
        setDimensions(stage, 240, 340);
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
