package com.nwn.logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Caitlin
 */
public class LoggerException extends Exception {

    public LoggerException() {

    }

    public LoggerException(String message) {
        super(message);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public LoggerException(Throwable cause) {
        super(cause);
    }

    public LoggerException(String message, Throwable cause) {
        super(cause);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(cause.toString());
        alert.showAndWait();
    }
}
