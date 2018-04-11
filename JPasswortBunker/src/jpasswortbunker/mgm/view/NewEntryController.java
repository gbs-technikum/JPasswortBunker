package jpasswortbunker.mgm.view;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewEntryController{

    @FXML
    private JFXTextField textFieldTitle, textFieldUsername, textFieldURL, textFieldPassword1, textFieldPassword2;

    @FXML
    private JFXPasswordField passwordField1, passwordField2;

    @FXML
    private JFXTextArea textAreaDescription;

    @FXML
    private Label labelErrorMessage;

    @FXML
    private JFXButton btn_save, btn_key, btn_eye;

    @FXML
    public JFXComboBox<Label> comboBox = new JFXComboBox<Label>();

    public PresenterMain presenter;

    @FXML
    public void initialize() {
        btn_eye.setTooltip(new Tooltip("Show Password"));
        btn_key.setTooltip(new Tooltip("Generate a random Password"));
        textFieldPassword1.setManaged(false);
        textFieldPassword1.setVisible(false);
        textFieldPassword2.setManaged(false);
        textFieldPassword2.setVisible(false);
    }

    /**
     * public void btn_save(ActionEvent actionEvent)
     * speichert den neu erstellten Eintrag
     */
    public void btn_save(ActionEvent actionEvent) {
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

                    //Eingefügt Wagenhuber: Zwischenspeichern der gewählten Kategorie, um diese anschließend im View anzuzeigen
                    presenter.setCategoryChoosenForLastNewEntry((comboBox.getSelectionModel().getSelectedIndex() + 1));


                    Stage stage = (Stage) btn_save.getScene().getWindow();
                    stage.close();
                    stage.setResizable(false);
                    System.out.println("neuer Eintrag angelegt");
                } catch (NullPointerException e) {
                    System.out.println("Kategorie nicht ausgewählt");
                    labelErrorMessage.setText("Please choose a categorie");

                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Eintrag konnte nicht erstellt werden");
            }
        } else {
            labelErrorMessage.setText("Please set a title");
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
        labelErrorMessage.setText("Password not Equals");
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
        comboBox.setPromptText("Category ...");
    }

    /**
     * public void setPresenter(PresenterMain presenter)
     * Bekommt den Presenter übergeben
     */
    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }
}

//test hallo
//test2