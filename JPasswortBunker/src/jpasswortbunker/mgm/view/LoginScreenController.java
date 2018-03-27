package jpasswortbunker.mgm.view;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class LoginScreenController {

    @FXML
    private PasswordField password_box;

    @FXML
    private Label wrongPassword;

    @FXML
    private JFXButton btn_login;

    private PresenterMain presenter;

    //EventHandling von Login Button
    public void btn_login(ActionEvent actionEvent) throws IOException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        presenter.initMasterPassword(password_box.getText());
        if (presenter.checkIfMasterPasswordIsCorrect()) {
            Stage stage = (Stage) btn_login.getScene().getWindow();
            stage.close();
        } else {
            wrongPassword.setText("Wrong Password");
        }
    }


    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }
}

