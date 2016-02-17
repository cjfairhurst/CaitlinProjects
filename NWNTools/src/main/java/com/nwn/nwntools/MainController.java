package com.nwn.nwntools;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
    @FXML
    private void openLogger() throws ToolException {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Logger.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            LoggerController controller = fxmlLoader.<LoggerController>getController();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            setDimensions(stage, 590, 500);

            stage.setTitle("Logger");
            stage.show();
        } catch (Exception ex) {
            throw new ToolException("An error occured opening NoteKeeper.", ex);
        }
    }

    @FXML
    private void openNoteKeeper() throws ToolException {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NoteKeeper.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            NoteController controller = fxmlLoader.<NoteController>getController();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            setDimensions(stage, 600, 720);

            stage.setTitle("NoteKeeper");
            stage.show();
        } catch (Exception ex) {
            throw new ToolException("An error occured opening NoteKeeper.", ex);
        }
    }

    @FXML
    private void openPointCalculator() throws ToolException {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PointCalculator.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            PointCalculatorController controller = fxmlLoader.<PointCalculatorController>getController();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setMinWidth(480);
            stage.setMinHeight(500);
            stage.setMaxWidth(500);
            stage.setMaxHeight(575);
            stage.setWidth(500);
            stage.setHeight(575);

            stage.setTitle("PointCalculator");
            stage.show();
        } catch (Exception ex) {
            throw new ToolException("An error occured opening NoteKeeper.", ex);
        }
    }
    
    @FXML
    private void openCharacterNotes() throws ToolException {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Character.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            CharacterController controller = fxmlLoader.<CharacterController>getController();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            setDimensions(stage, 840, 1070);

            stage.setTitle("CharacterNotes");
            stage.show();
        } catch (Exception ex) {
            throw new ToolException("An error occured opening NoteKeeper.", ex);
        }
    }

    public void setDimensions(Stage stage, int height, int width) {
        stage.setMaxHeight(height);
        stage.setMinHeight(height);
        stage.setMaxWidth(width);
        stage.setMinWidth(width);

        stage.setHeight(height);
        stage.setWidth(width);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
