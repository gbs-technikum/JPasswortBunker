package jpasswortbunker.mgm.view;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.util.Callback;

import javafx.util.Duration;
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


    private ResourceBundle bundle;
    private Locale locale;

    @FXML
    private JFXButton btn_finance, btn_social, btn_email, btn_network, btn_settings, btn_newEntry, btn_recycle,
            btn_settings_timeoutClipboard, btn_settings_numberBackupEntriesOk, btn_settings_lengthRandomPasswords,
            btn_settings_language;

    @FXML
    private ImageView btn_logo;

    @FXML
    private JFXComboBox<Label> comboBox_settings_language;

    @FXML
    private AnchorPane pane_entrys, pane_settings, pane_recycle;

    @FXML
    private JFXTreeTableView<EntryProperty> treeView;

    @FXML
    private JFXTreeTableView<EntryProperty> tableView_recylce;

    @FXML
    private JFXTextField textField_Search, textField_settings_timeoutClipboard, textField_settings_backupEntries,
            textField_settings_lengthRandomPasswords, textField_settings_saveStatus, textField_settings_numberBackupEntries,
            textField_settings_lengthRandomPasswordsText, textField_settings_timeoutClipboardText,
            textField_settings_saveStatusText, textField_settings_language;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Menu menu_File, menu_Edit, menu_Help;

    @FXML
    private MenuItem menuItem_NewMasterpassword, menuItem_NewEntry, menuItem_Help, menuItem_About;

    private static Stage stageMainInterfaceController, stageSetMasterPassword, stageLogin, stageNewEntry;
    private ContextMenu contextMenu, contextMenuRecycleBin;
    private Alert alert;


    private PresenterMain presenter = new PresenterMain(this);


    public MainInterfaceController() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            bundle = presenter.getLangBundle();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //loadLang(presenter.getLanguage());
        try {
            //Ruft Methode auf und ruft jeweiliges Fenster auf
            checkIfMasterPasswortExistsInDB();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //bekommt die Stage der Main methode um diese später sichtbar zu schalten
        stageMainInterfaceController = Testklasse.getPrimaryStage();
    }

    public void loadView() {
        //setLang();
        fillRecycleTable();
        fillTreeView();
        setLang();
        stageMainInterfaceController.show();
        fillComboboxLangauge();
    }

    public void updateRecycleView() {
        fillRecycleTable();
    }

    //Hinzugefügt von Wagenhuber: Wird nach dem Hinzufügen / Updaten eines neuen / bestehenden Entries ausgeführt um die View zu aktualisieren
    public void updateView() {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        textField_Search.setVisible(true);
        stageMainInterfaceController.show();
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(presenter.getCategoryChoosenForLastNewEntry());
                return flag;
            }
        });

    }


    public void fillTreeView() {

        //Spalte Title
        JFXTreeTableColumn<EntryProperty, String> titleName = new JFXTreeTableColumn<>(bundle.getString("tableColumn.title"));
        titleName.setPrefWidth(120);
        titleName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().titleProperty();
            }
        });

        //Spalte Username
        JFXTreeTableColumn<EntryProperty, String> usernameCol = new JFXTreeTableColumn<>(bundle.getString("tableColumn.username"));
        usernameCol.setPrefWidth(200);
        usernameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().usernameProperty();
            }
        });

        //Spalte URL
        JFXTreeTableColumn<EntryProperty, String> urlCol = new JFXTreeTableColumn<>(bundle.getString("tableColumn.url"));
        urlCol.setPrefWidth(190);
        urlCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().urlProperty();
            }
        });

        //Spalte Description
        JFXTreeTableColumn<EntryProperty, String> desCol = new JFXTreeTableColumn<>(bundle.getString("tableColumn.description"));
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
        treeView.sort();
        treeView.setShowRoot(false);
        //ruft Methode auf und baut ContextMenu zusammen
        buildContextMenu();


        //Eventhandling für die Elemente
        treeView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ee) {
                if (ee.isPrimaryButtonDown() && ee.getClickCount() == 2) {

                    try {
                        editEntryScene(treeView.getSelectionModel().getSelectedItem());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }
                }
                if (ee.isSecondaryButtonDown()) {
                    contextMenu.show(treeView, ee.getScreenX(), ee.getScreenY());
                }
            }
        });
    }




    /**
     * public void searchFunction()
     * Suchfunktion in Suchleiste Suche nach: Title und Username
     */
    public void searchFunction() {
        textField_Search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
                    @Override
                    public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                        Boolean flag = entryTreeItem.getValue().titleProperty().getValue().toLowerCase().contains(newValue.toLowerCase()) ||
                                entryTreeItem.getValue().usernameProperty().getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });
    }

    /**
     * Ruft Methode in Model auf um zu überprüfen, ob Masterpasswort gesetzt wurde
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
            stageLogin.setTitle(bundle.getString("login.title"));
            stageLogin.setScene(new Scene(parent, 500, 400));
            stageLogin.setAlwaysOnTop(true);
            stageLogin.setResizable(false);
            stageLogin.getIcons().add(new Image(String.valueOf(this.getClass().getResource("images/logo.png"))));
            stageLogin.initModality(Modality.APPLICATION_MODAL);
            stageLogin.show();
        } else {
            System.out.println("Nicht gesetzt");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetMasterPassword.fxml"));
            Parent parent = fxmlLoader.load();
            SetMasterPasswordController loginScreenController = fxmlLoader.<SetMasterPasswordController>getController();
            loginScreenController.setPresenter(presenter);
            this.stageLogin = new Stage();
            stageLogin.setTitle("SetMasterPassword");
            stageLogin.setScene(new Scene(parent, 390, 310));
            stageLogin.setAlwaysOnTop(true);
            stageLogin.setResizable(false);
            stageLogin.initModality(Modality.APPLICATION_MODAL);
            stageLogin.getIcons().add(new Image(String.valueOf(this.getClass().getResource("images/logo.png"))));
            stageLogin.show();
        }
    }

    /**
     * Button erstellt neues Fenster um einen neuen Eintrag zu erstellen
     */
    public void btn_newEntry() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewEntry.fxml"));
        Parent parent = fxmlLoader.load();
        NewEntryController newEntryController = fxmlLoader.<NewEntryController>getController();
        newEntryController.setPresenter(presenter);
        newEntryController.fillComboBox();
        this.stageNewEntry = new Stage();
        stageNewEntry.setTitle(bundle.getString("entryNew.title"));
        stageNewEntry.setScene(new Scene(parent, 400, 400));
        stageNewEntry.setAlwaysOnTop(true);
        stageNewEntry.setResizable(false);
        stageNewEntry.initModality(Modality.APPLICATION_MODAL);
        stageNewEntry.getIcons().add(new Image(String.valueOf(this.getClass().getResource("images/logo.png"))));
        stageNewEntry.show();
    }


    //Button Logo zeit alle Einträge an und setzt Suchfilter bzw Kategorie zurück
    public void btn_logo(MouseEvent mouseEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        textField_Search.setVisible(true);
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                return true;
            }
        });
    }

    //Button Kategorie_Finanzen
    public void btn_finance(ActionEvent actionEvent) {

        //Auskommentiert durch Wagenhuber am 24.3.18 bzgl. Ansicht aktualisiert nicht
        //loadView();
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        textField_Search.setVisible(true);
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(1);
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
        textField_Search.setVisible(true);
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(2);
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
        textField_Search.setVisible(true);
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(3);
                return flag;
            }
        });
    }


    /**
     * public void btn_network(ActionEvent actionEvent)
     * Wechselt zur Network Kategorie
     */
    public void btn_network(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(true);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        textField_Search.setVisible(true);
        treeView.setPredicate(new Predicate<TreeItem<EntryProperty>>() {
            @Override
            public boolean test(TreeItem<EntryProperty> entryTreeItem) {
                Boolean flag = entryTreeItem.getValue().categoryIDProperty().getValue().equals(4);
                return flag;
            }
        });
    }


    /**
     * public void btn_recycle(ActionEvent actionEvent)
     * Wechselt zur Recycle Pane um sich die gelöschten Einträge anzeigen zu lassen
     */
    public void btn_recycle(ActionEvent actionEvent) {
        pane_settings.setVisible(false);
        pane_entrys.setVisible(false);
        pane_recycle.setVisible(true);
        textField_Search.clear();
        textField_Search.setVisible(false);
    }


    //Button für die Einstellungen
    public void btn_settings(ActionEvent actionEvent) {
        pane_settings.setVisible(true);
        pane_entrys.setVisible(false);
        pane_recycle.setVisible(false);
        textField_Search.clear();
        textField_Search.setVisible(false);

        //Hinzugefügt durch Wagenhuber: Textfelder für Settings
        textField_settings_backupEntries.setText(presenter.getTextField_settings_numberBackupEntries());
        textField_settings_lengthRandomPasswords.setText(presenter.getTextField_settings_lengthRandomPasswords());
        textField_settings_timeoutClipboard.setText(presenter.getTextField_settings_timeoutClipboard());
    }


    /**
     * public void btn_newMasterPassword()
     * Ruft einen neuen Dialog auf, um das MasterPasswort zu ändern
     */
    public void btn_newMasterPassword() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(presenter);
    }

    public void btn_lang_en(ActionEvent actionEvent) throws SQLException {
        System.out.println("Englisch");
        bundle = presenter.setLanguage("en");
        setLang();
    }

    public void btn_lang_de(ActionEvent actionEvent) throws SQLException {
        System.out.println("Deutsch");
        bundle = presenter.setLanguage("de");
        setLang();

    }

    public void btn_about(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("title.about"));
        alert.setHeaderText("jPasswortBunker");
        alert.setContentText(bundle.getString("text.about"));
        // Get the Stage.
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();


        // Add a custom icon.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(this.getClass().getResource("images/logo.png").toString()));
        alert.setGraphic(new ImageView(this.getClass().getResource("images/logo.png").toString()));
        alert.showAndWait();
    }

    public void btn_help(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("title.help"));
        alert.setHeaderText("jPasswortBunker");
        alert.setContentText(bundle.getString("text.help"));
        // Get the Stage.
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        // Add a custom icon.
        stage.getIcons().add(new Image(this.getClass().getResource("images/logo.png").toString()));
        stage.initModality(Modality.APPLICATION_MODAL);
        alert.setGraphic(new ImageView(this.getClass().getResource("images/logo.png").toString()));
        alert.showAndWait();
    }

    /**
     * private void loadLang(String lang)
     * Bekommt als Paramter das jeweilige Landeskuerzel
     * setzt die jeweiligen Felder dann neu mit der neuen Sprache
     */
    private void loadLang(String lang) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("jpasswortbunker.mgm.view.bundles.LangBundle", locale);

    }

    public void setLang() {
        btn_finance.setText(bundle.getString("button.finance"));
        btn_social.setText(bundle.getString("button.social"));
        btn_email.setText(bundle.getString("button.email"));
        btn_network.setText(bundle.getString("button.network"));
        btn_recycle.setText(bundle.getString("button.recycle"));
        btn_settings.setText(bundle.getString("button.settings"));
        btn_newEntry.setText(bundle.getString("button.newEntry"));
        textField_Search.setPromptText(bundle.getString("promptText.search"));
        menu_File.setText(bundle.getString("menu.file"));
        menu_Edit.setText(bundle.getString("menu.edit"));
        menu_Help.setText(bundle.getString("menu.help"));
        menuItem_NewMasterpassword.setText(bundle.getString("menuItem.newMasterPassword"));
        menuItem_NewEntry.setText(bundle.getString("menuItem.newEntry"));
        menuItem_Help.setText(bundle.getString("menuItem.help"));
        menuItem_About.setText(bundle.getString("menuItem.about"));
        textField_settings_numberBackupEntries.setText(bundle.getString("textField.settings.numberBackupEntries"));
        textField_settings_lengthRandomPasswordsText.setText(bundle.getString("textField.settings.lenghtRandomPasswordText"));
        textField_settings_timeoutClipboardText.setText(bundle.getString("textField.settings.timeoutClipboardText"));
        textField_settings_saveStatusText.setText(bundle.getString("textField.settings.saveSatusText"));
        textField_settings_language.setText(bundle.getString("textField.settings.language"));
        textField_settings_saveStatus.setText(bundle.getString("textField.settings.saveStatus"));
        btn_settings_numberBackupEntriesOk.setText(bundle.getString("button.settings.numberBackupEntries"));
        btn_settings_timeoutClipboard.setText(bundle.getString("button.settings.timeoutClipboard"));
        btn_settings_lengthRandomPasswords.setText(bundle.getString("button.settings.lengthRandomPassword"));
        btn_settings_language.setText(bundle.getString("button.settings.setLanguage"));
        contextMenu.getItems().get(0).setText(bundle.getString("contextMenu.delete"));
        contextMenu.getItems().get(1).setText(bundle.getString("contextMenu.edit"));
        contextMenu.getItems().get(2).setText(bundle.getString("contextMenu.copyPassword"));
        treeView.getColumns().get(0).setText(bundle.getString("tableColumn.title"));
        treeView.getColumns().get(1).setText(bundle.getString("tableColumn.username"));
        treeView.getColumns().get(2).setText(bundle.getString("tableColumn.url"));
        treeView.getColumns().get(3).setText(bundle.getString("tableColumn.description"));
    }

    /**
     * private void buildContextMenu()
     * Baut Context Menu zusammen
     */
    private void buildContextMenu() {
        //ToDo eventuell noch mal anpassen wenn Zeit
        contextMenu = null;
        contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem(bundle.getString("contextMenu.delete"));
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(bundle.getString("alert.title"));
                alert.setHeaderText(bundle.getString("alert.headerText"));
                alert.setContentText(bundle.getString("alert.text"));

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("images/logo.png").toString()));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    try {
                        presenter.removeEntry(treeView.getSelectionModel().getSelectedItem().getValue());
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
        MenuItem item2 = new MenuItem(bundle.getString("contextMenu.edit"));
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    editEntryScene(treeView.getSelectionModel().getSelectedItem());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });
        contextMenu.getItems().add(item2);
        MenuItem item3 = new MenuItem(bundle.getString("contextMenu.copyPassword"));
        item3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(treeView.getSelectionModel().getSelectedItem().getValue().getPassword());
                clipboard.setContent(content);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(Integer.parseInt(presenter.getTextField_settings_timeoutClipboard())), ev -> {
                    clipboard.clear();
                }));
                timeline.play();
            }
        });
        contextMenu.getItems().add(item3);
    }

