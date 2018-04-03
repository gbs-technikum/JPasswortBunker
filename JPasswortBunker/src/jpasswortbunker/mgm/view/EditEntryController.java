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
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public void initialize() throws SQLException {

    }

    public void btn_save(ActionEvent actionEvent) throws IllegalBlockSizeException, SQLException, InvalidKeyException, BadPaddingException, UnsupportedEncodingException {

        if (changeEntry()) {
            Stage stage = (Stage) btn_save.getScene().getWindow();
            stage.setResizable(false);
            stage.close();
            stage.show();
        }
    }

    public void btn_eyeIcon(MouseEvent mouseEvent){
        System.out.println("Test Auge");
    }

    public void btn_copyPasswordToClipboard(MouseEvent mouseEvent) {
        Ziwschenablage();
        System.out.println("test: btn_copyPasswordToClipboard");
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
                entryProperty.getDescription().equals(textAreaDescription.getText()) &&
                entryProperty.getCategoryID() == (comboBox.getSelectionModel().getSelectedIndex()+1) ) {
        } else {
            if (equalsPassword()) {
                this.entryProperty.setTitle(textFieldTitle.getText());
                this.entryProperty.setUsername((textFieldUsername.getText()));
                this.entryProperty.setPassword(passwordField1.getText());
                this.entryProperty.setUrl(textFieldURL.getText());
                this.entryProperty.setDescription(textAreaDescription.getText());
                this.entryProperty.setCategoryID((comboBox.getSelectionModel().getSelectedIndex()+1));
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

    public void fillComboBox() throws SQLException {
        System.out.println("test");
        ArrayList<String> categoryList = (ArrayList<String>) presenter.getCategoryListFromDB();
        for (int i = 1; i < categoryList.size(); i++) {
            comboBox.getItems().add(new Label(categoryList.get(i)));
        }
        comboBox.setPromptText("Select categorie");
        comboBox.getSelectionModel().select(entryProperty.getCategoryID()-1);
    }



    public void setPresenter(PresenterMain presenter) throws SQLException {
        this.presenter = presenter;
    }

    public void Ziwschenablage() {
        Clipboard systemClip = Toolkit.getDefaultToolkit().getSystemClipboard();

        systemClip.setContents(new StringSelection("Ich bin die Zwischenablge"), null);

        Transferable transfer = systemClip.getContents(null);

        for (int i = 0; i < transfer.getTransferDataFlavors().length; i++)
        {
            Object content = null;

            try {
                content = transfer.getTransferData(transfer.getTransferDataFlavors()[i]);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (content instanceof String) {
                System.out.println(content);

            }

        }

    }

    }




