package com.nwn.nwntools;

import com.nwn.nwntools.DiceRoller.Characters;
import com.nwn.nwntools.DiceRoller.Character;
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
import javafx.scene.control.ChoiceBox;
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

public class CharacterController implements Initializable {

    private Characters characters;
    private com.nwn.nwntools.DiceRoller.Character currentCharacter;
    private int currentCharacterIndex;
    private int newCharacters;
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
    private ListView characterList;
    @FXML
    private Label nameLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private ScrollPane descriptionScroll;
    @FXML
    private Label tagsLabel;
    @FXML
    private Label forumLabel;
    @FXML
    private Label classesLabel;
    @FXML
    private Label alignmentLabel;
    @FXML
    private Label raceLabel;
    @FXML
    private Label originLabel;
    @FXML
    private Label religionLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField loginField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField tagsField;
    @FXML
    private TextField forumField;
    @FXML
    private TextField classesField;
    @FXML
    private TextField alignmentField;
    @FXML
    private TextField raceField;
    @FXML
    private TextField originField;
    @FXML
    private TextField religionField;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ChoiceBox mainChoice;
    @FXML
    private VBox abilityList;
    @FXML
    private VBox saveList;
    @FXML
    private VBox skillList;
    private List<ChoiceBox> abilityChoices;
    private List<ChoiceBox> saveChoices;
    private List<ChoiceBox> skillChoices;

    @FXML
    private ChoiceBox strengthChoice;
    @FXML
    private ChoiceBox dexterityChoice;
    @FXML
    private ChoiceBox constitutionChoice;
    @FXML
    private ChoiceBox wisdomChoice;
    @FXML
    private ChoiceBox intelligenceChoice;
    @FXML
    private ChoiceBox charismaChoice;

    @FXML
    private ChoiceBox fortitudeChoice;
    @FXML
    private ChoiceBox reflexChoice;
    @FXML
    private ChoiceBox willChoice;
    
    @FXML
    private ChoiceBox hideChoice;
    @FXML
    private ChoiceBox moveSilentlyChoice;
    @FXML
    private ChoiceBox spotChoice;
    @FXML
    private ChoiceBox listenChoice;
    @FXML
    private ChoiceBox searchChoice;
    @FXML
    private ChoiceBox performChoice;
    @FXML
    private ChoiceBox concentrationChoice;
    @FXML
    private ChoiceBox parryChoice;
    @FXML
    private ChoiceBox loreChoice;
    @FXML
    private ChoiceBox spellcraftChoice;
    @FXML
    private ChoiceBox bluffChoice;
    @FXML
    private ChoiceBox persuadeChoice;
    @FXML
    private ChoiceBox slightOfHandChoice;
    @FXML
    private ChoiceBox disciplineChoice;
    @FXML
    private ChoiceBox antagonizeChoice;
    @FXML
    private ChoiceBox pickLockChoice;
    
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private Label label8;
    @FXML
    private Label label9;
    @FXML
    private Label label10;
    @FXML
    private Label label11;
    @FXML
    private Label label12;
    @FXML
    private Label label13;
    @FXML
    private Label label14;
    @FXML
    private Label label15;
    @FXML
    private Label label16;
    