//Folgende Methode Hinzugefügt von Eglseder am 22.04.18

    private void buildContextMenuRecycleBin() {
        contextMenuRecycleBin = null;
        contextMenuRecycleBin = new ContextMenu();
        MenuItem item1 = new MenuItem(bundle.getString("contextMenu.delete"));
        item1.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent event) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(bundle.getString("alert.title"));
                alert.setHeaderText(bundle.getString("alert.headerText"));
                alert.setContentText(bundle.getString("alert.text"));

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("images/logo.png").toString()));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    try {
                        presenter.removeEntry(tableView_recylce.getSelectionModel().getSelectedItem().getValue());
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
        contextMenuRecycleBin.getItems().add(item1);
        MenuItem item2 = new MenuItem(bundle.getString("contextMenu.edit"));
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    editEntryScene(tableView_recylce.getSelectionModel().getSelectedItem());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });
        contextMenuRecycleBin.getItems().add(item2);
    }


    private void editEntryScene(TreeItem<EntryProperty> selectedItem) throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEntry.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //übergibt an EditEntryController den ausgewählten Eintrag
        EditEntryController editEntryController = loader.getController();
        //Ausgewähltes Element treeView.getSelectionModel().getSelectedItem()
        editEntryController.setPresenter(presenter);
        editEntryController.setEntry(selectedItem);
        editEntryController.fillComboBox();
        editEntryController.fillComboBoxhistorie();



        Parent parentEditEntry = loader.getRoot();
        Stage stageEditEntry = new Stage();
        Scene sceneEditentry = new Scene(parentEditEntry, 420, 420);
        stageEditEntry.setTitle(bundle.getString("entryEdit.title"));
        stageEditEntry.setScene(sceneEditentry);
        stageEditEntry.setResizable(false);
        stageEditEntry.initModality(Modality.APPLICATION_MODAL);
        stageEditEntry.show();
        stageEditEntry.setResizable(false);
        stageEditEntry.getIcons().add(new Image(String.valueOf(this.getClass().getResource("images/logo.png"))));
    }


    public void fillRecycleTable() {

        //Spalte Title
        JFXTreeTableColumn<EntryProperty, String> columntitleName = new JFXTreeTableColumn<>(bundle.getString("tableColumn.title"));
        columntitleName.setPrefWidth(100);
        columntitleName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().titleProperty();
            }
        });

        //Spalte Username
        JFXTreeTableColumn<EntryProperty, String> usernameCol = new JFXTreeTableColumn<>(bundle.getString("tableColumn.username"));
        usernameCol.setPrefWidth(100);
        usernameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().usernameProperty();
            }
        });

        //Spalte URL
        JFXTreeTableColumn<EntryProperty, String> urlCol = new JFXTreeTableColumn<>(bundle.getString("tableColumn.url"));
        urlCol.setPrefWidth(150);
        urlCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().urlProperty();
            }
        });

        //Spalte Description
        JFXTreeTableColumn<EntryProperty, String> desCol = new JFXTreeTableColumn<>(bundle.getString("tableColumn.description"));
        desCol.setPrefWidth(150);
        desCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().descriptionProperty();
            }
        });
        ObservableList<EntryProperty> entryPropertiesListRecycle = FXCollections.observableArrayList();

        for (EntryProperty entryRecycle:presenter.getEntryPropertiesListRecycle()) {
            if (entryRecycle.getCategoryID() == -1) {
                entryPropertiesListRecycle.add(entryRecycle);
            }
        }

        //Inhalte werden in die Tabelle geschrieben
        final TreeItem<EntryProperty> root1 = new RecursiveTreeItem<EntryProperty>(entryPropertiesListRecycle, RecursiveTreeObject::getChildren);
        tableView_recylce.getColumns().setAll(columntitleName, usernameCol, urlCol, desCol);
        tableView_recylce.setRoot(root1);
        tableView_recylce.setShowRoot(false);
        tableView_recylce.sort();


        //ruft Methode auf und baut ContextMenu zusammen
        buildContextMenuRecycleBin();



        //Eventhandling für die Elemente
        tableView_recylce.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ee) {
                if (ee.isPrimaryButtonDown() && ee.getClickCount() == 2) {

                    try {
                        editEntryScene(tableView_recylce.getSelectionModel().getSelectedItem());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    }
                }
                if (ee.isSecondaryButtonDown()) {
                    contextMenuRecycleBin.show(tableView_recylce, ee.getScreenX(), ee.getScreenY());
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
                        Boolean flag = entryTreeItem.getValue().titleProperty().getValue().toLowerCase().contains(newValue.toLowerCase()) ||
                                entryTreeItem.getValue().usernameProperty().getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });
    }


    public void fillComboboxLangauge() {
        comboBox_settings_language.setPromptText("Select...");
        comboBox_settings_language.getItems().add(new Label("English"));
        comboBox_settings_language.getItems().add(new Label("German"));
    }


