package jpasswortbunker.mgm.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import jpasswortbunker.mgm.presenter.PresenterMain;

import java.io.IOException;


public class LoginScreenController {

    @FXML
    private PasswordField password_box;

    @FXML
    private Label wrongPassword;

    private PresenterMain presenter;

    //EventHandling von Login Button
    public void btn_login(ActionEvent actionEvent) throws IOException {
        System.out.println("Button gedrückt");
    }


    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }
}

