package jpasswortbunker.mgm.presenter;

import javafx.beans.property.*;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public final class PresenterMain {

    private MainInterfaceController controller;
    private ModelMain model;
    public ObservableList<EntryProperty> entryPropertiesList = FXCollections.observableArrayList();
    public ObservableList<EntryProperty> entryPropertiesListRecycle = FXCollections.observableArrayList();
    private StringProperty textField_settings_numberBackupEntries;
    private StringProperty textField_settings_lengthRandomPasswords;
    private StringProperty textField_settings_timeoutClipboard;
    private BooleanProperty textField_settings_saveStatusBoolean;
    private IntegerProperty categoryChoosenForLastNewEntry;
    private StringProperty textField_settings_TimeClipboard;
    //private String language = "en";

    private Locale locale;
    private ResourceBundle bundle;


    public ResourceBundle setLanguage(String language) throws SQLException {
        locale = new Locale(language);
        bundle = ResourceBundle.getBundle("jpasswortbunker.mgm.view.bundles.LangBundle", locale);
        model.setLanguageToDB(language);
        return bundle;
    }

    public ResourceBundle getLangBundle() throws SQLException {
        locale = new Locale(model.getLanguageFromDB());
        bundle = ResourceBundle.getBundle("jpasswortbunker.mgm.view.bundles.LangBundle", locale);
        return bundle;
    }

    public PresenterMain(MainInterfaceController controller) throws NoSuchPaddingException, BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, NoSuchAlgorithmException, InvalidKeyException {
        this.controller = controller;
        model = new ModelMain();

        initProperties();
        getLangBundle();
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
        entryPropertiesList.clear();
        for (jpasswortbunker.mgm.model.Entry entry : model.getEntryListEntrysTable()) {
            entryPropertiesList.add(new EntryProperty(entry.getDbID(), entry.getEntryID(), entry.getTitle(),
                    entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getDescription(), entry.getCategoryID(), entry.getTimestamp()));
        }
    }


    //Schreibt die Liste der Arraylist aus Model in die Observable List im Presenter
    public void writeToObservableListrecycle() throws SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException {
        model.FillEntryListFromRecycleBin();
        entryPropertiesListRecycle.clear();
        //Geändert Wagenhuber - Vorher: for (jpasswortbunker.mgm.model.Entry entry : model.getEntryListRecycleBinTable()) {
        for (jpasswortbunker.mgm.model.Entry entry : model.getEntryListRecycleBinTableLatestTimestamp()) {
            entryPropertiesListRecycle.add(new EntryProperty(entry.getDbID(), entry.getEntryID(), entry.getTitle(),
                    entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getDescription(), entry.getCategoryID(), entry.getTimestamp()));
        }
    }

    public void renewMasterPassword(String password) throws BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        model.renewMasterPassword(password);
        writeToObservableListrecycle();
        writeToObservableList();
        controller.updateRecycleView();
        controller.updateView();
        controller.btn_logo();
    }

    public void initMasterPassword(String password) throws IllegalBlockSizeException, SQLException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        model.initMasterPassword(password);
    }


    public void setMasterPasswordinDB(String password) throws IllegalBlockSizeException, SQLException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        model.setSaltPasswordHashForPasswortStoreInDb(password);
        model.initMasterPassword(password);
        model.initEncryptionService();
        controller.loadView();
    }

    public boolean checkIfMasterPasswordIsCorrect() throws UnsupportedEncodingException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (model.checkIfMasterPasswordIsCorrect()) {
            model.initEncryptionService();
            writeToObservableList();
            writeToObservableListrecycle();
            controller.loadView();
            return true;
        }
        return false;
    }

    public boolean checkIfMasterPasswortExistsInDB() throws SQLException {
        return model.checkIfMasterPasswortExistsInDB();
    }

    public void newEntry(String title, String username, String password, String url, String description, int categoryID) throws SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException {
        model.newEntry(title, username, password, description, url, categoryID);
        /**
         * Parameter werden an Model übergeben und Entry in Model erstellt
         * Den eben erstellten Eintrag wieder holen und diesen in die ObservableList schreiben
         */
        Entry entry = model.getEntryListEntrysTable().get(model.getEntryListEntrysTable().size() - 1);
        entryPropertiesList.add(new EntryProperty(entry.getDbID(), entry.getEntryID(), entry.getTitle(),
                entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getDescription(), entry.getCategoryID(), entry.getTimestamp()));
        controller.updateView();
    }

    public void removeEntry(EntryProperty entry) throws IllegalBlockSizeException, SQLException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        model.removeEntry(entry.getEntryID().toString(), entry.getCategoryID());
        entryPropertiesList.remove(entry);
        //controller.fillTreeView();
        writeToObservableListrecycle();
        controller.updateRecycleView();

    }

    public void updateEntry(EntryProperty entry) throws IllegalBlockSizeException, SQLException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        model.updateEntry(entry.getEntryID().toString(), entry.getTitle(), entry.getUsername(), entry.getPassword(), entry.getUrl(), entry.getDescription(), entry.getCategoryID());
        //controller.fillTreeView();//Obsolet - führt zu Programmabsturz
        controller.updateView();
    }