// Folgende Methoden hinzugefügt von Wagenhuber:


    public void btn_settings_setNumberBackupEntries(ActionEvent actionEvent) {
        presenter.setTextField_settings_numberBackupEntries(textField_settings_backupEntries.getText());
        updateSaveStatus();
    }


    public void btn_settings_lengthRandomPasswords(ActionEvent actionEvent) {
        presenter.setTextField_settings_lengthRandomPasswords(textField_settings_lengthRandomPasswords.getText());
        updateSaveStatus();
    }


    public void btn_settings_timeoutClipboard(ActionEvent actionEvent) {
        presenter.setTextField_settings_timeoutClipboard(textField_settings_timeoutClipboard.getText());
        updateSaveStatus();
    }

    //Hinzugefügt Kopp
    public void btn_settings_language(ActionEvent actionEvent) throws SQLException {
        switch (comboBox_settings_language.getSelectionModel().getSelectedIndex()) {
            case 0:
                presenter.setLanguage("en");
                bundle = presenter.setLanguage("en");
                setLang();
                presenter.setTextField_settings_saveStatusBoolean(true);
                updateSaveStatus();
                break;
            case 1:
                presenter.setLanguage("de");
                bundle = presenter.setLanguage("de");
                setLang();
                presenter.setTextField_settings_saveStatusBoolean(true);
                updateSaveStatus();
                break;
        }
    }


    private void updateSaveStatus() {
        boolean status = presenter.isTextField_settings_saveStatusBoolean();
        if (status) {
            textField_settings_saveStatus.setText(bundle.getString("textField.settings.success"));
        } else {
            textField_settings_saveStatus.setText(bundle.getString("textField.settings.error"));
        }

    }

}
