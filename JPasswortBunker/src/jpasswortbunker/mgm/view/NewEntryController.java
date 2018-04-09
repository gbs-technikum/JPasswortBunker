package jpasswortbunker.mgm.view;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private JFXTextField tfTitle, tfUsername, tfURL;

    @FXML
    private JFXPasswordField pf1, pf2;

    @FXML
    private JFXTextArea taDescription;

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
                presenter.newEntry(tfTitle.getText(), tfUsername.getText(), pf1.getText(), tfURL.getText(),
                        taDescription.getText(),(comboBox.getSelectionModel().getSelectedIndex()+1));

                //Eingefügt Wagenhuber: Zwischenspeichern der gewählten Kategorie, um diese anschließend im View anzuzeigen
                presenter.setCategoryChoosenForLastNewEntry((comboBox.getSelectionModel().getSelectedIndex()+1));





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
    }

    public void btn_createPassword(ActionEvent actionEvent) throws SQLException {
        System.out.println("Test createPassword");
        String randomPassword = presenter.createPassword();
        pf1.setText(randomPassword);
        pf2.setText(randomPassword);
    }

    public void btn_eyeIcon(ActionEvent actionEvent){
        System.out.println("Test Auge");
    }

    //Ueberpruefung ob Passwoerter gleich sind gibt true oder false zurück und setzt Label bei false
    private boolean equalsPassword() {
        if (pf1.getText().equals(pf2.getText())) {
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
        comboBox.setPromptText("Category ...");
    }


    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }
}

//test hallo
