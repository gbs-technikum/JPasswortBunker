package jpasswortbunker.mgm.view;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jpasswortbunker.mgm.presenter.PresenterMain;

public final class SetMasterPasswordController{

    @FXML
    private Label labelErrorMessage;

    @FXML
    private JFXPasswordField passwordField1, passwordField2;

    private PresenterMain presenter;

    /**
     * public void setPresenter(PresenterMain presenter)
     * bekommt als Parameter den presenter übergeben
     */
    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }

    public void btn_setMasterPassword(ActionEvent actionEvent) {
        if (!(passwordField1.getText().equals("") || passwordField2.getText().equals(""))) {
            if (equalsPassword()) {
                System.out.println("Eingaben gleich");
            }
        } else {
            labelErrorMessage.setText("Field can not be empty");
        }
        System.out.println("Button gedrückt");
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
