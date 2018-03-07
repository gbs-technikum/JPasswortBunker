package jpasswortbunker.mgm.view;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import jpasswortbunker.mgm.entry.Entry;
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
import java.util.ResourceBundle;


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
    private JFXTreeTableView<EntryProperty> treeView;

    @FXML
    private JFXTextField textField_Search;

    @FXML
    private AnchorPane mainAnchorPane;

    private static Stage stageMainInterfaceController, stageSetMasterPassword, stageLogin, stageNewEntry;

    private PresenterMain presenter = new PresenterMain(this);

    public MainInterfaceController() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTreeView();
    }

    public void fillTreeView() {
        //ToDo Methoden aufruf muss wo anders eingebaut werden, sollte erst aufgerufen werden wenn Passwort richtig
        try {
            presenter.writeToObservableList();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


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

        //Spalte Password
        JFXTreeTableColumn<EntryProperty, String> passwordCol = new JFXTreeTableColumn<>("Password");
        passwordCol.setPrefWidth(150);
        passwordCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<EntryProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<EntryProperty, String> param) {
                return param.getValue().getValue().passwordProperty();
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
        final TreeItem<EntryProperty> root = new RecursiveTreeItem<EntryProperty>(presenter.getEntryPropertiesList(), RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(titleName, usernameCol, passwordCol, urlCol, desCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }



    //Methode wird bei Controlleraufruf ausgeführt
    public void initialize() throws IOException {
        checkSetMasterpassword();
        stageMainInterfaceController = Testklasse.getPrimaryStage();
        //stageMainInterfaceController.show();
    }


    /**Ruft Methode in Model auf um zu überprüfen, ob Masterpasswort gesetzt wurde
     * Return Value:
     * true -> LoginScreen
     * false -> SetMasterPassword
     */
    private void checkSetMasterpassword() throws IOException {
        if (presenter.checkSetMasterpassword()) {
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




}
