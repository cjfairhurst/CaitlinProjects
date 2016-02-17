package com.nwn.nwntools;

import com.nwn.nwntools.NoteKeeper.Note;
import com.nwn.nwntools.NoteKeeper.Notes;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class NoteController implements Initializable {

    private Notes notes;
    private Note currentNote;
    private int currentNoteIndex;
    private int newNotes;
    private Properties properties;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField searchField;
    @FXML
    private VBox labelVBox;
    @FXML
    private VBox fieldVBox;
    @FXML
    private ListView noteList;
    @FXML
    private Label nameLabel;
    @FXML
    private Label subjectLabel;
    @FXML
    private ScrollPane descriptionScroll;
    @FXML
    private Label tagsLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField subjectField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField tagsField;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;

    @FXML
    private void displayNote(String sourceText) {
        toggleFields(false);
        for (Note note : notes.getNotes()) {
            if (note.getName().equals(sourceText)) {
                nameLabel.setText(note.getName());
                subjectLabel.setText(note.getSubject());
                tagsLabel.setText(note.getTags());

                Text text = new Text(note.getDescription());
                text.setId("text");
                text.wrappingWidthProperty().bind(descriptionScroll.widthProperty());
                descriptionScroll.setContent(text);

                currentNote = note;
            }
        }
    }

    @FXML
    private void cancelEdit() {
        toggleFields(false);
        refreshCurrentNote();
    }

    @FXML
    private void deleteNote() {
        notes.getNotes().remove(currentNote);
        if (notes.getNotes().isEmpty()) {
            newNotes = 1;
            currentNote = new Note();
            currentNote.setName("New Note " + newNotes);
            notes.getNotes().add(currentNote);
            currentNoteIndex = 1;
        }
        fillNoteList();
        noteList.getSelectionModel().select(currentNoteIndex - 1);
    }

    @FXML
    private void refreshCurrentNote() {
        nameLabel.setText(currentNote.getName());
        subjectLabel.setText(currentNote.getSubject());
        Text text = new Text(currentNote.getDescription());
        text.setId("text");
        text.wrappingWidthProperty().bind(descriptionScroll.widthProperty());
        descriptionScroll.setContent(text);
        tagsLabel.setText(currentNote.getTags());
    }

    @FXML
    private void newNote() {
        newNotes++;
        currentNote = new Note();
        currentNote.setName("New Note " + newNotes);
        notes.getNotes().add(currentNote);
        fillNoteList();
        noteList.getSelectionModel().select(noteList.getItems().size() - 1);

        toggleFields(true);

        nameField.setText(currentNote.getName());
        subjectField.setText("");
        descriptionField.setText("");
        tagsField.setText("");
    }

    @FXML
    private void editNote() {
        toggleFields(true);
    }

    @FXML
    private void saveNote() {
        if (nameField.getText() != null && !nameField.getText().isEmpty()) {
            nameLabel.setText(nameField.getText());
        } else {
            newNotes++;
            nameLabel.setText("New Note " + newNotes);
        }
        subjectLabel.setText(subjectField.getText());
        Text text = new Text(descriptionField.getText());
        text.setId("text");
        text.wrappingWidthProperty().bind(descriptionScroll.widthProperty());
        descriptionScroll.setContent(text);
        tagsLabel.setText(tagsField.getText());
        toggleFields(false);

        currentNote.setName(nameLabel.getText());
        currentNote.setSubject(subjectLabel.getText());
        Text descriptionText = (Text) descriptionScroll.getContent();
        currentNote.setDescription(descriptionText.getText());
        currentNote.setTags(tagsLabel.getText());

        fillNoteList();
        noteList.getSelectionModel().select(currentNoteIndex);
        displayNote((String) noteList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void searchAll() {
        if (searchField.getText() != null && !searchField.getText().isEmpty()) {
            List<String> noteNameList = new ArrayList<>();
            for (Note note : notes.getNotes()) {
                if (note.getName().contains(searchField.getText()) || note.getTags().contains(searchField.getText())) {
                    noteNameList.add(note.getName());
                }
            }
            if (!noteNameList.isEmpty()) {
                ObservableList<String> items = FXCollections.observableArrayList(noteNameList);
                noteList.setItems(items);
                noteList.getSelectionModel().select(0);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Notes Found");
                alert.setHeaderText(null);
                alert.setContentText("No notes were found based on this criteria.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
                dialogPane.setId("dialog");

                alert.showAndWait();
            }
            searchField.setText("");
        }
    }

    @FXML
    private void searchName() {
        if (searchField.getText() != null && !searchField.getText().isEmpty()) {
            List<String> noteNameList = new ArrayList<>();
            for (Note note : notes.getNotes()) {
                if (note.getName().contains(searchField.getText())) {
                    noteNameList.add(note.getName());
                }
            }
            if (!noteNameList.isEmpty()) {
                ObservableList<String> items = FXCollections.observableArrayList(noteNameList);
                noteList.setItems(items);
                noteList.getSelectionModel().select(0);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Notes Found");
                alert.setHeaderText(null);
                alert.setContentText("No notes were found based on this criteria.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
                dialogPane.setId("dialog");

                alert.showAndWait();
            }
            searchField.setText("");
        }
    }

    @FXML
    private void searchTags() {
        if (searchField.getText() != null && !searchField.getText().isEmpty()) {
            List<String> noteNameList = new ArrayList<>();
            for (Note note : notes.getNotes()) {
                if (note.getTags().contains(searchField.getText())) {
                    noteNameList.add(note.getName());
                }
            }
            if (!noteNameList.isEmpty()) {
                ObservableList<String> items = FXCollections.observableArrayList(noteNameList);
                noteList.setItems(items);
                noteList.getSelectionModel().select(0);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Notes Found");
                alert.setHeaderText(null);
                alert.setContentText("No notes were found based on this criteria.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
                dialogPane.setId("dialog");

                alert.showAndWait();
            }
            searchField.setText("");
        }
    }

    @FXML
    private void fillNoteList() {
        List<String> noteNameList = new ArrayList<>();
        for (Note note : notes.getNotes()) {
            noteNameList.add(note.getName());
        }
        ObservableList<String> items = FXCollections.observableArrayList(noteNameList);
        noteList.setItems(items);
    }

    private void toggleFields(boolean editMode) {

        if (editMode) {
            nameField.setText(nameLabel.getText());
            subjectField.setText(subjectLabel.getText());
            Text descriptionText = (Text) descriptionScroll.getContent();
            descriptionField.setText(descriptionText.getText());
            tagsField.setText(tagsLabel.getText());
        }

        saveButton.setVisible(editMode);
        cancelButton.setVisible(editMode);
        editButton.setVisible(!editMode);
        deleteButton.setVisible(!editMode);
        labelVBox.setVisible(!editMode);
        fieldVBox.setVisible(editMode);
    }

    private void loadProperties() throws ToolException {
        try (InputStream inputStream = new FileInputStream("config.properties");) {
            properties.load(inputStream);

            String notesXml = properties.getProperty("notes");

            if (notesXml == null || notesXml.isEmpty()) {
                newNotes++;
                notes = new Notes();
                currentNote = new Note();
                currentNote.setName("New Note " + newNotes);
                notes.getNotes().add(currentNote);
            } else {
                try (StringReader reader = new StringReader(notesXml)) {
                    JAXBContext jc = JAXBContext.newInstance(Notes.class);
                    Unmarshaller unmarshaller = jc.createUnmarshaller();
                    notes = (Notes) unmarshaller.unmarshal(reader);
                } catch (JAXBException ex) {
                    throw new ToolException("Error parsing properties.", ex);
                }
            }

        } catch (FileNotFoundException ex) {
            newNotes++;
            notes = new Notes();
            currentNote = new Note();
            currentNote.setName("New Note " + newNotes);
            notes.getNotes().add(currentNote);
        } catch (Exception ex) {
            notes = new Notes();
            throw new ToolException("Error loading properties.", ex);
        }
    }

    @FXML
    private void saveProperties() throws ToolException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Notes.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(notes, writer);
            properties.setProperty("notes", writer.toString());

            try (OutputStream outputStream = new FileOutputStream("config.properties")) {
                properties.store(outputStream, null);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project Saved");
            alert.setHeaderText(null);
            alert.setContentText("The project was saved.");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
            dialogPane.setId("dialog");

            alert.showAndWait();
        } catch (Exception ex) {
            throw new ToolException("Error saving properties.", ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            noteList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    displayNote(newValue);
                    if (noteList.getSelectionModel().getSelectedIndex() != -1) {
                        currentNoteIndex = noteList.getSelectionModel().getSelectedIndex();
                    }
                }
            });
            newNotes = 0;
            properties = new Properties();

            loadProperties();
            fillNoteList();
            noteList.getSelectionModel().select(0);
            toggleFields(false);
        } catch (ToolException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
