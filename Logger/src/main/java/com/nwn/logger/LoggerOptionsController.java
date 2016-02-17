package com.nwn.logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
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
public class LoggerOptionsController implements Initializable {

    @FXML
    private ColorPicker talkColor;
    @FXML
    private ColorPicker tellColor;
    @FXML
    private ColorPicker whisperColor;
    @FXML
    private ColorPicker shoutColor;
    @FXML
    private ColorPicker partyColor;
    @FXML
    private ColorPicker filterColor;
    @FXML
    private ColorPicker selfColor;

    private Properties properties;

    /**
     * Resets default colors.
     */
    @FXML
    private void setDefaultColors() {
        talkColor.setValue(Color.web(properties.getProperty("DefaultTalkColor")));
        tellColor.setValue(Color.web(properties.getProperty("DefaultTellColor")));
        whisperColor.setValue(Color.web(properties.getProperty("DefaultWhisperColor")));
        shoutColor.setValue(Color.web(properties.getProperty("DefaultShoutColor")));
        partyColor.setValue(Color.web(properties.getProperty("DefaultPartyColor")));
        filterColor.setValue(Color.web(properties.getProperty("DefaultFilterColor")));
        selfColor.setValue(Color.web(properties.getProperty("DefaultSelfColor")));
    }

    /**
     * Saves properties to properties file.
     */
    @FXML
    private void saveProperties() throws LoggerException {
        try {
            OutputStream outputStream = new FileOutputStream("config.properties");

            properties.setProperty("TalkColor", ("#" + talkColor.getValue().toString().substring(2, 8)));
            properties.setProperty("TellColor", ("#" + tellColor.getValue().toString().substring(2, 8)));
            properties.setProperty("WhisperColor", ("#" + whisperColor.getValue().toString().substring(2, 8)));
            properties.setProperty("ShoutColor", ("#" + shoutColor.getValue().toString().substring(2, 8)));
            properties.setProperty("PartyColor", ("#" + partyColor.getValue().toString().substring(2, 8)));
            properties.setProperty("FilterColor", ("#" + filterColor.getValue().toString().substring(2, 8)));
            properties.setProperty("SelfColor", ("#" + selfColor.getValue().toString().substring(2, 8)));
            
            properties.setProperty("Test", "TEST");
            
            properties.store(outputStream, null);
            
            Stage stage = (Stage) talkColor.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            throw new LoggerException("Error saving properties.", ex);
        }
    }

    /**
     * Loads properties from property file and initializes values.
     */
    @FXML
    private void loadProperties() throws LoggerException {
        try {
            InputStream inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);

            if (properties.getProperty("TalkColor") != null && !properties.getProperty("TalkColor").isEmpty()) {
                talkColor.setValue(Color.web(properties.getProperty("TalkColor")));
            } else {
                talkColor.setValue(Color.web(properties.getProperty("DefaultTalkColor")));
            }

            if (properties.getProperty("TellColor") != null && !properties.getProperty("TellColor").isEmpty()) {
                tellColor.setValue(Color.web(properties.getProperty("TellColor")));
            } else {
                tellColor.setValue(Color.web(properties.getProperty("DefaultTellColor")));
            }

            if (properties.getProperty("WhisperColor") != null && !properties.getProperty("WhisperColor").isEmpty()) {
                whisperColor.setValue(Color.web(properties.getProperty("WhisperColor")));
            } else {
                whisperColor.setValue(Color.web(properties.getProperty("DefaultWhisperColor")));
            }

            if (properties.getProperty("ShoutColor") != null && !properties.getProperty("ShoutColor").isEmpty()) {
                shoutColor.setValue(Color.web(properties.getProperty("ShoutColor")));
            } else {
                shoutColor.setValue(Color.web(properties.getProperty("DefaultShoutColor")));
            }
            
            if (properties.getProperty("PartyColor") != null && !properties.getProperty("PartyColor").isEmpty()) {
                partyColor.setValue(Color.web(properties.getProperty("PartyColor")));
            } else {
                partyColor.setValue(Color.web(properties.getProperty("DefaultPartyColor")));
            }
            
            if (properties.getProperty("FilterColor") != null && !properties.getProperty("FilterColor").isEmpty()) {
                filterColor.setValue(Color.web(properties.getProperty("FilterColor")));
            } else {
                filterColor.setValue(Color.web(properties.getProperty("DefaultFilterColor")));
            }
            
            if (properties.getProperty("SelfColor") != null && !properties.getProperty("SelfColor").isEmpty()) {
                selfColor.setValue(Color.web(properties.getProperty("SelfColor")));
            } else {
                selfColor.setValue(Color.web(properties.getProperty("DefaultSelfColor")));
            }

        } catch (Exception ex) {
            throw new LoggerException("Error loading properties.", ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        properties = new Properties();
        
        Tooltip talkTip = new Tooltip();
        talkColor.setTooltip(talkTip);
        talkTip.setText("Set the color Talk messages will appear in.");

        Tooltip tellTip = new Tooltip();
        tellColor.setTooltip(tellTip);
        tellTip.setText("Sets the color Tell messages will appear in.");

        Tooltip whisperTip = new Tooltip();
        whisperColor.setTooltip(whisperTip);
        whisperTip.setText("Sets the color Whisper messages will appear in.");

        Tooltip shoutTip = new Tooltip();
        shoutColor.setTooltip(shoutTip);
        shoutTip.setText("Sets the color Shout messages will appear in.");

        Tooltip partyTip = new Tooltip();
        partyColor.setTooltip(partyTip);
        partyTip.setText("Sets the color Party messages will appear in.");

        Tooltip filterTip = new Tooltip();
        filterColor.setTooltip(filterTip);
        filterTip.setText("Sets the color filtered usernames will appear in.");

        Tooltip selfTip = new Tooltip();
        selfColor.setTooltip(selfTip);
        selfTip.setText("Sets the color your username will appear in.");
        
        
        try {
            loadProperties();
        } catch (LoggerException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
