package jpasswortbunker.mgm.gui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import jpasswortbunker.mgm.entry.Entry2;
import jpasswortbunker.mgm.entry.EntryDB;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class MainInterfaceController implements Initializable {

    @FXML
    private Label labelTest;
    private ResourceBundle bundle;
    private Locale locale;

    @FXML
    private JFXButton btn_finance, btn_social, btn_email, btn_network, btn_settings, btn_newEntry;

    @FXML
    private ImageView btn_logo;

    @FXML
    private AnchorPane pane_entrys, pane_settings;

    @FXML
    private TableColumn columnID;

    @FXML
    private JFXTreeTableView<Entry> treeView;

    @FXML
    private JFXTextField textField_Search;

    public ObservableList<Entry> entrys = FXCollections.observableArrayList();

    //Spalten für Tabelle werden angelegt, kann für die Libery jfoenix nicht über Scenebuilder gemacht werden
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        //Spalte Title
        JFXTreeTableColumn<Entry, String> titleName = new JFXTreeTableColumn<>("Title");
        titleName.setPrefWidth(100);
        titleName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Entry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Entry, String> param) {
                return param.getValue().getValue().titleProperty();
            }
        });

        //Spalte Username
        JFXTreeTableColumn<Entry, String> usernameCol = new JFXTreeTableColumn<>("Username");
        usernameCol.setPrefWidth(100);
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

        JFXTreeTableColumn<Entry2, String> test = new JFXTreeTableColumn<>("test");
        test.setPrefWidth(40);
        test.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Entry2, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Entry2, String> param) {
                return null;
            }
        });
        //Temp Einträge zum testen

        entrys.add(new Entry("Hallo", "neuer alter", "mein Passwort", "Link", "Beschreibung", 1));
        entrys.add(new Entry("Haus", "neuer Adi", "mein Passwort", "dieLink", "was auch immer", 1));
        entrys.add(new Entry("Nix", "niemand", "mein Passwort", "desLink", "hier könnte deine Werbung stehen", 3));
        entrys.add(new Entry("Netflix", "GeilSerien", "mein Passwort", "derLink", "leer", 3));
        entrys.add(new Entry("Yotube", "Moneyboy", "mein Passwort", "derLink", "leer", 0));
        entrys.add(new Entry("Facebook", "ka", "mein Passwort", "derLink", "leer", 2));
        entrys.add(new Entry("Schule", "Musterman", "mein Passwort", "derLink", "leer", 0));
        entrys.add(new Entry("Test", "nobody", "mein Passwort", "derLink", "leer", 1));
        entrys.add(new Entry("Hallo", "neuer alter", "mein Passwort", "Link", "Beschreibung", 1));
        entrys.add(new Entry("Haus", "neuer Adi", "mein Passwort", "dieLink", "was auch immer", 1));
        entrys.add(new Entry("Nix", "niemand", "mein Passwort", "desLink", "hier könnte deine Werbung stehen", 3));
        entrys.add(new Entry("Netflix", "GeilSerien", "mein Passwort", "derLink", "leer", 3));
        entrys.add(new Entry("Yotube", "Moneyboy", "mein Passwort", "derLink", "leer", 0));
        entrys.add(new Entry("Facebook", "ka", "mein Passwort", "derLink", "leer", 2));
        entrys.add(new Entry("Schule", "Musterman", "mein Passwort", "derLink", "leer", 0));
        entrys.add(new Entry("Test", "nobody", "mein Passwort", "derLink", "leer", 1));
        entrys.add(new Entry("Hallo", "neuer alter", "mein Passwort", "Link", "Beschreibung", 1));
        entrys.add(new Entry("Haus", "neuer Adi", "mein Passwort", "dieLink", "was auch immer", 1));
        entrys.add(new Entry("Nix", "niemand", "mein Passwort", "desLink", "hier könnte deine Werbung stehen", 3));
        entrys.add(new Entry("Netflix", "GeilSerien", "mein Passwort", "derLink", "leer", 3));
        entrys.add(new Entry("Yotube", "Moneyboy", "mein Passwort", "derLink", "leer", 0));
        entrys.add(new Entry("Facebook", "ka", "mein Passwort", "derLink", "leer", 2));
        entrys.add(new Entry("Schule", "Musterman", "mein Passwort", "derLink", "leer", 0));




        //Inhalte werden in die Tabelle geschrieben
        final TreeItem<Entry> root = new RecursiveTreeItem<Entry>(entrys, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(titleName, usernameCol, passwordCol, urlCol, desCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);


        //Eventhandling für die Elemente
        treeView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ee) {
                if (ee.isPrimaryButtonDown() && ee.getClickCount() == 2) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEntry.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //übergibt an EditEntryController den ausgewählten Eintrag
                    EditEntryController editEntryController = loader.getController();
                    //Ausgewähltes Element treeView.getSelectionModel().getSelectedItem()
                    editEntryController.setEntry(treeView.getSelectionModel().getSelectedItem());

                    Parent parentEditEntry = loader.getRoot();
                    Stage stageEditEntry = new Stage();
                    Scene sceneEditentry = new Scene(parentEditEntry, 400, 400);
                    stageEditEntry.setTitle("Edit your Entry");
                    stageEditEntry.setScene(sceneEditentry);
                    stageEditEntry.show();
                }
            }
        });

    }

    //#######################################################

    //Suchfunktion in Suchleiste Suche nach: Title und Username
    public void searchFunction() {
        textField_Search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<Entry>>() {
                    @Override
                    public boolean test(TreeItem<Entry> entryTreeItem) {
                        Boolean flag = entryTreeItem.getValue().titleProperty().getValue().toLowerCase().contains(newValue.toLowerCase())||
                                entryTreeItem.getValue().usernameProperty().getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });
    }

    //Button Logo zeit alle Einträge an und setzt Suchfilter bzw Kategorie zurück
    public void btn_logo(MouseEvent mouseEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<Entry>>() {
            @Override
            public boolean test(TreeItem<Entry> entryTreeItem) {
                return true;
            }
        });
    }

    //Button Kategorie_Finanzen
    public void btn_finance(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<Entry>>() {
            @Override
            public boolean test(TreeItem<Entry> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categorieIDProperty().getValue().equals(0);
                return flag;
            }
        });
    }

    //Button Kategorie_Social
    public void btn_social(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<Entry>>() {
            @Override
            public boolean test(TreeItem<Entry> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categorieIDProperty().getValue().equals(1);
                return flag;
                }
        });
    }


    //Button Kategorie_E-Mail
    public void btn_email(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<Entry>>() {
            @Override
            public boolean test(TreeItem<Entry> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categorieIDProperty().getValue().equals(2);
                return flag;
            }
        });
    }


    //Button Kategorie_Netzwerk
    public void btn_network(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<Entry>>() {
            @Override
            public boolean test(TreeItem<Entry> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categorieIDProperty().getValue().equals(3);
                return flag;
            }
        });
    }


    //Button für die Einstellungen
    public void btn_settings(ActionEvent actionEvent) {
        pane_settings.setVisible(true);
        pane_entrys.setVisible(false);
        textField_Search.clear();
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
