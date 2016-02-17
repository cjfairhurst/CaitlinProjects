package com.nwn.nwntools;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

/**
 * A program meant to cycle NWN logs. This program can be used to parse NWN logs
 * into formatted HTML pages. It also renames the original log file, allowing it
 * to be saved. Various options are available to make the parsed log easier to
 * read.
 *
 * version 1.0
 *
 * @author Caitlin
 */
public class LoggerController implements Initializable {

    @FXML
    private AnchorPane main;
    @FXML
    private Label logDirectoryLabel;
    @FXML
    private Label saveDirectoryLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label confirmationLabel;
    @FXML
    private TextField titleField;
    @FXML
    private TextField filterField;
    @FXML
    private Label filterLabel;
    @FXML
    private Button optionsButton;
    @FXML
    private Button openLogDirectoryButton;
    @FXML
    private Button openSaveDirectoryButton;
    @FXML
    private Button clearFiltersButton;
    @FXML
    private Button clearTitleButton;
    @FXML
    private Button clearSelfButton;
    @FXML
    private TextField selfField;
    private List<String> filters;
    private Properties properties;

    /**
     * Choose the directory where original logs are located. Changes properties
     * file.
     *
     * @throws ToolException
     */
    @FXML
    private void chooseLogDirectory() throws ToolException {
        loadProperties();
        clearNotifications();
        DirectoryChooser dirChooser = new DirectoryChooser();
        File selectedDirectory = dirChooser.showDialog(main.getScene().getWindow());

        if (selectedDirectory == null && hasDefault(logDirectoryLabel)) {
            errorLabel.setText("No directory selected. Resorting to default.");
        } else if (selectedDirectory == null) {
            errorLabel.setText("No directory selected.");
        } else {
            logDirectoryLabel.setText(selectedDirectory.getAbsolutePath());
            saveProperties();
        }
    }

    /**
     * Choose the directory where renamed logs are saved. Changes properties
     * file.
     *
     * @throws ToolException
     */
    @FXML
    private void chooseSaveDirectory() throws ToolException {
        loadProperties();
        clearNotifications();
        DirectoryChooser dirChooser = new DirectoryChooser();
        File selectedDirectory = dirChooser.showDialog(main.getScene().getWindow());

        if (selectedDirectory == null && hasDefault(saveDirectoryLabel)) {
            errorLabel.setText("No directory selected. Resorting to default.");
        } else if (selectedDirectory == null) {
            errorLabel.setText("No directory selected.");
        } else {
            saveDirectoryLabel.setText(selectedDirectory.getAbsolutePath());
            saveProperties();
        }
    }

    /**
     * Adds a filter. Changes properties file.
     */
    @FXML
    private void addFilter() throws ToolException {
        loadProperties();
        if (filterField.getText() != null && !filterField.getText().isEmpty()) {
            filters.add(filterField.getText());

            filterLabel.setText(filterString());
            filterField.clear();

            saveProperties();
        }
    }

    /**
     * Sets the self filter. Changes properties file.
     *
     * @throws ToolException
     */
    @FXML
    private void setSelfFilter() throws ToolException {
        loadProperties();
        if (selfField.getText() != null && !selfField.getText().isEmpty()) {
            selfField.setPromptText(selfField.getText());
            selfField.clear();
            main.requestFocus();
            saveProperties();
        }
    }

    /**
     * Clears the self filter. Changes properties file.
     */
    @FXML
    private void clearSelfFilter() throws ToolException {
        loadProperties();
        selfField.clear();
        selfField.setPromptText("");
        saveProperties();
    }

    /**
     * Checks if a directory has been set.
     *
     * @param label
     * @return
     */
    private boolean hasDefault(Label label) {
        return !(label.getText() == null || label.getText().isEmpty());
    }

    /**
     * Clears messages.
     */
    private void clearNotifications() {
        errorLabel.setText("");
        confirmationLabel.setText("");
    }

    /**
     * Clear options.
     */
    private void clearOptions() {
        titleField.clear();
        filterField.clear();
    }

