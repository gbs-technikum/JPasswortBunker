package jpasswortbunker.mgm.view;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jpasswortbunker.mgm.presenter.EntryProperty;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditEntryController {

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



    public EntryProperty entryProperty;
    private PresenterMain presenter;

    @FXML
    public void initialize() {
        comboBox.getItems().add(new Label("Finance"));
        comboBox.getItems().add(new Label("Social"));
        comboBox.getItems().add(new Label("E-Mail"));
        comboBox.getItems().add(new Label("Settings"));
        comboBox.setPromptText("Select categorie");
    }

    public void btn_eyeIcon(MouseEvent mouseEvent) {
        System.out.println("Test Auge");
        System.out.println(passwordField1.getText());
    }


    public void btn_save(ActionEvent actionEvent) throws IllegalBlockSizeException, SQLException, InvalidKeyException, BadPaddingException, UnsupportedEncodingException {

        if (changeEntry()) {
            Stage stage = (Stage) btn_save.getScene().getWindow();
            stage.close();
        }
    }

    //Übergebenes Element wird in die jeweiligen Felder geschrieben
    public void setEntry(TreeItem<EntryProperty> selectedEntry) {
        this.entryProperty = selectedEntry.getValue();

        textFieldTitle.setText(entryProperty.getTitle());
        textFieldUsername.setText(entryProperty.getUsername());
        passwordField1.setText(entryProperty.getPassword());
        passwordField2.setText(entryProperty.getPassword());
        textFieldURL.setText(entryProperty.getUrl());
        textAreaDescription.setText(entryProperty.getDescription());
        comboBox.getSelectionModel().select(entryProperty.getCategoryID());
    }

    //Ändert den bestehenden Eintrag
    public Boolean changeEntry() throws SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException {
        //System.out.println(comboBox.getSelectionModel().getSelectedIndex());
//        EntryProperty entryPropertyEdit = new EntryProperty(entryProperty);
//
//
//        if (entryPropertyEdit.equals(entryProperty)) {
//            System.out.println("nix geändert");
//        } else {
//            System.out.println("es wurde was geändert");
//        }
//        System.out.println(entryPropertyEdit);
//        System.out.println(entryProperty);



        if (entryProperty.getTitle().equals(textFieldTitle.getText()) &&
                entryProperty.getUsername().equals(textFieldUsername.getText()) &&
                entryProperty.getPassword().equals(passwordField1.getText()) &&
                entryProperty.getPassword().equals(passwordField2.getText()) &&
                entryProperty.getUrl().equals(textFieldURL.getText()) &&
                entryProperty.getDescription().equals(textAreaDescription.getText()) && entryProperty.getCategoryID() == comboBox.getSelectionModel().getSelectedIndex() ) {
        } else {
            if (equalsPassword()) {
                this.entryProperty.setTitle(textFieldTitle.getText());
                this.entryProperty.setUsername((textFieldUsername.getText()));
                this.entryProperty.setPassword(passwordField1.getText());
                this.entryProperty.setUrl(textFieldURL.getText());
                this.entryProperty.setDescription(textAreaDescription.getText());
                this.entryProperty.setCategoryID(comboBox.getSelectionModel().getSelectedIndex());
                presenter.updateEntry(entryProperty);

            } else {
                return false;
            }
        }


        return true;
    }

    private boolean equalsPassword() {
        if (passwordField1.getText().equals(passwordField2.getText())) {
            return true;
        }
        labelErrorMessage.setText("Password not Equals");
        return false;
    }



    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }

}

