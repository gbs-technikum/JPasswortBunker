package jpasswortbunker.mgm.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SetMasterPasswordController {

    @FXML
    private PasswordField password_box;

    @FXML
    private PasswordField repeatPassword_box;

    @FXML
    private Label l_MasterKeyWrong;




    public void correctMasterKey(ActionEvent actionEvent) throws IOException {
        if (equalsPassword(password_box.getText())) {
            System.out.println("Passwort erfolgreich");
            equalsPasswordwithList();
        } else {
            l_MasterKeyWrong.setText("Passwords not equals!");
        }
    }

    private boolean equalsPasswordwithList() throws IOException {
        String url = String.valueOf(this.getClass().getResource("passwordliste.txt" ));

        System.out.println(url);
        File file = new File("passwordliste.txt");

        BufferedReader br = null;
        br = new BufferedReader(new FileReader(file));
        String zeile = null;
        while ((zeile = br.readLine()) != null) {
            System.out.println("Gelesene Zeile " + zeile);

        }



        return true;

    }


    private boolean equalsPassword(String password) {
        if (password.equals(repeatPassword_box.getText())) {
            return true;
        }
        return false;
    }
}
