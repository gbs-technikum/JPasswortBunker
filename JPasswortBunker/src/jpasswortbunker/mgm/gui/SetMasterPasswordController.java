package jpasswortbunker.mgm.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.FileNotFoundException;

public class SetMasterPasswordController {

    @FXML
    private PasswordField password_box;

    @FXML
    private PasswordField repeatPassword_box;

    @FXML
    private Label l_MasterKeyWrong;

    public void correctMasterKey(ActionEvent actionEvent) {
        if (equalsPassword(password_box.getText())) {
            System.out.println("Passwort erfolgreich");
        }else{
            l_MasterKeyWrong.setText("Passwords not equals!");
        }
    }

    private boolean equalsPasswordwithList(String password) throws FileNotFoundException {
        //new BufferedReader()br =
        return false;
    }

    private boolean equalsPassword(String password) {
        if (password.equals(repeatPassword_box.getText())) {
            return true;
        }
        return false;
    }
}
