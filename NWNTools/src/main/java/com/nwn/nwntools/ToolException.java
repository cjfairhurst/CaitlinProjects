package com.nwn.nwntools;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;

/**
 *
 * @author Caitlin
 */
public class ToolException extends Exception {

    public ToolException() {

    }

    public ToolException(String message) {
        super(message);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
        dialogPane.setId("dialog");
        
        alert.showAndWait();
    }

    public ToolException(Throwable cause) {
        super(cause);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(cause.toString());
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
        dialogPane.setId("dialog");
        
        alert.showAndWait();
    }

    public ToolException(String message, Throwable cause) {
        super(cause);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(cause.toString());
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
        dialogPane.setId("dialog");
        
        alert.showAndWait();
    }
}