    /**
     * Cycle through and rename logs.
     */
    @FXML
    private void cycleLogs() throws ToolException {
        loadProperties();
        clearNotifications();
        if (hasDefault(logDirectoryLabel) && hasDefault(saveDirectoryLabel)) {//Checks if directories are set.
            boolean fileRenamed = false;
            String directoryName = logDirectoryLabel.getText();
            String newDirectoryName = saveDirectoryLabel.getText();

            File fileDirectory = new File(directoryName);
            File[] directoryListing = fileDirectory.listFiles();
            if (directoryListing != null) {//Checks if directory is empty.
                for (File originalFile : directoryListing) {
                    if (originalFile.getName().equals("nwclientLog1.txt")) {//Check file name for match.
                        if (!fileIsEmpty(originalFile)) {//Check if file is empty.
                            String newName;
                            if (titleField != null && !titleField.getText().isEmpty()) {//Attach title to filename.
                                if (titleField.getLength() <= 120) {//Limits title length.
                                    newName = newDirectoryName + "\\" + calculateDateTime() + "_" + filterTitle(titleField.getText());
                                } else {
                                    newName = newDirectoryName + "\\" + calculateDateTime() + "_" + filterTitle(titleField.getText().substring(0, 119));
                                }
                            } else {
                                newName = newDirectoryName + "\\" + calculateDateTime();
                            }
                            File newHTMLFile = new File(newName + ".html");
                            File newFile = new File(newName + ".txt");
                            parseToHTML(originalFile, newHTMLFile);//Parses file into html format and saves it.
                            confirmationLabel.setText(newHTMLFile.getName() + " was saved.");
                            copyFile(originalFile, newFile);
                            confirmationLabel.setText(newHTMLFile.getName() + " was saved.\n"
                                    + newFile.getName() + " was saved.");
                            clearOptions();
                            fileRenamed = true;
                        }
                    }
                }
            }

            if (fileRenamed != true) {
                if (!errorLabel.getText().equals("Error renaming file.")) {
                    errorLabel.setText("No files found.");
                }
            }

        } else {
            errorLabel.setText("Empty directory.");
        }
    }

    /**
     * Filters symbols out of the title to prevent naming errors.
     *
     * @param title
     * @return
     */
    private String filterTitle(String title) {
        return title.replace(".", "")
                .replace(" ", "_")
                .replace("#", "")
                .replace("%", "")
                .replace("&", "_and_")
                .replace("{", "")
                .replace("}", "")
                .replace("\\", "")
                .replace("<", "")
                .replace(">", "")
                .replace("*", "")
                .replace("?", "")
                .replace("/", "")
                .replace("$", "")
                .replace("!", "")
                .replace("'", "")
                .replace("\"", "")
                .replace(":", "")
                .replace("@", "")
                .replace("+", "")
                .replace("`", "")
                .replace("|", "")
                .replace("=", "");
    }

