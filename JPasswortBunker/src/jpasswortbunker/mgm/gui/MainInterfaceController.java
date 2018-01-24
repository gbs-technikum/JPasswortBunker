package jpasswortbunker.mgm.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainInterfaceController {

    @FXML
    private Label labelTest;
    private ResourceBundle bundle;
    private Locale locale;

    @FXML
    private Menu btn_File;

    @FXML
    private Menu btn_Edit;

    @FXML
    private Menu btn_Help;

    @FXML
    private Menu btn_Settings;

    @FXML
    private Menu btn_Language;




    public void newEntry(MouseEvent actionEvent) throws IOException {
        System.out.println("neuer Eintrag wurde erstellt");

        //neues Fenster im Vordergrund
        Stage stage2 = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("NewEntry.fxml"));
        Scene scene2 = new Scene(root2, 500, 400);
        stage2.setTitle("zweites Fenster");
        stage2.setScene(scene2);
        stage2.setAlwaysOnTop(true);
        stage2.show();

        //Neues Fenster, altes wird geschlossen
//        Parent home_page_parent = FXMLLoader.load(getClass().getResource("NewEntry.fxml"));
//        Scene home_page_scene = new Scene(home_page_parent);
//        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        app_stage.setScene(home_page_scene);
//        app_stage.show();


    }


    public void save(MouseEvent actionEvent) {
        System.out.println("Datenbank gespeichert");
    }

    public void test(ActionEvent actionEvent) {
        System.out.println("Test Button in toolbar");
    }


    public void btn_lang_en(ActionEvent actionEvent) {
        System.out.println("Englisch");
        loadLang("en");
    }

    public void btn_lang_de(ActionEvent actionEvent) {
        System.out.println("Deutsch");
        loadLang("de");
    }


    private void loadLang(String lang) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("sample.gui.lang", locale);
        labelTest.setText(bundle.getString("labelTest"));
        btn_File.setText(bundle.getString("btn_File"));
        btn_Settings.setText(bundle.getString("btn_Settings"));
        btn_Language.setText(bundle.getString("btn_Language"));
        btn_Edit.setText(bundle.getString("btn_Edit"));
        btn_Help.setText(bundle.getString("btn_Help"));

    }
}
