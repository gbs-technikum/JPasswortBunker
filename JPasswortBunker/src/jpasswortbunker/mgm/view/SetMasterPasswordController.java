package jpasswortbunker.mgm.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import jpasswortbunker.mgm.presenter.PresenterMain;

import java.io.IOException;

public final class SetMasterPasswordController{

    @FXML
    private PasswordField password_box;

    @FXML
    private PasswordField repeatPassword_box;

    @FXML
    private Label l_MasterKeyWrong;

    private PresenterMain presenter;

    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }

    public void btn_setMasterPassword(ActionEvent actionEvent) {
        System.out.println("Button gedrückt");
    }

    public void test() {
        System.out.println("testMethode SetmasterPassword Controller");
    }

}
