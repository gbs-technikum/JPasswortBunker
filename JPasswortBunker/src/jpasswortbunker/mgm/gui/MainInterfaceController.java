package jpasswortbunker.mgm.gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private JFXButton btn_finance, btn_social, btn_email, btn_network, btn_settings;

    @FXML
    private AnchorPane pane_finance, pane_social, pane_email, pane_network, pane_settings;

    public void test(ActionEvent actionEvent) {
        System.out.println("Test Button");
    }

    public void btn_finance(ActionEvent actionEvent) {
        System.out.println("test btn_finance");
        pane_finance.setVisible(true);
        pane_social.setVisible(false);
        pane_email.setVisible(false);
        pane_network.setVisible(false);
        pane_settings.setVisible(false);
    }

    public void btn_social(ActionEvent actionEvent) {
        System.out.println("test btn_social");
        pane_social.setVisible(true);
        pane_finance.setVisible(false);
        pane_email.setVisible(false);
        pane_network.setVisible(false);
        pane_settings.setVisible(false);
    }

    public void btn_email(ActionEvent actionEvent) {
        System.out.println("email");
        pane_email.setVisible(true);
        pane_social.setVisible(false);
        pane_finance.setVisible(false);
        pane_network.setVisible(false);
        pane_settings.setVisible(false);
    }

    public void btn_network(ActionEvent actionEvent) {
        System.out.println("network");
        pane_network.setVisible(true);
        pane_social.setVisible(false);
        pane_finance.setVisible(false);
        pane_email.setVisible(false);
        pane_settings.setVisible(false);
    }

    public void btn_settings(ActionEvent actionEvent) {
        System.out.println("settings");
        pane_settings.setVisible(true);
        pane_social.setVisible(false);
        pane_finance.setVisible(false);
        pane_email.setVisible(false);
        pane_network.setVisible(false);
    }


//    public void newEntry(MouseEvent actionEvent) throws IOException {
//        System.out.println("neuer Eintrag wurde erstellt");

//        //neues Fenster im Vordergrund
//        Stage stage2 = new Stage();
//        Parent root2 = FXMLLoader.load(getClass().getResource("NewEntry.fxml"));
//        Scene scene2 = new Scene(root2, 500, 400);
//        stage2.setTitle("zweites Fenster");
//        stage2.setScene(scene2);
//        stage2.setAlwaysOnTop(true);
//        stage2.show();

        //Neues Fenster, altes wird geschlossen
//        Parent home_page_parent = FXMLLoader.load(getClass().getResource("NewEntry.fxml"));
//        Scene home_page_scene = new Scene(home_page_parent);
//        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        app_stage.setScene(home_page_scene);
//        app_stage.show();

//    }

//
//    public void save(MouseEvent actionEvent) {
//        System.out.println("Datenbank gespeichert");
//    }
//
//

//
//    public void btn_lang_en(ActionEvent actionEvent) {
//        System.out.println("Englisch");
//        loadLang("en");
//    }
//
//    public void btn_lang_de(ActionEvent actionEvent) {
//        System.out.println("Deutsch");
//        loadLang("de");
//    }
//
//
//    private void loadLang(String lang) {
//        locale = new Locale(lang);
//        bundle = ResourceBundle.getBundle("sample.gui.lang", locale);
//        labelTest.setText(bundle.getString("labelTest"));
//        btn_File.setText(bundle.getString("btn_File"));
//        btn_Settings.setText(bundle.getString("btn_Settings"));
//        btn_Language.setText(bundle.getString("btn_Language"));
//        btn_Edit.setText(bundle.getString("btn_Edit"));
//        btn_Help.setText(bundle.getString("btn_Help"));
//
//    }

}
