package jpasswortbunker.mgm.view;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jpasswortbunker.mgm.entry.Entry;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.sql.SQLException;

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

    public PresenterMain presenter;

    @FXML
    public void initialize() {
        comboBox.getItems().add(new Label("Finance"));
        comboBox.getItems().add(new Label("Social"));
        comboBox.getItems().add(new Label("E-Mail"));
        comboBox.getItems().add(new Label("Settings"));
        comboBox.setPromptText("Select categorie");
    }

    public void btn_save(ActionEvent actionEvent) {
        if (equalsPassword()) {
            try {
                /**
                 * comboBox.getValue().getText();
                 * wird aufgerufen, um zu überprüfen ob eine Kategorie ausgewählt ist
                 * nein -> gibt eine Exeption und geht in den catch teil
                 * Ja -> Eintrag wird erstellt
                 */
                comboBox.getValue().getText();
                presenter.newEntry(tfTitle.getText(), tfUsername.getText(), pf1.getText(), tfURL.getText(), taDescription.getText(),comboBox.getSelectionModel().getSelectedIndex());
                Stage stage = (Stage) btn_save.getScene().getWindow();
                stage.close();
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

    }

    //Ueberpruefung ob Passwoerter gleich sind gibt true oder false zurück und setzt Label bei false
    private boolean equalsPassword() {
        if (pf1.getText().equals(pf2.getText())) {
            return true;
        }
        labelErrorMessage.setText("Password not Equals");
        return false;
    }


    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }
}