//    public void updateView() {
//        controller.loadView();
//    }

    public List getCategoryListFromDB() throws SQLException {
        //System.out.println(model.getCategoryListFromDB().size() + "-------------");
        return model.getCategoryListFromDB();
    }

    public ArrayList<Entry> getEntrysFromRecycleBinForEntryID(String id) throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        return model.getEntrysFromRecycleBinForEntryID(id);
    }

    public String createPassword() throws SQLException {
        return model.createPassword();
    }


    public void test() {
        System.out.println("testMethode Presenter");
    }

    public void restoreEntryFromRecycleBin(String entryID, int categoryID, long timestamp) throws SQLException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, UnsupportedEncodingException {
        model.restoreEntryFromRecycleBin(entryID, categoryID, timestamp);
        writeToObservableListrecycle();
        writeToObservableList();
        controller.updateRecycleView();
        controller.updateView();
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


    public String getTextField_settings_lengthRandomPasswords() {
        return textField_settings_lengthRandomPasswords.getValue();
    }

    public StringProperty textField_settings_lengthRandomPasswordsProperty() {
        return textField_settings_lengthRandomPasswords;
    }

    public void setTextField_settings_lengthRandomPasswords(String textField_settings_lengthRandomPasswords) {
        this.textField_settings_lengthRandomPasswords.setValue(textField_settings_lengthRandomPasswords);
    }


    public String getTextField_settings_timeoutClipboard() {
        return textField_settings_timeoutClipboard.getValue();
    }

    public StringProperty textField_settings_timeoutClipboardProperty() {
        return textField_settings_timeoutClipboard;
    }

    public void setTextField_settings_timeoutClipboard(String textField_settings_timeoutClipboard) {
        this.textField_settings_timeoutClipboard.setValue(textField_settings_timeoutClipboard);
    }


    public boolean isTextField_settings_saveStatusBoolean() {
        return textField_settings_saveStatusBoolean.getValue();
    }

    public BooleanProperty setTextField_settings_saveStatusBooleanProperty() {
        return textField_settings_saveStatusBoolean;
    }

    public void setTextField_settings_saveStatusBoolean(boolean textField_settings_saveStatusBoolean) {
        this.textField_settings_saveStatusBoolean.setValue(textField_settings_saveStatusBoolean);
    }


    public int getCategoryChoosenForLastNewEntry() {
        return categoryChoosenForLastNewEntry.getValue();
    }

    public IntegerProperty categoryChoosenForLastNewEntryProperty() {
        return categoryChoosenForLastNewEntry;
    }

    public void setCategoryChoosenForLastNewEntry(int categoryChoosenForLastNewEntry) {
        this.categoryChoosenForLastNewEntry.setValue(categoryChoosenForLastNewEntry);
    }

    public void setTimePeriodForClipboardFromDB(int timePeriodForClipboardFromDB) {
        this.textField_settings_TimeClipboard.setValue(String.valueOf(timePeriodForClipboardFromDB));
    }

    public StringProperty getTimePeriodForClipboardFromDB(String text) {
        return textField_settings_TimeClipboard;
    }

    public void setTextfield_TimePeriodForClipboardFromDB(String textField_settings_TimeClipboard) {
        this.textField_settings_TimeClipboard.setValue(textField_settings_TimeClipboard);
    }

    public String timestampToTime(long timeStamp) {
        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String java_date = jdf.format(date);
        return java_date;
    }


    private void initProperties() {


        categoryChoosenForLastNewEntry = new SimpleIntegerProperty();
        categoryChoosenForLastNewEntry.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            }
        });


        try {
            textField_settings_numberBackupEntries = new SimpleStringProperty();
            textField_settings_numberBackupEntries.setValue(String.valueOf(model.getNumberOfBackupEntriesFromDB()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            textField_settings_lengthRandomPasswords = new SimpleStringProperty();
            textField_settings_lengthRandomPasswords.setValue(String.valueOf(model.getLengthOfRandomPasswordsFromDB()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            textField_settings_timeoutClipboard = new SimpleStringProperty();
            textField_settings_timeoutClipboard.setValue(String.valueOf(model.getTimePeriodForClipboardFromDB()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        textField_settings_saveStatusBoolean = new SimpleBooleanProperty();

        textField_settings_numberBackupEntries.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (checkIfTextFieldNumeric(textField_settings_numberBackupEntries.getValue())) {
                        if (model.setNumberOfBackupEntiresToDB(Integer.parseInt(textField_settings_numberBackupEntries.getValue()))) {
                            setTextField_settings_saveStatusBoolean(true);
                        } else {
                            setTextField_settings_saveStatusBoolean(false);
                        }
                    } else {
                        setTextField_settings_saveStatusBoolean(false);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        textField_settings_lengthRandomPasswords.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (checkIfTextFieldNumeric(textField_settings_lengthRandomPasswords.getValue())) {
                        if (model.setLengthOfRandomPasswordsToDB(Integer.parseInt(textField_settings_lengthRandomPasswords.getValue()))) {
                            setTextField_settings_saveStatusBoolean(true);
                        } else {
                            setTextField_settings_saveStatusBoolean(false);
                        }
                    } else {
                        setTextField_settings_saveStatusBoolean(false);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        textField_settings_timeoutClipboard.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (checkIfTextFieldNumeric(textField_settings_timeoutClipboard.getValue())) {
                        if (model.setTimePeriodForClipboardToDB(Integer.parseInt(textField_settings_timeoutClipboard.getValue()))) {
                            setTextField_settings_saveStatusBoolean(true);
                        } else {
                            setTextField_settings_saveStatusBoolean(false);
                        }
                    } else {
                        setTextField_settings_saveStatusBoolean(false);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


    }//ende initProperties

    public boolean checkIfTextFieldNumeric(String value) {
        try {
            int i = Integer.parseInt(value);
            if (i > 0 && i < 31) {
                return true;
            }
        } catch (NumberFormatException e) {
        }
        return false;

    }


}
