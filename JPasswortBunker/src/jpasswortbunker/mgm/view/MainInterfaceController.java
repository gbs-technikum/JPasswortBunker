package jpasswortbunker.mgm.view;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import jpasswortbunker.mgm.view.EditEntryController;
import jpasswortbunker.mgm.presenter.EntryProperty;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
//http://code.makery.ch/blog/javafx-dialogs-official/ -> ConfirmDialog

public class MainInterfaceController implements Initializable {

    @FXML
    private Label labelTest;
    private ResourceBundle bundle;
    private Locale locale;

    @FXML
    private JFXButton btn_finance, btn_social, btn_email, btn_network, btn_settings, btn_newEntry, btn_recycle;

    @FXML
    private ImageView btn_logo;

    @FXML
    private AnchorPane pane_entrys, pane_settings,  pane_recycle;


    @FXML
    private JFXTreeTableView<EntryProperty> treeView;

    @FXML
    private JFXTreeTableView<EntryProperty> tableView_recylce;



    @FXML
    private JFXTextField textField_Search;

    @FXML
    private AnchorPane mainAnchorPane;

    private static Stage stageMainInterfaceController, stageSetMasterPassword, stageLogin, stageNewEntry;
    private ContextMenu contextMenu;



    private PresenterMain presenter = new PresenterMain(this);

