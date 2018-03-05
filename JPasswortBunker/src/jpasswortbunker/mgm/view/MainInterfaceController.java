package jpasswortbunker.mgm.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jpasswortbunker.mgm.presenter.EntryProperty;
import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;


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
    private JFXTreeTableView<EntryProperty> treeView;

    @FXML
    private JFXTextField textField_Search;

    @FXML
    private AnchorPane mainAnchorPane;

    private static Stage stageMainInterfaceController, stageSetMasterPassword, stageLogin;

    private PresenterMain presenter = new PresenterMain(this);

    public MainInterfaceController() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
    }

    //Methode wird bei Controlleraufruf ausgeführt
    public void initialize() throws IOException {
        //checkSetMasterpassword();
        stageMainInterfaceController = Testklasse.getPrimaryStage();
    }

    public static Stage getStageSetMasterPassword() {
        return stageSetMasterPassword;
    }

    public static Stage getStageLogin() {
        return stageLogin;
    }

    //Methode überprüft ob ein MasterPasswort gesetzt ist
    //Ja -> Login
    //Nein -> SetMasterPassword
//    private void checkSetMasterpassword() throws IOException {
//        if (presenter.checkSetMasterpassword()) {
//            System.out.println("gesetzt");
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
//            Parent parent = fxmlLoader.load();
//            LoginScreenController setMasterPasswordController = fxmlLoader.<LoginScreenController>getController();
//            setMasterPasswordController.setPresenter(presenter);
//            this.stageLogin = new Stage();
//            stageLogin.setTitle("SetMasterPassword");
//            stageLogin.setScene(new Scene(parent, 500, 400));
//            stageLogin.setAlwaysOnTop(true);
//            stageLogin.show();
//        } else {
//            System.out.println("nicht gesetzt");
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetMasterPassword.fxml"));
//            Parent parent = fxmlLoader.load();
//            SetMasterPasswordController setMasterPasswordController = fxmlLoader.<SetMasterPasswordController>getController();
//            setMasterPasswordController.setPresenter(presenter);
//            this.stageSetMasterPassword = new Stage();
//            stageSetMasterPassword.setTitle("SetMasterPassword");
//            stageSetMasterPassword.setScene(new Scene(parent, 500, 400));
//            stageSetMasterPassword.setAlwaysOnTop(true);
//            stageSetMasterPassword.show();
//        }
//    }

    public void mainTest() {


    }




}
