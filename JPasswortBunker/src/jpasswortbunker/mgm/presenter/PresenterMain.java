package jpasswortbunker.mgm.presenter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jpasswortbunker.mgm.model.Entry;
import jpasswortbunker.mgm.model.ModelMain;
import jpasswortbunker.mgm.view.MainInterfaceController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public final class PresenterMain {

    private MainInterfaceController controller;
    private ModelMain model;
    public ObservableList<EntryProperty> entryPropertiesList = FXCollections.observableArrayList();
    public ObservableList<EntryProperty> entryPropertiesListRecycle = FXCollections.observableArrayList();
    private StringProperty textField_settings_numberBackupEntries;
    private StringProperty textField_settings_saveStatus;

    public PresenterMain(MainInterfaceController controller) throws NoSuchPaddingException, BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, NoSuchAlgorithmException, InvalidKeyException {
        this.controller = controller;
        model = new ModelMain();

        initProperties();

        //model.initMasterPassword("test");
        //model.initEncryptionService();
    }


    public ObservableList<EntryProperty> getEntryPropertiesList() {
        return entryPropertiesList;
    }

    public ObservableList<EntryProperty> getEntryPropertiesListRecycle() {
        return entryPropertiesListRecycle;
    }

    public PresenterMain getPresenter() {
        return this;
    }

    //Schreibt die Liste der Arraylist aus Model in die Observable List im Presenter
    public void writeToObservableList() throws SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException {
        model.FillEntryListFromDb();
        for (jpasswortbunker.mgm.model.Entry entry : model.getEntryListEntrysTable()) {
            entryPropertiesList.add(new EntryProperty(entry.getDbID(), entry.getEntryID(), entry.getTitle(),
                    entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getDescription(), entry.getCategoryID()));
        }
    }

    //TODO: 14.03.2018  anpassen für das Schreiben in den Mülleimer
    //Schreibt die Liste der Arraylist aus Model in die Observable List im Presenter
    public void writeToObservableListrecycle() throws SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException {
        model.FillEntryListFromRecycleBin();
        for (jpasswortbunker.mgm.model.Entry entry : model.getEntryListRecycleBinTable()) {
            entryPropertiesListRecycle.add(new EntryProperty(entry.getDbID(), entry.getEntryID(), entry.getTitle(),
                    entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getDescription(), entry.getCategoryID()));
        }
    }

    public void renewMasterPassword(String password) throws BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        model.renewMasterPassword(password);
        controller.fillTreeView();
    }

    public void initMasterPassword(String password) throws IllegalBlockSizeException, SQLException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        model.initMasterPassword(password);
    }


    public boolean checkIfMasterPasswordIsCorrect() throws UnsupportedEncodingException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (model.checkIfMasterPasswordIsCorrect()) {
            model.initEncryptionService();
            writeToObservableList();
            //Todo Fehler mit den Recycle Einträgen, wirft eine Exception nach MasterPasswort Änderung
            //Könnte ein Problem mit der Verschlüsselung oder Datenbank sein
            writeToObservableListrecycle();
            controller.updateView();
            return true;
        }
        return false;
    }

    public boolean checkIfMasterPasswortExistsInDB() throws SQLException {
        return model.checkIfMasterPasswortExistsInDB();
    }

    public void newEntry(String title, String username, String password, String description, String url, int categoryID) throws SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException {
        model.newEntry(title, username, password, description, url, categoryID);
        /**
         * Parameter werden an Model übergeben und Entry in Model erstellt
         * Den eben erstellten Eintrag wieder holen und diesen in die ObservableList schreiben
         */
        Entry entry = model.getEntryListEntrysTable().get(model.getEntryListEntrysTable().size() - 1);
        entryPropertiesList.add(new EntryProperty(entry.getDbID(), entry.getEntryID(), entry.getTitle(),
                entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getDescription(), entry.getCategoryID()));
        controller.updateView2();
    }

    public void removeEntry(EntryProperty entry) throws IllegalBlockSizeException, SQLException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        model.removeEntry(entry.getEntryID().toString());
        entryPropertiesList.remove(entry);
        //controller.fillTreeView();
    }

    public void updateEntry(EntryProperty entry) throws IllegalBlockSizeException, SQLException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        model.updateEntry(entry.getEntryID().toString(), entry.getTitle(), entry.getUsername(), entry.getPassword(), entry.getPassword(), entry.getDescription(), entry.getCategoryID());
        //controller.fillTreeView();//Obsolet - führt zu Programmabsturz
    }

    public void updateView() {
        controller.updateView();
    }

    public List getCategoryListFromDB() throws SQLException {
        //System.out.println(model.getCategoryListFromDB().size() + "-------------");
        return model.getCategoryListFromDB();
    }

    public String createPassword() throws SQLException {
        return model.createPassword();
    }


    public void test() {
        System.out.println("testMethode Presenter");
    }


    // Folgende Methoden hinzugefügt von Wagenhuber:

    /*public void setNumberOfBackupEntiresToDB(String number) throws SQLException {
        model.setNumberOfBackupEntiresToDB(Integer.parseInt(number));
    }*/


    public String getTextField_settings_numberBackupEntries() {
        return textField_settings_numberBackupEntries.getValue();
    }

    public StringProperty getTextField_settings_numberBackupEntriesProperty() {
        return textField_settings_numberBackupEntries;
    }

    public void setTextField_settings_numberBackupEntries(String textField_settings_numberBackupEntries) {
        this.textField_settings_numberBackupEntries.set(textField_settings_numberBackupEntries);
    }

    public String getTextField_settings_saveStatus() {
        return textField_settings_saveStatus.getValue();
    }

    public StringProperty setTextField_settings_saveStatusProperty() {
        return textField_settings_saveStatus;
    }

    public void setTextField_settings_saveStatus(String textField_settings_saveStatus) {
        this.textField_settings_saveStatus.setValue(textField_settings_saveStatus);
    }

    private void initProperties() {


        try {
            textField_settings_numberBackupEntries = new SimpleStringProperty();
            textField_settings_numberBackupEntries.setValue(String.valueOf(model.getNumberOfBackupEntriesFromDB()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        textField_settings_saveStatus = new SimpleStringProperty();


        textField_settings_numberBackupEntries.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (checkIfTextFieldNumeric(textField_settings_numberBackupEntries.getValue())) {
                        if (model.setNumberOfBackupEntiresToDB(Integer.parseInt(textField_settings_numberBackupEntries.getValue()))) {
                            setTextField_settings_saveStatus("true");
                        } else {
                            setTextField_settings_saveStatus("false");
                        }
                    } else {
                        setTextField_settings_saveStatus("false");
                        //System.out.println(textField_settings_saveStatus);
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    textField_settings_numberBackupEntries.setValue(String.valueOf(model.getNumberOfBackupEntriesFromDB()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

    }//ende initProperties


    public boolean checkIfTextFieldNumeric(String value){
            if (!value.matches("\\d*")) {
                return false;
            }
            return true;
        }


}