    public MainInterfaceController() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Ruft Methode auf und ruft jeweiliges Fenster auf
            checkIfMasterPasswortExistsInDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //bekommt die Stage der Main methode
        stageMainInterfaceController = Testklasse.getPrimaryStage();
    }

    public void updateView() {
        stageMainInterfaceController.show();
        fillTreeView();
        fillRecycleTable();
    }

       public void fillTreeView() {

        //Spalte Title
        JFXTreeTableColumn<EntryProperty, String> titleName = new JFXTreeTableColumn<>("Title");
        titleName.setPrefWidth(120);
        titleName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().titleProperty();
            }
        });

        //Spalte Username
        JFXTreeTableColumn<EntryProperty, String> usernameCol = new JFXTreeTableColumn<>("Username");
        usernameCol.setPrefWidth(200);
        usernameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().usernameProperty();
            }
        });

        //Spalte URL
        JFXTreeTableColumn<EntryProperty, String> urlCol = new JFXTreeTableColumn<>("URL");
        urlCol.setPrefWidth(190);
        urlCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().urlProperty();
            }
        });

        //Spalte Description
        JFXTreeTableColumn<EntryProperty, String> desCol = new JFXTreeTableColumn<>("Description");
        desCol.setPrefWidth(180);
        desCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().descriptionProperty();
            }
        });

        //Inhalte werden in die Tabelle geschrieben
        final TreeItem<EntryProperty> root = new RecursiveTreeItem<EntryProperty>(presenter.getEntryPropertiesList(), RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(titleName, usernameCol, urlCol, desCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        //ruft Methode auf und baut ContextMenu zusammen
        buildContextMenu();



        //Eventhandling für die Elemente
        treeView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ee) {
                if (ee.isPrimaryButtonDown() && ee.getClickCount() == 2) {

                    editEntryScene();
                }
                if (ee.isSecondaryButtonDown()) {
                    contextMenu.show(treeView, ee.getScreenX(), ee.getScreenY());
                }
            }
        });
    }


    //Suchfunktion in Suchleiste Suche nach: Title und Username
    public void searchFunction() {
        textField_Search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
                    @Override
                    public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                        Boolean flag = entryTreeItem.getValue().titleProperty().getValue().toLowerCase().contains(newValue.toLowerCase())||
                                entryTreeItem.getValue().usernameProperty().getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });
    }

    /**Ruft Methode in Model auf um zu überprüfen, ob Masterpasswort gesetzt wurde
     * Return Value:
     * true -> LoginScreen
     * false -> SetMasterPassword
     */
    private void checkIfMasterPasswortExistsInDB() throws IOException, SQLException {
        if (presenter.checkIfMasterPasswortExistsInDB()) {
            System.out.println("gesetzt");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            Parent parent = fxmlLoader.load();
            LoginScreenController loginScreenController = fxmlLoader.<LoginScreenController>getController();
            loginScreenController.setPresenter(presenter);
            this.stageLogin = new Stage();
            stageLogin.setTitle("LoginScreen");
            stageLogin.setScene(new Scene(parent, 500, 400));
            stageLogin.setAlwaysOnTop(true);
            stageLogin.show();
        } else {
            System.out.println("Nicht gesetzt");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetMasterPassword.fxml"));
            Parent parent = fxmlLoader.load();
            SetMasterPasswordController loginScreenController = fxmlLoader.<SetMasterPasswordController>getController();
            loginScreenController.setPresenter(presenter);
            this.stageLogin = new Stage();
            stageLogin.setTitle("SetMasterPassword");
            stageLogin.setScene(new Scene(parent, 500, 400));
            stageLogin.setAlwaysOnTop(true);
            stageLogin.show();
        }
    }

    /**
     *Button erstellt neues Fenster um einen neuen Eintrag zu erstellen
     */
    public void btn_newEntry() throws IOException {
        System.out.println("Neuer Eintrag");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewEntry.fxml"));
        Parent parent = fxmlLoader.load();
        NewEntryController newEntryController = fxmlLoader.<NewEntryController>getController();
        newEntryController.setPresenter(presenter);
        this.stageNewEntry = new Stage();
        stageNewEntry.setTitle("New Entry");
        stageNewEntry.setScene(new Scene(parent, 400, 400));
        stageNewEntry.setAlwaysOnTop(true);
        stageNewEntry.show();
    }


    //Button Logo zeit alle Einträge an und setzt Suchfilter bzw Kategorie zurück
    public void btn_logo(MouseEvent mouseEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                return true;
            }
        });
    }

    //Button Kategorie_Finanzen
    public void btn_finance(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(0);
                return flag;
            }
        });
    }

    //Button Kategorie_Social
    public void btn_social(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(1);
                return flag;
            }
        });
    }


    //Button Kategorie_E-Mail
    public void btn_email(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(2);
                return flag;
            }
        });
    }


    //Button Kategorie_Netzwerk
    public void btn_network(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(3);
                return flag;
            }
        });
    }


    //Button für den Müll
    public void btn_recycle(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(false);
        pane_recycle.setVisible(true);
        textField_Search.clear();

    }


    //Button für die Einstellungen
    public void btn_settings(ActionEvent actionEvent) {
        pane_settings.setVisible(true);
        pane_entrys.setVisible(false);
        pane_recycle.setVisible(false);
        textField_Search.clear();
    }


    public void btn_newMasterPassword() {
        System.out.println("Buttontest: change Master Password");
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(presenter);

    }

    /**
     * private void buildContextMenu()
     * Baut Context Menu zusammen
     */
    private void buildContextMenu() {
        //ToDo eventuell noch mal anpassen wenn Zeit
        contextMenu = null;
        contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Delete");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Delete Confirmation");
                alert.setContentText("Are you realy sure, that you want delete the Entry ");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    try {
                        presenter.removeEntry(treeView.getSelectionModel().getSelectedItem().getValue());
                        System.out.println();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    // ... user chose CANCEL or closed the dialog

                }
            }
        });
        contextMenu.getItems().add(item1);
        MenuItem item2 = new MenuItem("Edit");
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                editEntryScene();
            }
        });
        contextMenu.getItems().add(item2);
    }

    private void editEntryScene() {
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
        editEntryController.setPresenter(presenter);


        Parent parentEditEntry = loader.getRoot();
        Stage stageEditEntry = new Stage();
        Scene sceneEditentry = new Scene(parentEditEntry, 400, 400);
        stageEditEntry.setTitle("Edit your EntryProperty");
        stageEditEntry.setScene(sceneEditentry);
        stageEditEntry.show();
        stageEditEntry.getIcons().add(new Image(String.valueOf(this.getClass().getResource("images/logo.png"))));
    }



    //TODO: 14.03.2018 Liste richtig laden in den Mülleimer
    public void fillRecycleTable() {

        //Spalte Title
        JFXTreeTableColumn<EntryProperty, String> titleName = new JFXTreeTableColumn<>("Title");
        titleName.setPrefWidth(100);
        titleName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().titleProperty();
            }
        });

        //Spalte Username
        JFXTreeTableColumn<EntryProperty, String> usernameCol = new JFXTreeTableColumn<>("Username");
        usernameCol.setPrefWidth(100);
        usernameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().usernameProperty();
            }
        });

        //Spalte URL
        JFXTreeTableColumn<EntryProperty, String> urlCol = new JFXTreeTableColumn<>("URL");
        urlCol.setPrefWidth(150);
        urlCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().urlProperty();
            }
        });

        //Spalte Description
        JFXTreeTableColumn<EntryProperty, String> desCol = new JFXTreeTableColumn<>("Description");
        desCol.setPrefWidth(150);
        desCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().descriptionProperty();
            }
        });

        //Inhalte werden in die Tabelle geschrieben
        final TreeItem<EntryProperty> root1 = new RecursiveTreeItem<EntryProperty>(presenter.getEntryPropertiesListRecycle(), RecursiveTreeObject::getChildren);
        tableView_recylce.getColumns().setAll(titleName, usernameCol, urlCol, desCol);
        tableView_recylce.setRoot(root1);
        tableView_recylce.setShowRoot(false);
        //ruft Methode auf und baut ContextMenu zusammen
        buildContextMenu();



        //Eventhandling für die Elemente
        tableView_recylce.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ee) {
                if (ee.isPrimaryButtonDown() && ee.getClickCount() == 2) {

                    editEntryScene();
                }
                if (ee.isSecondaryButtonDown()) {
                    contextMenu.show(treeView, ee.getScreenX(), ee.getScreenY());
                }
            }
        });
    }


    //Suchfunktion in Suchleiste Suche nach: Title und Username
    public void searchFunction1() {
        textField_Search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tableView_recylce.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
                    @Override
                    public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                        Boolean flag = entryTreeItem.getValue().titleProperty().getValue().toLowerCase().contains(newValue.toLowerCase())||
                                entryTreeItem.getValue().usernameProperty().getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });
    }



}
