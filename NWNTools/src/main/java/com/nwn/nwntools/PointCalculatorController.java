package com.nwn.nwntools;

import com.nwn.nwntools.PointCalculator.Ability;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class PointCalculatorController implements Initializable {

    private int totalPoints;
    private List<Ability> abilities;
    private Ability strength;
    private Ability dexterity;
    private Ability constitution;
    private Ability wisdom;
    private Ability intelligence;
    private Ability charisma;

    @FXML
    private Label totalLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label strengthLabel;
    @FXML
    private Label dexterityLabel;
    @FXML
    private Label constitutionLabel;
    @FXML
    private Label wisdomLabel;
    @FXML
    private Label intelligenceLabel;
    @FXML
    private Label charismaLabel;
    @FXML
    private Label strengthCost;
    @FXML
    private Label dexterityCost;
    @FXML
    private Label constitutionCost;
    @FXML
    private Label wisdomCost;
    @FXML
    private Label intelligenceCost;
    @FXML
    private Label charismaCost;
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
    private void clear() {
        this.totalPoints = 30;
        abilities = new ArrayList<>();
        strength = new Ability("Strength");
        dexterity = new Ability("Dexterity");
        constitution = new Ability("Constitution");
        wisdom = new Ability("Wisdom");
        intelligence = new Ability("Intelligence");
        charisma = new Ability("Charisma");
        abilities.add(strength);
        abilities.add(dexterity);
        abilities.add(constitution);
        abilities.add(wisdom);
        abilities.add(intelligence);
        abilities.add(charisma);

        for (Ability ability : abilities) {
            ability.calculateTotal();
        }

        clearChoiceBox();
        refresh();
    }
    
    private void clearChoiceBox() {
        strengthChoice.getSelectionModel().select(4);
        dexterityChoice.getSelectionModel().select(4);
        constitutionChoice.getSelectionModel().select(4);
        wisdomChoice.getSelectionModel().select(4);
        intelligenceChoice.getSelectionModel().select(4);
        charismaChoice.getSelectionModel().select(4);
    }
    
    private void initialiseChoiceBox() {
        strengthChoice.setItems(FXCollections.observableArrayList("-4", "-3", "-2", "-1", "0", "1", "2", "3", "4"));
        strengthChoice.getSelectionModel().select(4);
        strengthChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                strength.setOffset(Integer.parseInt((String) strengthChoice.getItems().get((Integer) number2)));
                refresh();
            }
        });
        dexterityChoice.setItems(FXCollections.observableArrayList("-4", "-3", "-2", "-1", "0", "1", "2", "3", "4"));
        dexterityChoice.getSelectionModel().select(4);
        dexterityChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                dexterity.setOffset(Integer.parseInt((String) dexterityChoice.getItems().get((Integer) number2)));
                refresh();
            }
        });
        constitutionChoice.setItems(FXCollections.observableArrayList("-4", "-3", "-2", "-1", "0", "1", "2", "3", "4"));
        constitutionChoice.getSelectionModel().select(4);
        constitutionChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                constitution.setOffset(Integer.parseInt((String) constitutionChoice.getItems().get((Integer) number2)));
                refresh();
            }
        });
        wisdomChoice.setItems(FXCollections.observableArrayList("-4", "-3", "-2", "-1", "0", "1", "2", "3", "4"));
        wisdomChoice.getSelectionModel().select(4);
        wisdomChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                wisdom.setOffset(Integer.parseInt((String) wisdomChoice.getItems().get((Integer) number2)));
                refresh();
            }
        });
        intelligenceChoice.setItems(FXCollections.observableArrayList("-4", "-3", "-2", "-1", "0", "1", "2", "3", "4"));
        intelligenceChoice.getSelectionModel().select(4);
        intelligenceChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                intelligence.setOffset(Integer.parseInt((String) intelligenceChoice.getItems().get((Integer) number2)));
                refresh();
            }
        });
        charismaChoice.setItems(FXCollections.observableArrayList("-4", "-3", "-2", "-1", "0", "1", "2", "3", "4"));
        charismaChoice.getSelectionModel().select(4);
        charismaChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                charisma.setOffset(Integer.parseInt((String) charismaChoice.getItems().get((Integer) number2)));
                refresh();
            }
        });
    }

    @FXML
    private void refresh() {
        strengthLabel.setText("" + strength.getAdjustedTotal() + "  [" + strength.checkBonus() + "]");
        dexterityLabel.setText("" + dexterity.getAdjustedTotal() + "  [" + dexterity.checkBonus() + "]");
        constitutionLabel.setText("" + constitution.getAdjustedTotal() + "  [" + constitution.checkBonus() + "]");
        wisdomLabel.setText("" + wisdom.getAdjustedTotal() + "  [" + wisdom.checkBonus() + "]");
        intelligenceLabel.setText("" + intelligence.getAdjustedTotal() + "  [" + intelligence.checkBonus() + "]");
        charismaLabel.setText("" + charisma.getAdjustedTotal() + "  [" + charisma.checkBonus() + "]");

        strengthCost.setText("" + strength.checkCost(true));
        dexterityCost.setText("" + dexterity.checkCost(true));
        constitutionCost.setText("" + constitution.checkCost(true));
        wisdomCost.setText("" + wisdom.checkCost(true));
        intelligenceCost.setText("" + intelligence.checkCost(true));
        charismaCost.setText("" + charisma.checkCost(true));

        totalLabel.setText("" + totalPoints);
        errorLabel.setText("");
    }

    @FXML
    private void addPoint(ActionEvent event) {
        Button button = (Button) event.getSource();
        String source = button.getId();
        for (Ability ability : abilities) {
            if (source.contains(ability.getName())) {
                int originalTotal = totalPoints;
                this.totalPoints = ability.addPoint(totalPoints);
                refresh();
                if (totalPoints == originalTotal) {
                    errorLabel.setText("Can't add further.");
                }
            }
        }
    }

    @FXML
    private void subtractPoint(ActionEvent event) {
        Button button = (Button) event.getSource();
        String source = button.getId();
        for (Ability ability : abilities) {
            if (source.contains(ability.getName())) {
                int originalTotal = totalPoints;
                this.totalPoints = ability.subtractPoint(totalPoints);
                refresh();
                if (totalPoints == originalTotal) {
                    errorLabel.setText("Can't subtract further.");
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clear();
        initialiseChoiceBox();
    }
}
