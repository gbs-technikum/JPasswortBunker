package jpasswortbunker.mgm.gui;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import jpasswortbunker.mgm.entry.Entry;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

public class EditEntryController implements Initializable {

    @FXML
    private JFXTextField textFieldTitle, textFieldUsername, textFieldURL;

    @FXML
    private JFXPasswordField passwordField1, passwordField2;

    @FXML
    private JFXTextArea textAreaDescription;

    @FXML
    private Label labelErrorMessage;

    @FXML
    private JFXButton btn_save;

    @FXML
    public JFXComboBox<Label> comboBox = new JFXComboBox<Label>();

    @FXML
    public TreeItem<Entry> entry;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().add(new Label("Kategorie0"));
        comboBox.getItems().add(new Label("Kategorie1"));
        comboBox.getItems().add(new Label("Kategorie2"));
        comboBox.getItems().add(new Label("Kategorie3"));
        comboBox.setPromptText("Select categorie");

    }


    public void btn_save(ActionEvent actionEvent) {
        System.out.println("btn_save gedrückt");

        changeEntry();

        Stage stage = (Stage) btn_save.getScene().getWindow();
        stage.close();
    }

    //Übergebenes Element wird in die jeweiligen Felder geschrieben
    public void setEntry(TreeItem<Entry> selectedEntry) {
        this.entry = selectedEntry;

        textFieldTitle.setText(entry.getValue().getTitle());
        textFieldUsername.setText(entry.getValue().getUsername());
        passwordField1.setText(entry.getValue().getPassword());
        passwordField2.setText(entry.getValue().getPassword());
        textFieldURL.setText(entry.getValue().getUrl());
        textAreaDescription.setText(entry.getValue().getDescription());
        comboBox.getSelectionModel().select(entry.getValue().getCategorieID());
    }

    //Ändert den bestehenden Eintrag
    public Boolean changeEntry() {

        this.entry.getValue().setTitle(textFieldTitle.getText());



        return false;
    }










}

