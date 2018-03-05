package jpasswortbunker.mgm.presenter;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jpasswortbunker.mgm.model.ModelMain;
import jpasswortbunker.mgm.view.MainInterfaceController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public final class PresenterMain {

    private MainInterfaceController controller;
    //private SetMasterPasswordController controllerSetMasterPassword;
    private ModelMain model;
    private StringProperty stringProperty;
    public ObservableList<EntryProperty> entryPropertiesList = FXCollections.observableArrayList();


    public PresenterMain(MainInterfaceController controller) throws NoSuchPaddingException, BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, NoSuchAlgorithmException, InvalidKeyException {
        this.controller = controller;
        model = new ModelMain("test");
        initStringProperty();
    }


    public void initStringProperty() {


    }

//    public void updateGui() {
//        controller.updateGui();
//    }

    //######################################################################################################


    public PresenterMain getPresenter() {
        return this;
    }

    //Schreibt die Liste der Arraylist aus Model in die Observable List im Presenter
    public void writeToObservableList() {
        for (jpasswortbunker.mgm.model.Entry entry : model.getEntryList()) {
            entryPropertiesList.add(new EntryProperty(entry.getDbID(), entry.getEntryID(), entry.getTitle(),
                    entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getDescription(), entry.getCategoryID()));
        }
    }


//    public boolean checkSetMasterpassword() {
//        if (model.checkSetMasterpassword()) {
//
//            return true;
//        } else {
//
//            return false;
//        }
//    }


    public boolean checkIfMasterPasswordIsCorrect() throws UnsupportedEncodingException, SQLException {
        return model.checkIfMasterPasswordIsCorrect();
    }


    public void test() {
        System.out.println("testMethode Presenter");
    }

}
