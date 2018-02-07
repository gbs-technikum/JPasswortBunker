package jpasswortbunker.mgm.gui;

import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jpasswortbunker.mgm.entry.Entry;

public class NewEntryController{

    @FXML
    private JFXTextField tfTitle, tfUsername, tfURL;

    @FXML
    private JFXPasswordField pf1, pf2;

    @FXML
    private JFXTextArea taDescription;

    @FXML
    private Label labelErrorMessage;

    @FXML
    private JFXButton btn_save;

    @FXML
    public JFXComboBox<Label> comboBox = new JFXComboBox<Label>();

    @FXML
    public void initialize() {
        comboBox.getItems().add(new Label("Java 1.8"));
        comboBox.getItems().add(new Label("Java 1.7"));
        comboBox.getItems().add(new Label("Java 1.6"));
        comboBox.getItems().add(new Label("Java 1.5"));

        comboBox.setPromptText("Select categorie");
    }

    public void btn_save(ActionEvent actionEvent) {


        if (equalsPassword()) {
            try {
                Entry entry = new Entry(tfTitle.getText(), tfUsername.getText(), pf1.getText(), tfURL.getText(), taDescription.getText(), 1);
                System.out.println("neuer Eintrag angelegt");
                System.out.println(comboBox.getValue().getText());
                System.out.println(entry.toString());
                Stage stage = (Stage) btn_save.getScene().getWindow();
                stage.close();
            } catch (NullPointerException e) {
                System.out.println("Kategorie nicht ausgewählt");
                labelErrorMessage.setText("Please choose a categorie");
            }
        } else {
            System.out.println("Eintrag konnte nicht erstellt werden");
        }

    }

    //Ueberpruefung ob Passwoerter gleich sind gibt true oder false zurück und setzt Label bei false
    private boolean equalsPassword() {
        if (pf1.getText().equals(pf2.getText())) {
            return true;
        }
        labelErrorMessage.setText("Password not Equals");
        return false;
    }



}

