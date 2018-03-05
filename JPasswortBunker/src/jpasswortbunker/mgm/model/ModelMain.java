package jpasswortbunker.mgm.model;

import jpasswortbunker.mgm.presenter.PresenterMain;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ModelMain {

    private PasswordObject masterPasswordObject;
    private EntryList entryList;
    private EncryptionService encryptionService;
    private DBService dbService;
    private PresenterMain presenter;


    public ModelMain(String userInputForMasterPassword) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        this.dbService = new DBService();
        this.masterPasswordObject = PasswordObject.getInstance();
        initMasterPassword(userInputForMasterPassword);
        this.entryList = new EntryList();
        this.encryptionService = new EncryptionService();
    }

    /**Zugriff via Presenter
     * Presenter wird an Model übergeben
     */
    public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }

    /**Zugriff via View
     * Masterpasswort wird entgegengenommen und weitergereicht an PasswordObject
     */
    public void initMasterPassword(String password) throws UnsupportedEncodingException, InvalidKeyException, BadPaddingException, SQLException, IllegalBlockSizeException {
        this.masterPasswordObject.setPassword(password);
    }

    /**Zugriff via View
     * Das zuvor entgegengenommen Passwort des Benutzers wird nun über den DatenbankService mit dem Passwort in der DB abgelichen
     */
    public boolean checkIfMasterPasswordIsCorrect() throws UnsupportedEncodingException, SQLException {
        return masterPasswordObject.checkPassword(getSaltPasswordHashForPasswortStoreFromDb());

    }

    /**Zugriff via View
     * Ein zuvor verwendetes Masterpasswort wird durch ein neues Ersetzt.
     * Hierbei werden alle Einträge mit dem neuen Passwort entschlüsselt und neu verschlüsselt.
     */
    //Achtung Methode zum neuverschlüsseln aller Datensätze fehlt!!
    public void renewMasterPassword(String password) throws InvalidKeyException, BadPaddingException, SQLException, IllegalBlockSizeException, UnsupportedEncodingException {
        setSaltPasswordHashForPasswortStoreInDb(password);
        initMasterPassword(password);

        ArrayList<Entry> arrayList = (ArrayList<Entry>) this.entryList.getEntryObjectList();

        for (Entry entry : arrayList) {
            dbService.reEnryptEntry(entry.getTitle(), entry.getUsername(), entry.getPassword(), entry.getDescription(), entry.getUrl(), entry.getEntryID());
        }
    }


    /**Kein Zugriff via View
     * Abrufen des Masterpasswort-Hash-Wertes aus der Datenbank
     */
    private String getSaltPasswordHashForPasswortStoreFromDb() throws SQLException, UnsupportedEncodingException {
        String masterPasswordFromDB = dbService.getMasterPasswordFromDB();
        return masterPasswordFromDB;
    }

    /**Kein Zugriff via View
     * Masterpasswort-Hash-Wert in Datenbank setzen bzw. überschreiben
     */
    private void setSaltPasswordHashForPasswortStoreInDb(String password) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, SQLException {
        dbService.setMasterPasswordToDB(this.masterPasswordObject.getSaltPasswordHashForPasswortStore());
    }

    /**Zugriff via View
     * ArrayList aus Klasse EntryList zurückgeben
     */
    public ArrayList<Entry> getEntryList() {
        ArrayList<Entry> arrayList = (ArrayList<Entry>) this.entryList.getEntryObjectList();
        return arrayList;
    }

    /**Zugriff via View
     * Einträge aus Datenbank holen und in EntryProperty-Liste schreiben
     */
    public void FillEntryListFromDb() throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        ArrayList<Entry> arrayListEncrypted;
        arrayListEncrypted = dbService.readAllEntries();
        ArrayList<Entry> arrayListDecrypted = new ArrayList<>();

        for (Entry encryptedEntry : arrayListEncrypted) {
            Entry decryptedEntry = new Entry(encryptionService.decrypt(encryptedEntry.getTitle()), encryptionService.decrypt(encryptedEntry.getUsername()), encryptionService.decrypt(encryptedEntry.getPassword()), encryptionService.decrypt(encryptedEntry.getDescription()), encryptionService.decrypt(encryptedEntry.getUrl()), encryptedEntry.getCategoryID(), encryptedEntry.getDbID(), encryptedEntry.getEntryID(), encryptedEntry.getTimestamp());
            arrayListDecrypted.add(decryptedEntry);
        }
        this.entryList.setEntryObjectList(arrayListDecrypted);
    }


    /**Kein Zugriff via View
     * EntryProperty-Objekt zur Liste hinzufügen
     */
    private void addEntryToList(Entry entry) {
        entryList.addEntry(entry);
    }


    /**Zugriff via View
     * Neuen Eintrag erstelen
     */
    public void newEntry(String title, String username, String password, String description, String url, int categoryID) throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        Entry newEntry = new Entry(title, username, password, description, url, categoryID);
        entryList.addEntry(newEntry);
        Entry newEntryEncrypted = new Entry(encryptionService.encrypt(title), encryptionService.encrypt(username), encryptionService.encrypt(password), encryptionService.encrypt(description), encryptionService.encrypt(url), categoryID);
        dbService.insertEntry(newEntryEncrypted);
    }

    /**Zugriff via View
     * Bestehenden Datensatz ändern
     */
    public void updateEntry(Entry newerEntry) throws SQLException {
        Entry olderEntry = this.entryList.getEntry(newerEntry.getEntryID());
        dbService.insertEntryInRecycleBin(olderEntry);
        olderEntry = newerEntry;
    }




    //##############################################################################################

    //Einträge aus Liste entferen
    public void removeEntryFromList(Entry entry) {
        if (entryList.removeEntry(entry) != null) {
            System.out.println("Eintrag wurde gelöscht");
            return;
        }
        System.out.println("eintrag konnte nicht gelöscht werden");
    }

    public void removeEntryFromList(UUID uuid) {
        if (entryList.removeEntry(uuid) != null) {
            System.out.println("Eintrag wurde gelöscht");
            return;
        }
        System.out.println("eintrag konnte nicht gelöscht werden");
    }

    public void removeEntryFromList(String title) {
        if (entryList.removeEntry(title) != null) {
            System.out.println("Eintrag wurde gelöscht");
            return;
        }
        System.out.println("eintrag konnte nicht gelöscht werden");
    }



}
