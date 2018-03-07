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
import java.util.Iterator;
import java.util.UUID;

public class ModelMain {

    private PasswordObject masterPasswordObject;
    private EntryList entryList;
    private EncryptionService encryptionService;
    private DBService dbService;
    private PresenterMain presenter;


    public ModelMain() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        this.masterPasswordObject = PasswordObject.getInstance();
        this.dbService = new DBService();
        this.entryList = new EntryList();
        //initMasterPassword(userInputForMasterPassword);
        //this.encryptionService = new EncryptionService();
    }




    public void initEncryptionService() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.encryptionService = new EncryptionService();
    }

    /**
     * Zugriff via View
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

    /**
     * Zugriff via View
     * Das zuvor entgegengenommen Passwort des Benutzers wird nun über den DatenbankService mit dem Passwort in der DB abgelichen
     */
    public boolean checkIfMasterPasswordIsCorrect() throws UnsupportedEncodingException, SQLException {
        return masterPasswordObject.checkPassword(getSaltPasswordHashForPasswortStoreFromDb());

    }

    /**
     * Zugriff via View
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


    /**
     * Kein Zugriff via View
     * Abrufen des Masterpasswort-Hash-Wertes aus der Datenbank
     */
    private String getSaltPasswordHashForPasswortStoreFromDb() throws SQLException, UnsupportedEncodingException {
        String masterPasswordFromDB = dbService.getMasterPasswordFromDB();
        return masterPasswordFromDB;
    }

    /**
     * Kein Zugriff via View
     * Masterpasswort-Hash-Wert in Datenbank setzen bzw. überschreiben
     */
    private void setSaltPasswordHashForPasswortStoreInDb(String password) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, SQLException {
        dbService.setMasterPasswordToDB(this.masterPasswordObject.getSaltPasswordHashForPasswortStore());
    }

    /**
     * Zugriff via View
     * ArrayList aus Klasse EntryList zurückgeben
     */
    public ArrayList<Entry> getEntryList() {
        ArrayList<Entry> arrayList = (ArrayList<Entry>) this.entryList.getEntryObjectList();
        return arrayList;
    }

    /**
     * Zugriff via View
     * Einträge aus Datenbank holen und in Entry-Liste schreiben
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


    /**
     * Kein Zugriff via View
     * Entry-Objekt zur Liste hinzufügen
     */
    private void addEntryToList(Entry entry) {
        entryList.addEntry(entry);
    }


    /**
     * Zugriff via View
     * Neuen Eintrag erstelen
     */
    public void newEntry(String title, String username, String password, String description, String url, int categoryID) throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        Entry newEntry = new Entry(title, username, password, description, url, categoryID);
        entryList.addEntry(newEntry);
        Entry newEntryEncrypted = createEncryptedEntry(newEntry);

        dbService.insertEntry(newEntryEncrypted);
    }


    /**
     * Zugriff via View
     * Bestehenden Datensatz ändern
     */
    public boolean updateEntry(String entryID, String title, String username, String password, String url, String descripton, int categoryID) throws SQLException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        ArrayList<Entry> entryArrayList = (ArrayList<Entry>) this.entryList.getEntryObjectList();
        for (Entry entry : entryArrayList) {
            if (entry.getEntryIDasString().equals(entryID)) {
                Entry encrypedEntryForRecycleBinTable = createEncryptedEntry(entry);
                dbService.insertEntryInRecycleBin(encrypedEntryForRecycleBinTable);
                entry.setTitle(title);
                entry.setUsername(username);
                entry.setPassword(password);
                entry.setUrl(url);
                entry.setDescription(descripton);
                entry.setCategoryID(categoryID);
                entry.setTimestamp(System.currentTimeMillis() / 1000L);
                Entry encrypedEntryForEntrysTable = createEncryptedEntry(entry);
                dbService.updateEntry(encrypedEntryForEntrysTable);
                return true;
            }
        }
        System.out.println("EntryID nicht gefunden!");
        return false;
    }

    /**
     * Gibt die Datenfelder eines Entry in verschlüsselter Form zurück.
     */
    public Entry createEncryptedEntry(Entry entry) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, SQLException {
        String title = entry.getTitle();
        String username = entry.getUsername();
        String password = entry.getPassword();
        String description = entry.getDescription();
        String url = entry.getUrl();
        int categoryID = entry.getCategoryID();
        int dbId = entry.getDbID();
        UUID entryID = entry.getEntryID();
        long timestamp = entry.getTimestamp();
        Entry newEntryEncrypted = new Entry(encryptionService.encrypt(title), encryptionService.encrypt(username), encryptionService.encrypt(password), encryptionService.encrypt(description), encryptionService.encrypt(url), categoryID, dbId, entryID, timestamp);
        return newEntryEncrypted;
    }


    public void soutEntryList() {
        ArrayList<Entry> entryArrayList = (ArrayList<Entry>) this.entryList.getEntryObjectList();
        for (Entry entry : entryArrayList) {
            System.out.println(entry);
        }
    }

    /**
     * Löscht einen Entry aus der Liste und Datenbank. Zudem wird die Category_ID für Einträge im RecycleController auf -1 gesetzt.
     * Kategorie -1 bedeutet gelöscht in Entry-Tabelle
     */
    public boolean removeEntry(String entryIdAsString) throws SQLException {
        ArrayList<Entry> entryArrayList = (ArrayList<Entry>) this.entryList.getEntryObjectList();
        Iterator<Entry> iterator = entryArrayList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getEntryIDasString().equals(entryIdAsString)) {
                iterator.remove();
                dbService.removeEntry(entryIdAsString);
                dbService.updateRecycleBinForRemovedEntrys(entryIdAsString);
                System.out.println("##Status## Entry erfolgreich gelöscht!");
                return true;
            }
        }
        System.out.println("##Status## Entry konnte nicht gelöscht werden!");
        return false;
    }





}