    /**
     * Calculate the current date and time for use in renaming logs.
     *
     * @return
     */
    private String calculateDateTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        return simpleDateFormat.format(date);
    }

    /**
     * Checks if file is empty.
     *
     * @param originalFile
     * @return
     * @throws LoggerException
     */
    private boolean fileIsEmpty(File originalFile) throws ToolException {
        try {
            FileInputStream inputStream = new FileInputStream(originalFile);
            String fileContents = IOUtils.toString(inputStream);
            return fileContents.isEmpty();
        } catch (Exception ex) {
            throw new ToolException("Error reading file.", ex);
        }

    }

    private void copyFile(File originalFile, File newFile) throws ToolException {
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

        } catch (Exception ex) {
            throw new ToolException("Error copying file.", ex);
        }
    }

    /**
     * Copy the original file to a new file.
     *
     * @param originalFile
     * @param newFile
     * @throws LoggerException
     */
    private void parseToHTML(File originalFile, File newFile) throws ToolException {
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {
            String line;
            writer.write("<html><body>");
            writer.newLine();
            writer.write("<head>\n"
                    + "<style>\n"
                    + "body {margin: 10px 20px}\n"
                    + "</style>\n"
                    + "</head>");
            writer.newLine();
            writer.write("<body>");
            writer.newLine();
            writer.write("<h1>" + newFile.getName().replace("_", " ").replace(".html", "") + "</h1>");
            writer.newLine();

            if (!filters.isEmpty()) {
                writer.write("<h3>Filters: ");
                for (String filter : filters) {
                    writer.write(filterString());
                }
                writer.write("</h3>");
                writer.newLine();
            }
            while ((line = reader.readLine()) != null) {
                writer.write(formatLog(line));
                writer.newLine();
            }
            writer.write("</body></html>");
        } catch (Exception ex) {
            throw new ToolException("Error parsing file to HTML.", ex);
        }
    }

    /**
     * Formats the log to HTML.
     *
     * @param line
     * @return
     */
    private String formatLog(String line) {
        String returnString = "";
        if (line.contains(":")) {
            if (line.contains("[Receives") || line.contains("[Takes") || line.contains("[Removes") || line.contains("[Hands")) {//Reduce size of spam.
                returnString += markFilters("<p style='font-size:75%'><b><u>" + line.replaceFirst(":", ":</u></b>") + "</p>");
            } else if (line.contains("[Talk]")) {//Mark normal messages.
                returnString += markFilters("<p><b><u>" + line.replaceFirst(":", ":</u></b><font color='" + retrieveColorProperty("TalkColor") + "'>") + "</font></p>");
            } else if (line.contains("[Tell]")) {//Mark tell messages.
                returnString += markFilters("<p><b><u>" + line.replaceFirst(":", ":</u></b><font color='" + retrieveColorProperty("TellColor") + "'>") + "</font></p>");
            } else if (line.contains("[Whisper]")) {//Mark whisper messages.
                returnString += markFilters("<p><b><u>" + line.replaceFirst(":", ":</u></b><font color='" + retrieveColorProperty("WhisperColor") + "'>") + "</font></p>");
            } else if (line.contains("[Shout]")) {//Mark shout messages.
                returnString += markFilters("<p><b><u>" + line.replaceFirst(":", ":</u></b><font color='" + retrieveColorProperty("ShoutColor") + "'>") + "</font></p>");
            } else if (line.contains("[Party]")) {//Mark shout messages.
                returnString += markFilters("<p><b><u>" + line.replaceFirst(":", ":</u></b><font color='" + retrieveColorProperty("PartyColor") + "'>") + "</font></p>");
            } else {//Catch other messages.
                returnString += "<p><b><u>" + line.replaceFirst(":", ":</u></b>") + "</br>";
            }
        } else {
            returnString += "<p><i>" + line + "</i></p>";
        }
        return returnString;
    }

    /**
     * Mark filters.
     *
     * @param returnString
     * @return
     */
    private String markFilters(String returnString) {
        if (selfField.getPromptText() != null && !selfField.getPromptText().isEmpty()) {
            if (returnString.contains("[" + selfField.getPromptText() + "]")) {
                return returnString.replace(("[" + selfField.getPromptText() + "]"), ("<font color='" + retrieveColorProperty("SelfColor") + "'>[" + selfField.getPromptText() + "]")).replace(":</u>", ":</font></u>");
            }
        }
        for (String filter : filters) {//Mark filters.
            if (returnString.contains("[" + filter + "]")) {
                return returnString.replace(("[" + filter + "]"), ("<font color='" + retrieveColorProperty("FilterColor") + "'>[" + filter + "]")).replace(":</u>", ":</font></u>");
            }
        }
        return returnString;
    }

    /**
     * Retrieves color from properties.
     *
     * @param colorType
     * @return
     */
    private String retrieveColorProperty(String colorType) {
        switch (colorType) {
            case "TalkColor":
                if (properties.getProperty("TalkColor") != null && !properties.getProperty("TalkColor").isEmpty()) {
                    return properties.getProperty("TalkColor");
                } else {
                    return properties.getProperty("DefaultTalkColor");
                }
            case "TellColor":
                if (properties.getProperty("TellColor") != null && !properties.getProperty("TellColor").isEmpty()) {
                    return properties.getProperty("TellColor");
                } else {
                    return properties.getProperty("DefaultTellColor");
                }
            case "WhisperColor":
                if (properties.getProperty("WhisperColor") != null && !properties.getProperty("WhisperColor").isEmpty()) {
                    return properties.getProperty("WhisperColor");
                } else {
                    return properties.getProperty("DefaultWhisperColor");
                }
            case "ShoutColor":
                if (properties.getProperty("ShoutColor") != null && !properties.getProperty("ShoutColor").isEmpty()) {
                    return properties.getProperty("ShoutColor");
                } else {
                    return properties.getProperty("DefaultShoutColor");
                }
            case "PartyColor":
                if (properties.getProperty("PartyColor") != null && !properties.getProperty("PartyColor").isEmpty()) {
                    return properties.getProperty("PartyColor");
                } else {
                    return properties.getProperty("DefaultPartyColor");
                }
            case "FilterColor":
                if (properties.getProperty("FilterColor") != null && !properties.getProperty("FilterColor").isEmpty()) {
                    return properties.getProperty("FilterColor");
                } else {
                    return properties.getProperty("DefaultFilterColor");
                }
            case "SelfColor":
                if (properties.getProperty("SelfColor") != null && !properties.getProperty("SelfColor").isEmpty()) {
                    return properties.getProperty("SelfColor");
                } else {
                    return properties.getProperty("DefaultSelfColor");
                }
            default:
                return "#000000";
        }
    }

    /**
     * Opens the options window.
     *
     * @param event
     * @throws com.nwn.nwntools.ToolException
     */
    @FXML
    public void openOptions(ActionEvent event) throws ToolException {
        loadProperties();
        saveProperties();
        clearNotifications();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/LoggerOptions.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Options");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (Exception ex) {
            throw new ToolException("Error opening options.", ex);
        }
    }

    /**
     * Opens the log directory.
     *
     * @throws ToolException
     */
    @FXML
    private void openLogDirectory() throws ToolException {
        clearNotifications();
        if (Desktop.isDesktopSupported()) {
            if (logDirectoryLabel.getText() != null && !logDirectoryLabel.getText().isEmpty()) {
                try {
                    Desktop.getDesktop().open(new File(logDirectoryLabel.getText()));
                } catch (Exception ex) {
                    throw new ToolException("Could not open directory.", ex);
                }
            }
        }
    }

    /**
     * Opens the save directory.
     *
     * @throws ToolException
     */
    @FXML
    private void openSaveDirectory() throws ToolException {
        clearNotifications();
        if (Desktop.isDesktopSupported()) {
            if (saveDirectoryLabel.getText() != null && !saveDirectoryLabel.getText().isEmpty()) {
                try {
                    Desktop.getDesktop().open(new File(saveDirectoryLabel.getText()));
                } catch (Exception ex) {
                    throw new ToolException("Could not open directory.", ex);
                }
            }
        }
    }

    /**
     * Clears all filters. Changes properties file.
     *
     * @throws ToolException
     */
    @FXML
    private void clearFilters() throws ToolException {
        loadProperties();
        filters.clear();
        filterLabel.setText("");
        filterField.clear();
        saveProperties();
    }

    @FXML
    private void clearTitle() {
        titleField.clear();
    }

    /**
     * Loads properties from properties file and initializes values.
     */
    @FXML
    private void loadProperties() throws ToolException {
        filters.clear();
        try (InputStream inputStream = new FileInputStream("config.properties");) {
            properties.load(inputStream);

            logDirectoryLabel.setText(properties.getProperty("LogDirectory"));
            saveDirectoryLabel.setText(properties.getProperty("SaveDirectory"));
            selfField.setPromptText(properties.getProperty("Self"));
            if (properties.getProperty("Filters") != null && !properties.getProperty("Filters").isEmpty()) {
                String[] split = properties.getProperty("Filters").split(",");
                filters.addAll(Arrays.asList(split));
                filterLabel.setText(filterString());
            }
        } catch (FileNotFoundException ex) {
            saveProperties();
        } catch (Exception ex) {
            saveProperties();
            throw new ToolException("Error loading properties. Reverting to default.", ex);
        }

    }

    /**
     * Translates filter array to comma-separated string.
     *
     * @return
     */
    private String filterString() {
        String filterString = "";
        if (!filters.isEmpty()) {
            for (String filter : filters) {
                filterString += (filter + ",");
            }
            filterString = filterString.substring(0, filterString.length() - 1);//Remove final comma.
        }
        return filterString;
    }

    /**
     * Saves properties to properties file. Changes properties file.
     */
    @FXML
    private void saveProperties() throws ToolException {
        try {
            OutputStream outputStream = new FileOutputStream("config.properties");
            properties.setProperty("LogDirectory", checkNull(logDirectoryLabel.getText()));
            properties.setProperty("SaveDirectory", checkNull(saveDirectoryLabel.getText()));
            properties.setProperty("Filters", filterString());
            properties.setProperty("DefaultTalkColor", "#1a1a1a");
            properties.setProperty("DefaultTellColor", "#008000");
            properties.setProperty("DefaultWhisperColor", "#007fff");
            properties.setProperty("DefaultShoutColor", "#cc9900");
            properties.setProperty("DefaultPartyColor", "#80007f");
            properties.setProperty("DefaultFilterColor", "#b30000");
            properties.setProperty("DefaultSelfColor", "#ff4d4d");
            if (selfField.getPromptText() != null) {
                properties.setProperty("Self", selfField.getPromptText());
            }

            if (properties.getProperty("TalkColor") != null && !properties.getProperty("TalkColor").isEmpty()) {
                properties.setProperty("TalkColor", properties.getProperty("TalkColor"));
            } else {
                properties.setProperty("TalkColor", properties.getProperty("DefaultTalkColor"));
            }

            if (properties.getProperty("TellColor") != null && !properties.getProperty("TellColor").isEmpty()) {
                properties.setProperty("TellColor", properties.getProperty("TellColor"));
            } else {
                properties.setProperty("TellColor", properties.getProperty("DefaultTellColor"));
            }

            if (properties.getProperty("WhisperColor") != null && !properties.getProperty("WhisperColor").isEmpty()) {
                properties.setProperty("WhisperColor", properties.getProperty("WhisperColor"));
            } else {
                properties.setProperty("WhisperColor", properties.getProperty("DefaultWhisperColor"));
            }

            if (properties.getProperty("ShoutColor") != null && !properties.getProperty("ShoutColor").isEmpty()) {
                properties.setProperty("ShoutColor", properties.getProperty("ShoutColor"));
            } else {
                properties.setProperty("ShoutColor", properties.getProperty("DefaultShoutColor"));
            }

            if (properties.getProperty("PartyColor") != null && !properties.getProperty("PartyColor").isEmpty()) {
                properties.setProperty("PartyColor", properties.getProperty("PartyColor"));
            } else {
                properties.setProperty("PartyColor", properties.getProperty("DefaultPartyColor"));
            }

            if (properties.getProperty("SelfColor") != null && !properties.getProperty("SelfColor").isEmpty()) {
                properties.setProperty("SelfColor", properties.getProperty("SelfColor"));
            } else {
                properties.setProperty("SelfColor", properties.getProperty("DefaultSelfColor"));
            }

            if (properties.getProperty("FilterColor") != null && !properties.getProperty("FilterColor").isEmpty()) {
                properties.setProperty("FilterColor", properties.getProperty("FilterColor"));
            } else {
                properties.setProperty("FilterColor", properties.getProperty("DefaultFilterColor"));
            }

            properties.store(outputStream, null);
        } catch (Exception ex) {
            throw new ToolException ("An error occured while saving properties.", ex);
        }
    }

    /**
     * Checks and handles null values.
     *
     * @param string
     * @return
     */
    private String checkNull(String string) {
        if (string == null) {
            return "";
        } else {
            return string;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filters = new ArrayList<>();
        properties = new Properties();

        Tooltip logDirectoryTip = new Tooltip();
        logDirectoryLabel.setTooltip(logDirectoryTip);
        logDirectoryTip.setText("The original directory where logs are saved.");

        Tooltip saveDirectoryTip = new Tooltip();
        saveDirectoryLabel.setTooltip(saveDirectoryTip);
        saveDirectoryTip.setText("The directory you would like to save your altered logs to.");

        Tooltip titleTip = new Tooltip();
        titleField.setTooltip(titleTip);
        titleTip.setText("OPTIONAL. A title to append to the new file's name.");

        Tooltip filterTip = new Tooltip();
        filterField.setTooltip(filterTip);
        filterTip.setText("OPTIONAL. A filter to make certain entries in the log more noticeable."
                + " Please filter on LOGIN NAMES only.");

        Tooltip optionsTip = new Tooltip();
        optionsButton.setTooltip(optionsTip);
        optionsTip.setText("Opens the options menu.");

        Tooltip openDirectoryTip = new Tooltip();
        openLogDirectoryButton.setTooltip(openDirectoryTip);
        openSaveDirectoryButton.setTooltip(openDirectoryTip);
        openDirectoryTip.setText("Open directory.");

        Tooltip clearFiltersTip = new Tooltip();
        clearFiltersButton.setTooltip(clearFiltersTip);
        clearFiltersTip.setText("Clears all filters.");

        Tooltip clearTitleTip = new Tooltip();
        clearTitleButton.setTooltip(clearTitleTip);
        clearTitleTip.setText("Clears the title.");

        Tooltip selfTip = new Tooltip();
        selfField.setTooltip(selfTip);
        selfTip.setText("Sets your username as a filter.");

        Tooltip clearSelfTip = new Tooltip();
        clearSelfButton.setTooltip(clearSelfTip);
        clearSelfTip.setText("Clears your self filter.");

        Tooltip filterLabelTip = new Tooltip();
        filterLabel.setTooltip(filterLabelTip);
        filterLabelTip.setText("The currently set filters.");

        try {
            loadProperties();
        } catch (ToolException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
