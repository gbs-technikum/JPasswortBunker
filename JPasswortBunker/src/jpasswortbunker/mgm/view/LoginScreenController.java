package jpasswortbunker.mgm.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
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
import java.util.ResourceBundle;


public class LoginScreenController {

    @FXML
    private PasswordField password_box;

    @FXML
    private Label label_wrongPassword, label_infoText;

    @FXML
    private JFXButton btn_login;

    @FXML
    private JFXTextField textFieldPassword;

    @FXML
    private JFXCheckBox checkBoxShowPassword;

    private PresenterMain presenter;
    private ResourceBundle bundle;


    @FXML
    public void initialize(){
        textFieldPassword.setManaged(false);
        textFieldPassword.setVisible(false);
    }

    //EventHandling von Login Button
    public void btn_login(ActionEvent actionEvent) throws IOException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        presenter.initMasterPassword(password_box.getText());
        if (presenter.checkIfMasterPasswordIsCorrect()) {
            Stage stage = (Stage) btn_login.getScene().getWindow();
            stage.setResizable(false);
            stage.close();
        } else {
            label_wrongPassword.setText(bundle.getString("login.labelWrongPassword"));
        }
    }

    public void checkBox(ActionEvent actionEvent) {
        textFieldPassword.textProperty().bindBidirectional(password_box.textProperty());
        if (password_box.isVisible()) {
            textFieldPassword.setManaged(true);
            textFieldPassword.setVisible(true);
            password_box.setManaged(false);
            password_box.setVisible(false);
        } else {
            textFieldPassword.setManaged(false);
            textFieldPassword.setVisible(false);
            password_box.setManaged(true);
            password_box.setVisible(true);
        }
    }

    public void setPresenter(PresenterMain presenter) throws SQLException {
        this.presenter = presenter;
        bundle = presenter.getLangBundle();
        setLang();
    }

    private void setLang() {
        btn_login.setText(bundle.getString("login.button"));
        label_infoText.setText(bundle.getString("login.infoText"));
        password_box.setPromptText(bundle.getString("login.passwordPromptText"));
        checkBoxShowPassword.setText(bundle.getString("login.checkBox.showPassword"));
        textFieldPassword.setPromptText(bundle.getString("login.passwordPromptText"));
    }


}

