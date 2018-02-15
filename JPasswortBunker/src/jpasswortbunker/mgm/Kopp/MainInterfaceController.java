package jpasswortbunker.mgm.Kopp;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.stage.Window;
import javafx.util.Callback;
import jpasswortbunker.mgm.entry.Entry;
import jpasswortbunker.mgm.gui.EditEntryController;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class MainInterfaceController {

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

    @FXML
    private AnchorPane mainAnchorPane;

    private Presenter presenter = new Presenter(this);

    //Methode wird bei Controlleraufruf ausgeführt
    public void initialize() throws IOException {
        checkSetMasterpassword();
    }

    //Methode überprüft ob ein MasterPasswort gesetzt ist
    //Ja -> Login
    //Nein -> SetMasterPassword
    private void checkSetMasterpassword() throws IOException {
        if (presenter.checkSetMasterpassword()) {
            System.out.println("gesetzt");
            Stage stageLogin= new Stage();
            Parent parentLogin = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
            Scene scene2 = new Scene(parentLogin, 500, 400);
            stageLogin.setTitle("zweites Fenster");
            stageLogin.setScene(scene2);
            stageLogin.setAlwaysOnTop(true);
            stageLogin.show();
        } else {
            System.out.println("nicht gesetzt");
            Stage stageSetMasterPassword = new Stage();
            Parent parentSetMasterPassword = FXMLLoader.load(getClass().getResource("SetMasterPassword.fxml"));
            Scene scene2 = new Scene(parentSetMasterPassword, 500, 400);
            stageSetMasterPassword.setTitle("zweites Fenster");
            stageSetMasterPassword.setScene(scene2);
            stageSetMasterPassword.setAlwaysOnTop(true);
            stageSetMasterPassword.show();
        }
    }




}