    @FXML
    private void displayCharacter(String sourceText) {
        toggleFields(false);
        for (Character character : characters.getCharacters()) {
            if (character.getName().equals(sourceText)) {
                nameLabel.setText(character.getName());
                loginLabel.setText(character.getLogin());
                forumLabel.setText(character.getForum());
                tagsLabel.setText(character.getTags());

                Text text = new Text(character.getDescription());
                text.setId("text");
                text.wrappingWidthProperty().bind(descriptionScroll.widthProperty());
                descriptionScroll.setContent(text);

                classesLabel.setText(character.getClasses());
                alignmentLabel.setText(character.getAlignment());
                raceLabel.setText(character.getRace());
                originLabel.setText(character.getOrigin());
                religionLabel.setText(character.getReligion());

                currentCharacter = character;

                fillChoiceList((String) mainChoice.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    private void cancelEdit() {
        toggleFields(false);
        refreshCurrentCharacter();
    }

    @FXML
    private void deleteCharacter() {
        characters.getCharacters().remove(currentCharacter);
        if (characters.getCharacters().isEmpty()) {
            newCharacters = 1;
            currentCharacter = new Character();
            currentCharacter.setName("New Character " + newCharacters);
            characters.getCharacters().add(currentCharacter);
            currentCharacterIndex = 1;
        }
        fillCharacterList();
        characterList.getSelectionModel().select(currentCharacterIndex - 1);
    }

    @FXML
    private void refreshCurrentCharacter() {
        nameLabel.setText(currentCharacter.getName());
        loginLabel.setText(currentCharacter.getLogin());
        forumLabel.setText(currentCharacter.getForum());

        Text text = new Text(currentCharacter.getDescription());
        text.setId("text");
        text.wrappingWidthProperty().bind(descriptionScroll.widthProperty());
        descriptionScroll.setContent(text);

        tagsLabel.setText(currentCharacter.getTags());
        classesLabel.setText(currentCharacter.getClasses());
        alignmentLabel.setText(currentCharacter.getAlignment());
        raceLabel.setText(currentCharacter.getRace());
        originLabel.setText(currentCharacter.getOrigin());
        religionLabel.setText(currentCharacter.getReligion());

        fillChoiceList((String) mainChoice.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void newCharacter() {
        newCharacters++;
        currentCharacter = new Character();
        currentCharacter.setName("New Character " + newCharacters);
        characters.getCharacters().add(currentCharacter);
        fillCharacterList();
        characterList.getSelectionModel().select(characterList.getItems().size() - 1);

        toggleFields(true);

        nameField.setText(currentCharacter.getName());
        loginField.setText("");
        descriptionField.setText("");
        tagsField.setText("");

        classesField.setText("");
        alignmentField.setText("");
        raceField.setText("");
        originField.setText("");
        religionField.setText("");

        fillChoiceList((String) mainChoice.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void editCharacter() {
        toggleFields(true);
    }

    @FXML
    private void saveCharacter() {
        if (nameField.getText() != null && !nameField.getText().isEmpty()) {
            nameLabel.setText(nameField.getText());
        } else {
            newCharacters++;
            nameLabel.setText("New Character " + newCharacters);
        }
        loginLabel.setText(loginField.getText());
        forumLabel.setText(forumField.getText());
        Text text = new Text(descriptionField.getText());
        text.setId("text");
        text.wrappingWidthProperty().bind(descriptionScroll.widthProperty());
        descriptionScroll.setContent(text);
        tagsLabel.setText(tagsField.getText());
        classesLabel.setText(classesField.getText());
        alignmentLabel.setText(alignmentField.getText());
        raceLabel.setText(raceField.getText());
        originLabel.setText(originField.getText());
        religionLabel.setText(religionField.getText());

        toggleFields(false);

        currentCharacter.setName(nameLabel.getText());
        currentCharacter.setLogin(loginLabel.getText());
        currentCharacter.setForum(forumLabel.getText());
        Text descriptionText = (Text) descriptionScroll.getContent();
        currentCharacter.setDescription(descriptionText.getText());
        currentCharacter.setTags(tagsLabel.getText());
        currentCharacter.setClasses(classesLabel.getText());
        currentCharacter.setAlignment(alignmentLabel.getText());
        currentCharacter.setRace(raceLabel.getText());
        currentCharacter.setOrigin(originLabel.getText());
        currentCharacter.setReligion(religionLabel.getText());
        
        saveCharacterChoices();

        fillCharacterList();
        characterList.getSelectionModel().select(currentCharacterIndex);
        displayCharacter((String) characterList.getSelectionModel().getSelectedItem());
    }

    private void saveCharacterChoices() {
        currentCharacter.getAbilities().setStrength((String) strengthChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getAbilities().setDexterity((String) dexterityChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getAbilities().setConstitution((String) constitutionChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getAbilities().setWisdom((String) wisdomChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getAbilities().setIntelligence((String) intelligenceChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getAbilities().setCharisma((String) charismaChoice.getSelectionModel().getSelectedItem());

        currentCharacter.getSaves().setFortitude((String) fortitudeChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSaves().setReflex((String) reflexChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSaves().setWill((String) willChoice.getSelectionModel().getSelectedItem());
        
        currentCharacter.getSkills().setHide((String) hideChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setMoveSilently((String) moveSilentlyChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setSpot((String) spotChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setListen((String) listenChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setSearch((String) searchChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setPerform((String) performChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setConcentration((String) concentrationChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setParry((String) parryChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setLore((String) loreChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setSpellcraft((String) spellcraftChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setBluff((String) bluffChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setPersuade((String) persuadeChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setSlightOfHand((String) slightOfHandChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setDiscipline((String) disciplineChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setAntagonize((String) antagonizeChoice.getSelectionModel().getSelectedItem());
        currentCharacter.getSkills().setPickLock((String) pickLockChoice.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void searchAll() {
        if (searchField.getText() != null && !searchField.getText().isEmpty()) {
            List<String> characterNamelist = new ArrayList<>();
            for (Character character : characters.getCharacters()) {
                if (character.getName().contains(searchField.getText()) || character.getTags().contains(searchField.getText())) {
                    characterNamelist.add(character.getName());
                }
            }
            if (!characterNamelist.isEmpty()) {
                ObservableList<String> items = FXCollections.observableArrayList(characterNamelist);
                characterList.setItems(items);
                characterList.getSelectionModel().select(0);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Characters Found");
                alert.setHeaderText(null);
                alert.setContentText("No character were found based on this criteria.");

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
            List<String> characterNameList = new ArrayList<>();
            for (Character character : characters.getCharacters()) {
                if (character.getName().contains(searchField.getText())) {
                    characterNameList.add(character.getName());
                }
            }
            if (!characterNameList.isEmpty()) {
                ObservableList<String> items = FXCollections.observableArrayList(characterNameList);
                characterList.setItems(items);
                characterList.getSelectionModel().select(0);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Characters Found");
                alert.setHeaderText(null);
                alert.setContentText("No characters were found based on this criteria.");

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
            List<String> characterNameList = new ArrayList<>();
            for (Character character : characters.getCharacters()) {
                if (character.getTags().contains(searchField.getText())) {
                    characterNameList.add(character.getName());
                }
            }
            if (!characterNameList.isEmpty()) {
                ObservableList<String> items = FXCollections.observableArrayList(characterNameList);
                characterList.setItems(items);
                characterList.getSelectionModel().select(0);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Characters Found");
                alert.setHeaderText(null);
                alert.setContentText("No characters were found based on this criteria.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
                dialogPane.setId("dialog");

                alert.showAndWait();
            }
            searchField.setText("");
        }
    }

    @FXML
    private void fillCharacterList() {
        List<String> characterNameList = new ArrayList<>();
        for (Character character : characters.getCharacters()) {
            characterNameList.add(character.getName());
        }
        ObservableList<String> items = FXCollections.observableArrayList(characterNameList);
        characterList.setItems(items);
    }

    private void toggleFields(boolean editMode) {

        if (editMode) {
            nameField.setText(nameLabel.getText());
            loginField.setText(loginLabel.getText());
            forumField.setText(forumLabel.getText());
            Text descriptionText = (Text) descriptionScroll.getContent();
            descriptionField.setText(descriptionText.getText());
            tagsField.setText(tagsLabel.getText());
            classesField.setText(classesLabel.getText());
            alignmentField.setText(alignmentLabel.getText());
            raceField.setText(raceLabel.getText());
            originField.setText(originLabel.getText());
            religionField.setText(religionLabel.getText());
        }

        cancelButton.setVisible(editMode);
        editButton.setVisible(!editMode);
        labelVBox.setVisible(!editMode);
        fieldVBox.setVisible(editMode);
    }

    private void loadProperties() throws ToolException {
        try (InputStream inputStream = new FileInputStream("config.properties");) {
            properties.load(inputStream);

            String charactersXml = properties.getProperty("characters");
            if (properties.getProperty("newCharacters") != null && !properties.getProperty("newCharacters").isEmpty()) {
                newCharacters = Integer.parseInt(properties.getProperty("newCharacters"));
            }

            if (charactersXml == null || charactersXml.isEmpty()) {
                newCharacters++;
                characters = new Characters();
                currentCharacter = new Character();
                currentCharacter.setName("New Character " + newCharacters);
                characters.getCharacters().add(currentCharacter);
            } else {
                try (StringReader reader = new StringReader(charactersXml)) {
                    JAXBContext jc = JAXBContext.newInstance(Characters.class);
                    Unmarshaller unmarshaller = jc.createUnmarshaller();
                    characters = (Characters) unmarshaller.unmarshal(reader);
                } catch (JAXBException ex) {
                    throw new ToolException("Error parsing properties.", ex);
                }
            }

        } catch (FileNotFoundException ex) {
            newCharacters++;
            characters = new Characters();
            currentCharacter = new Character();
            currentCharacter.setName("New Character " + newCharacters);
            characters.getCharacters().add(currentCharacter);
        } catch (Exception ex) {
            characters = new Characters();
            throw new ToolException("Error loading properties.", ex);
        }
    }

    @FXML
    private void saveProperties() throws ToolException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Characters.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(characters, writer);
            properties.setProperty("characters", writer.toString());
            properties.setProperty("newCharacters", "" + newCharacters);

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

    public void fillChoices() {
        abilityChoices = new ArrayList<>();
        saveChoices = new ArrayList<>();
        skillChoices = new ArrayList<>();

        abilityChoices.add(strengthChoice);
        abilityChoices.add(dexterityChoice);
        abilityChoices.add(constitutionChoice);
        abilityChoices.add(wisdomChoice);
        abilityChoices.add(intelligenceChoice);
        abilityChoices.add(charismaChoice);

        saveChoices.add(fortitudeChoice);
        saveChoices.add(reflexChoice);
        saveChoices.add(willChoice);
        
        skillChoices.add(hideChoice);
        skillChoices.add(spotChoice);
        skillChoices.add(spotChoice);
        skillChoices.add(listenChoice);
        skillChoices.add(searchChoice);
        skillChoices.add(performChoice);
        skillChoices.add(concentrationChoice);
        skillChoices.add(parryChoice);
        skillChoices.add(loreChoice);
        skillChoices.add(spellcraftChoice);
        skillChoices.add(bluffChoice);
        skillChoices.add(persuadeChoice);
        skillChoices.add(slightOfHandChoice);
        skillChoices.add(disciplineChoice);
        skillChoices.add(antagonizeChoice);
        skillChoices.add(pickLockChoice);

        for (ChoiceBox choice : abilityChoices) {
            ObservableList<String> items = FXCollections.observableArrayList();
            for (int i = -5; i <= 9; i++) {
                items.add("" + i);
            }
            choice.setItems(items);
        }

        for (ChoiceBox choice : saveChoices) {
            ObservableList<String> items = FXCollections.observableArrayList();
            for (int i = -10; i <= 40; i++) {
                items.add("" + i);
            }
            choice.setItems(items);
        }

        for (ChoiceBox choice : skillChoices) {
            ObservableList<String> items = FXCollections.observableArrayList();
            for (int i = -20; i <= 100; i++) {
                items.add("" + i);
            }
            choice.setItems(items);
        }

    }

    public void fillChoiceList(String choiceType) {

        switch (choiceType) {
            case "Abilities":
                abilityList.setVisible(true);
                saveList.setVisible(false);
                skillList.setVisible(false);
                strengthChoice.getSelectionModel().select(currentCharacter.getAbilities().getStrength());
                dexterityChoice.getSelectionModel().select(currentCharacter.getAbilities().getDexterity());
                constitutionChoice.getSelectionModel().select(currentCharacter.getAbilities().getConstitution());
                wisdomChoice.getSelectionModel().select(currentCharacter.getAbilities().getWisdom());
                intelligenceChoice.getSelectionModel().select(currentCharacter.getAbilities().getIntelligence());
                charismaChoice.getSelectionModel().select(currentCharacter.getAbilities().getCharisma());
                
                label1.setText("Strength:");
                label2.setText("Dexterity:");
                label3.setText("Constitution:");
                label4.setText("Wisdom:");
                label5.setText("Intelligence:");
                label6.setText("Charisma:");
                label7.setText("");
                label8.setText("");
                label9.setText("");
                label10.setText("");
                label11.setText("");
                label12.setText("");
                label13.setText("");
                label14.setText("");
                label15.setText("");
                label16.setText("");
                
                break;
            case "Saves":
                saveList.setVisible(true);
                abilityList.setVisible(false);
                skillList.setVisible(false);
                fortitudeChoice.getSelectionModel().select(currentCharacter.getSaves().getFortitude());
                reflexChoice.getSelectionModel().select(currentCharacter.getSaves().getReflex());
                willChoice.getSelectionModel().select(currentCharacter.getSaves().getWill());
                
                label1.setText("Fortitude:");
                label2.setText("Reflex:");
                label3.setText("Will:");
                label4.setText("");
                label5.setText("");
                label6.setText("");
                label7.setText("");
                label8.setText("");
                label9.setText("");
                label10.setText("");
                label11.setText("");
                label12.setText("");
                label13.setText("");
                label14.setText("");
                label15.setText("");
                label16.setText("");
                break;
            case "Skills":
                skillList.setVisible(true);
                abilityList.setVisible(false);
                saveList.setVisible(false);
                hideChoice.getSelectionModel().select(currentCharacter.getSkills().getHide());
                moveSilentlyChoice.getSelectionModel().select(currentCharacter.getSkills().getMoveSilently());
                spotChoice.getSelectionModel().select(currentCharacter.getSkills().getSpot());
                
                listenChoice.getSelectionModel().select(currentCharacter.getSkills().getListen());
                searchChoice.getSelectionModel().select(currentCharacter.getSkills().getSearch());
                performChoice.getSelectionModel().select(currentCharacter.getSkills().getPerform());
                concentrationChoice.getSelectionModel().select(currentCharacter.getSkills().getConcentration());
                parryChoice.getSelectionModel().select(currentCharacter.getSkills().getParry());
                loreChoice.getSelectionModel().select(currentCharacter.getSkills().getLore());
                spellcraftChoice.getSelectionModel().select(currentCharacter.getSkills().getSpellcraft());
                bluffChoice.getSelectionModel().select(currentCharacter.getSkills().getBluff());
                persuadeChoice.getSelectionModel().select(currentCharacter.getSkills().getPersuade());
                slightOfHandChoice.getSelectionModel().select(currentCharacter.getSkills().getSlightOfHand());
                disciplineChoice.getSelectionModel().select(currentCharacter.getSkills().getDiscipline());
                antagonizeChoice.getSelectionModel().select(currentCharacter.getSkills().getAntagonize());
                pickLockChoice.getSelectionModel().select(currentCharacter.getSkills().getPickLock());
                
                label1.setText("Hide:");
                label2.setText("Move Silenty:");
                label3.setText("Spot:");
                label4.setText("Listen:");
                label5.setText("Search:");
                label6.setText("Perform:");
                label7.setText("Concentration");
                label8.setText("Parry:");
                label9.setText("Lore:");
                label10.setText("Spellcraft:");
                label11.setText("Bluff:");
                label12.setText("Persuade:");
                label13.setText("Slight of Hand:");
                label14.setText("Discipline:");
                label15.setText("Antagonize:");
                label16.setText("Pick Lock:");
                break;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            characterList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    displayCharacter(newValue);
                    if (characterList.getSelectionModel().getSelectedIndex() != -1) {
                        currentCharacterIndex = characterList.getSelectionModel().getSelectedIndex();
                    }
                }
            });
            newCharacters = 0;
            properties = new Properties();

            mainChoice.setItems(FXCollections.observableArrayList("Abilities", "Saves", "Skills"));
            mainChoice.getSelectionModel().select("Abilities");
            mainChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    fillChoiceList(newValue);
                }
            });

            loadProperties();
            fillChoices();
            fillCharacterList();
            characterList.getSelectionModel().select(0);
            toggleFields(false);
        } catch (ToolException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
