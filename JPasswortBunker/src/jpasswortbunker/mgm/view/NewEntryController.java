package jpasswortbunker.mgm.view;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewEntryController{

    @FXML
    private JFXTextField textFieldTitle, textFieldUsername, textFieldURL, textFieldPassword1, textFieldPassword2;

    @FXML
    private JFXPasswordField passwordField1, passwordField2;

    @FXML
    private JFXTextArea textAreaDescription;

    @FXML
    private Label labelErrorMessage, labelEntryHead, labelDescriptionText;

    @FXML
    private JFXButton btn_save, btn_key, btn_eye;

    @FXML
    public JFXComboBox<Label> comboBox = new JFXComboBox<Label>();

    public PresenterMain presenter;
    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        textFieldPassword1.setManaged(false);
        textFieldPassword1.setVisible(false);
        textFieldPassword2.setManaged(false);
        textFieldPassword2.setVisible(false);
    }

    /**
     * public void btn_save(ActionEvent actionEvent)
     * speichert den neu erstellten Eintrag
     */
    public void btn_save(ActionEvent actionEvent) throws IllegalBlockSizeException, SQLException, InvalidKeyException, BadPaddingException, UnsupportedEncodingException {
        if (!textFieldTitle.getText().equals("")) {
            if (equalsPassword()) {
                try {
                    /**
                     * comboBox.getValue().getText();
                     * wird aufgerufen, um zu überprüfen ob eine Kategorie ausgewählt ist
                     * nein -> gibt eine Exeption und geht in den catch teil
                     * Ja -> Eintrag wird erstellt
                     */
                    comboBox.getValue().getText();
                    presenter.newEntry(textFieldTitle.getText(), textFieldUsername.getText(), passwordField1.getText(), textFieldURL.getText(),
                            textAreaDescription.getText(), (comboBox.getSelectionModel().getSelectedIndex() + 1));

                    presenter.setCategoryChoosenForLastNewEntry((comboBox.getSelectionModel().getSelectedIndex() + 1));


                    Stage stage = (Stage) btn_save.getScene().getWindow();
                    stage.close();
                    stage.setResizable(false);
                    System.out.println("##Status## Neuer Eintrag angelegt");
                } catch (NullPointerException e) {
                    System.out.println("##Status## Kategorie nicht ausgewählt");
                    labelErrorMessage.setText(bundle.getString("entry.labelErrorMessage.chooseCategorie"));


                }
            } else {
                System.out.println("##Status## Eintrag konnte nicht erstellt werden");
            }
        } else {
            labelErrorMessage.setText(bundle.getString("entry.labelErrorMessage.setTitle"));

        }

    }

    /**
     * public void btn_createPassword(ActionEvent actionEvent)
     * Ruft die Methode auf dem Presenter bzw. Model auf
     * und bekommt ein Random Passwort anhand der eingestellten Kriterien
     */
    public void btn_createPassword(ActionEvent actionEvent) throws SQLException {
        String randomPassword = presenter.createPassword();
        passwordField1.setText(randomPassword);
        passwordField2.setText(randomPassword);
    }

    /**
     * public void btn_eyeIcon(ActionEvent actionEvent)
     * Zeit das Passwort im Klartext an, indem das PasswortFeld ausgeblendet wird und Textfeld eingeblendet
     * Felder sind Bidirektional miteinander verbunden
     */
    public void btn_eyeIcon(ActionEvent actionEvent){
        textFieldPassword1.textProperty().bindBidirectional(passwordField1.textProperty());
        textFieldPassword2.textProperty().bindBidirectional(passwordField2.textProperty());
        if (passwordField1.isVisible()) {
            textFieldPassword1.setManaged(true);
            textFieldPassword1.setVisible(true);
            passwordField1.setManaged(false);
            passwordField1.setVisible(false);
            textFieldPassword2.setManaged(true);
            textFieldPassword2.setVisible(true);
            passwordField2.setManaged(false);
            passwordField2.setVisible(false);
        } else {
            textFieldPassword1.setManaged(false);
            textFieldPassword1.setVisible(false);
            passwordField1.setManaged(true);
            passwordField1.setVisible(true);
            textFieldPassword2.setManaged(false);
            textFieldPassword2.setVisible(false);
            passwordField2.setManaged(true);
            passwordField2.setVisible(true);
        }
    }

    //Ueberpruefung ob Passwoerter gleich sind gibt true oder false zurück und setzt Label bei false
    private boolean equalsPassword() {
        if (passwordField1.getText().equals(passwordField2.getText())) {
            return true;
        }
        labelErrorMessage.setText(bundle.getString("entry.labelErrorMessage.passwordEquals"));

        return false;
    }

    /**
     * public void fillComboBox()
     * Füllt die ComboBox mit den Kategorien aus der DB
     */
    public void fillComboBox() throws SQLException {
        ArrayList<String> categoryList = (ArrayList<String>) presenter.getCategoryListFromDB();
        for (int i = 1; i < categoryList.size(); i++) {
            comboBox.getItems().add(new Label(categoryList.get(i)));
        }
        comboBox.setPromptText(bundle.getString("entry.comboBox.promptText"));
    }

    /**
     * public void setPresenter(PresenterMain presenter)
     * Bekommt den Presenter übergeben
     */
    public void setPresenter(PresenterMain presenter) throws SQLException {
        this.presenter = presenter;
        bundle = presenter.getLangBundle();
        setToolTip();
        setLang();
    }

    private void setLang() {
        textFieldTitle.setPromptText(bundle.getString("entry.promptTextFieldTitle"));
        textFieldUsername.setPromptText(bundle.getString("entry.promptTextFieldUsername"));
        textFieldURL.setPromptText(bundle.getString("entry.promptTextFieldURL"));
        textFieldPassword1.setPromptText(bundle.getString("entry.promptTextPassword1"));
        textFieldPassword2.setPromptText(bundle.getString("entry.promptTextPassword2"));
        passwordField1.setPromptText(bundle.getString("entry.promptTextPassword1"));
        passwordField2.setPromptText(bundle.getString("entry.promptTextPassword2"));
        btn_save.setText(bundle.getString("entry.button.save"));
        labelDescriptionText.setText(bundle.getString("entry.label.descriptionText"));
        labelEntryHead.setText(bundle.getString("entryNew.label.entryHead"));
    }

    private void setToolTip() {
        btn_eye.setTooltip(new Tooltip(bundle.getString("entry.tooltip.showPassword")));
        btn_key.setTooltip(new Tooltip(bundle.getString("entry.tooltip.generatePassword")));
    }

}
