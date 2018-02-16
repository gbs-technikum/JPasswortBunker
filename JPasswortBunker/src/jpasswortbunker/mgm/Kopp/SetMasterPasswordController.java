package jpasswortbunker.mgm.Kopp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class SetMasterPasswordController{

    @FXML
    private PasswordField password_box;

    @FXML
    private PasswordField repeatPassword_box;

    @FXML
    private Label l_MasterKeyWrong;

    private Presenter presenter;


    public void initialize() throws IOException {
        System.out.println("Methode initialize");



    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public String getPassword_box() {
        return password_box.getText();
    }

    public String getRepeatPassword_box() {
        return repeatPassword_box.getText();
    }

    public void btn_setMasterPassword(ActionEvent actionEvent) {
        System.out.println("Button gedrückt");

//        presenter.setMasterpassword();
        if (presenter.setMasterpassword()) {
            System.out.println("ja");
        } else {
            System.out.println("nein");
        }

    }


    public void test() {
        System.out.println("testMethode SetmasterPassword Controller");
    }

    public SetMasterPasswordController getController() {
        return this;
    }


    //    public void correctMasterKey(ActionEvent actionEvent) throws IOException {
//        if (equalsPassword(password_box.getText())) {
//            System.out.println("Passwort erfolgreich");
//            equalsPasswordwithList();
//        } else {
//            l_MasterKeyWrong.setText("Passwords not equals!");
//        }
//    }
//
//    private boolean equalsPasswordwithList() throws IOException {
//        String url = String.valueOf(this.getClass().getResource("/images/passwordliste.txt" ));
//
//        System.out.println(url);
//        File file = new File("/images/passwordliste.txt");
//
//        BufferedReader br = null;
//        br = new BufferedReader(new FileReader(file));
//        String zeile = null;
//        while ((zeile = br.readLine()) != null) {
//            System.out.println("Gelesene Zeile " + zeile);
//
//        }
//
//
//
//        return true;
//
//    }
//
//
//    private boolean equalsPassword(String password) {
//        if (password.equals(repeatPassword_box.getText())) {
//            return true;
//        }
//        return false;
//    }
}
