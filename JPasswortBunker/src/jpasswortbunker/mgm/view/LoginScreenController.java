package jpasswortbunker.mgm.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import jpasswortbunker.mgm.presenter.PresenterMain;

import java.io.IOException;


public class LoginScreenController {

    //ToDo nur zum Test
    private String password = "";

    @FXML
    private PasswordField password_box;

    @FXML
    private Label wrongPassword;

    private PresenterMain presenter;

    //EventHandling von Login Button
    public void btn_login(ActionEvent actionEvent) throws IOException {
        System.out.println("eingegebenes Passwort: " + password_box.getText());
        if (checkPassword(password_box.getText())) {
//            Parent home_page_parent = FXMLLoader.load(getClass().getResource("MainInterface.fxml"));
//            Scene home_page_scene = new Scene(home_page_parent);
//
//            URL url = this.getClass().getResource("style/default_style.css");
//            if (url == null) {
//                System.out.println(" css Resource not found. Aborting.");
//                System.exit(-1);
//            }
//            String css = url.toExternalForm();
//            home_page_scene.getStylesheets().add(css);
//
//            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            app_stage.setResizable(false);
//            app_stage.setScene(home_page_scene);
//            app_stage.show();
        } else {
            wrongPassword.setText("Wrong Password");
        }


    }

    //überprüft ob das eingegebene Passwort übereinstimmt
    //ToDO Passwort von DB abrufen
    private boolean checkPassword(String eingabePassword) {
        if (eingabePassword.equals(password)) {
            return true;
        }
        return false;
    }

    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }
}

