package jpasswortbunker.mgm.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public final class SetMasterPasswordController{

    @FXML
    private Label labelErrorMessage;

    @FXML
    private JFXPasswordField passwordField1, passwordField2;

    @FXML
    private JFXButton btn_OK;

    private PresenterMain presenter;

    /**
     * public void setPresenter(PresenterMain presenter)
     * bekommt als Parameter den presenter übergeben
     */
    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }

    public void btn_setMasterPassword(ActionEvent actionEvent) throws BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (!(passwordField1.getText().equals("") || passwordField2.getText().equals(""))) {
            if (equalsPassword()) {
                presenter.setMasterPasswordinDB(passwordField1.getText());
                Stage stage = (Stage) btn_OK.getScene().getWindow();
                stage.close();
            }
        } else {
            labelErrorMessage.setText("Field can not be empty");
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

}
