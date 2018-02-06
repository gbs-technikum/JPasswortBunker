package jpasswortbunker.mgm.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import jpasswortbunker.mgm.entry.Entry;
import jpasswortbunker.mgm.entry.EntryDB;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainInterfaceController implements Initializable {


    public EntryDB entryDB = new EntryDB();

    @FXML
    private Label labelTest;
    private ResourceBundle bundle;
    private Locale locale;

    @FXML
    private JFXButton btn_finance, btn_social, btn_email, btn_network, btn_settings, btn_newEntry;

    @FXML
    private ImageView btn_logo;

    @FXML
    private AnchorPane pane_start, pane_finance, pane_social, pane_email, pane_network, pane_settings;

    @FXML
    private TableColumn columnID;

    @FXML
    private JFXTreeTableView<Entry> treeView;

    //Spalten für Tabelle werden angelegt, kann für die Libery jfoenix nicht über Scenebuilder gemacht werden
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        //Spalte ID
//        JFXTreeTableColumn<Entry, Integer> idColum = new JFXTreeTableColumn<>("ID");
//        idColum.setPrefWidth(150);
//        idColum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Entry, Integer>, ObservableValue<Integer>>() {
//            @Override
//            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<Entry, Integer> param) {
//                return param.getValue().getValue().categorieIDProperty();
//            }
//        });


        //Spalte Title
        JFXTreeTableColumn<Entry, String> titleName = new JFXTreeTableColumn<>("Title");
        titleName.setPrefWidth(150);
        titleName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Entry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Entry, String> param) {
                return param.getValue().getValue().titleProperty();
            }
        });

        //Spalte Username
        JFXTreeTableColumn<Entry, String> usernameCol = new JFXTreeTableColumn<>("Username");
        usernameCol.setPrefWidth(150);
        usernameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Entry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Entry, String> param) {
                return param.getValue().getValue().usernameProperty();
            }
        });

        //Spalte Password
        JFXTreeTableColumn<Entry, String> passwordCol = new JFXTreeTableColumn<>("Password");
        passwordCol.setPrefWidth(150);
        passwordCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Entry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Entry, String> param) {
                return param.getValue().getValue().passwordProperty();
            }
        });

        //Spalte URL
        JFXTreeTableColumn<Entry, String> urlCol = new JFXTreeTableColumn<>("URL");
        urlCol.setPrefWidth(150);
        urlCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Entry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Entry, String> param) {
                return param.getValue().getValue().urlProperty();
            }
        });

        //Spalte Description
        JFXTreeTableColumn<Entry, String> desCol = new JFXTreeTableColumn<>("Description");
        desCol.setPrefWidth(150);
        desCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Entry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Entry, String> param) {
                return param.getValue().getValue().descriptionProperty();
            }
        });

        //Temp Einträge zum testen
        ObservableList<Entry> entrys = FXCollections.observableArrayList();
        entrys.add(new Entry("neuer Titel", "neuer Username", "mein Passwort", "derLink", "Beschreibung", 2));
        entrys.add(new Entry("neuer Titel", "neuer Username", "mein Passwort", "derLink", "Beschreibung", 2));
        entrys.add(new Entry("neuer Titel", "neuer Username", "mein Passwort", "derLink", "Beschreibung", 2));
        entrys.add(new Entry("neuer Titel", "neuer Username", "mein Passwort", "derLink", "Beschreibung", 2));

        //Inhalte werden in die Tabelle geschrieben
        final TreeItem<Entry> root = new RecursiveTreeItem<Entry>(entrys, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(titleName, usernameCol, passwordCol, urlCol, desCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }



    //#######################################################

    public void test(ActionEvent actionEvent) {
        System.out.println("Test Button");

    }

    //Button Kategorie_Finanzen
    public void btn_finance(ActionEvent actionEvent) {
        System.out.println("test btn_finance");
        pane_finance.setVisible(true);
        pane_start.setVisible(false);
        pane_social.setVisible(false);
        pane_email.setVisible(false);
        pane_network.setVisible(false);
        pane_settings.setVisible(false);
    }

    //Button Kategorie_Social
    public void btn_social(ActionEvent actionEvent) {
        System.out.println("test btn_social");
        pane_social.setVisible(true);
        pane_start.setVisible(false);
        pane_finance.setVisible(false);
        pane_email.setVisible(false);
        pane_network.setVisible(false);
        pane_settings.setVisible(false);
    }

    //Button Kategorie_E-Mail
    public void btn_email(ActionEvent actionEvent) {
        System.out.println("email");
        pane_email.setVisible(true);
        pane_start.setVisible(false);
        pane_social.setVisible(false);
        pane_finance.setVisible(false);
        pane_network.setVisible(false);
        pane_settings.setVisible(false);
    }


    //Button Kategorie_Netzwerk
    public void btn_network(ActionEvent actionEvent) {
        System.out.println("network");
        pane_network.setVisible(true);
        pane_start.setVisible(false);
        pane_social.setVisible(false);
        pane_finance.setVisible(false);
        pane_email.setVisible(false);
        pane_settings.setVisible(false);
    }


    //Button für die Einstellungen
    public void btn_settings(ActionEvent actionEvent) {
        System.out.println("settings");
        pane_settings.setVisible(true);
        pane_start.setVisible(false);
        pane_social.setVisible(false);
        pane_finance.setVisible(false);
        pane_email.setVisible(false);
        pane_network.setVisible(false);
    }

    //Button Logo zeit alle Einträge an
    public void btn_logo(MouseEvent mouseEvent) {
        System.out.println("start");
        pane_start.setVisible(true);
        pane_settings.setVisible(false);
        pane_social.setVisible(false);
        pane_finance.setVisible(false);
        pane_email.setVisible(false);
        pane_network.setVisible(false);

    }


    //Button für neuen Eintrag, startet eine neue Scene
    public void btn_newEntry() {
        Stage stageNewEntry = new Stage();
        Parent parentNewEntry = null;
        try {
            parentNewEntry = FXMLLoader.load(getClass().getResource("NewEntry.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene2 = new Scene(parentNewEntry, 400, 400);
        stageNewEntry.setTitle("zweites Fenster");
        stageNewEntry.setScene(scene2);
        stageNewEntry.setAlwaysOnTop(true);
        stageNewEntry.show();
    }






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
