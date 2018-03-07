package jpasswortbunker.mgm.model;

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
    private EntryList entryListEntrysTable;
    private EntryList entryListRecycleBinTable;
    private EncryptionService encryptionService;
    private DBService dbService;
    //private PresenterMain presenter;


    public ModelMain() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        this.masterPasswordObject = PasswordObject.getInstance();
        this.dbService = new DBService();
        this.entryListEntrysTable = new EntryList();
        this.entryListRecycleBinTable = new EntryList();
    }


    public void initEncryptionService() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.encryptionService = new EncryptionService();
    }

    /**
     * Zugriff via View
     /**Zugriff via Presenter
     * Presenter wird an Model übergeben
     */
    /*public void setPresenter(PresenterMain presenter) {
        this.presenter = presenter;
    }
*/

    /**
     * Zugriff via View
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

        ArrayList<Entry> arrayList = (ArrayList<Entry>) this.entryListEntrysTable.getEntryObjectList();

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
     * ArrayList mit allen Entries aus Tabelle Entry zurückgeben
     */
    public ArrayList<Entry> getEntryListEntrysTable() {
        ArrayList<Entry> arrayList = (ArrayList<Entry>) this.entryListEntrysTable.getEntryObjectList();
        return arrayList;
    }


    /**
     * Zugriff via View
     * ArrayList mit allen Entries aus Tabelle Recycle_Bin zurückgeben
     */
    public ArrayList<Entry> getEntryListRecycleBinTable() {
        ArrayList<Entry> arrayList = (ArrayList<Entry>) this.entryListRecycleBinTable.getEntryObjectList();
        return arrayList;
    }


    /**
     * Gibt eine ArrayList mit den Entrys der Kategorie -1 aus der Tabelle Recycle_Bin zurück
     */
    public ArrayList<Entry> getEntryListRecycleBinTableRemoved(){
        ArrayList<Entry> arrayList = (ArrayList<Entry>) this.entryListRecycleBinTable.getEntryObjectList();
        ArrayList<Entry> returnList = new ArrayList<>();
        for (Entry entry : arrayList) {
            if (entry.getCategoryID() == -1) {
                returnList.add(entry);
            }
        }
        return returnList;
    }


    /**
     * Zugriff via View
     * Einträge aus Datenbanktabelle Entry holen und in Entry-Liste schreiben
     */
    public void FillEntryListFromDb() throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        ArrayList<Entry> arrayListEncrypted;
        arrayListEncrypted = dbService.readAllEntries();
        ArrayList<Entry> arrayListDecrypted = decryptEntryList(arrayListEncrypted);
        this.entryListEntrysTable.setEntryObjectList(arrayListDecrypted);
    }


    /**
     * Zugriff via View
     * Einträge aus Datenbanktabelle Recycle_Bin holen und in Entry-Liste schreiben
     */
    public void FillEntryListFromRecycleBin() throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        ArrayList<Entry> arrayListEncrypted;
        arrayListEncrypted = dbService.readAllEntriesFromRecycleBin();
        ArrayList<Entry> arrayListDecrypted = decryptEntryList(arrayListEncrypted);
        this.entryListRecycleBinTable.setEntryObjectList(arrayListDecrypted);
    }

    /**
     * Kein Zugriff via View
     * Verschlüsselte Daten entschlüsseln und als Liste zurückgeben
     */
    private ArrayList<Entry> decryptEntryList(ArrayList<Entry> arrayListEncrypted) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, SQLException {
        ArrayList<Entry> arrayListDecrypted = new ArrayList<>();
        for (Entry encryptedEntry : arrayListEncrypted) {
            Entry decryptedEntry = new Entry(encryptionService.decrypt(encryptedEntry.getTitle()), encryptionService.decrypt(encryptedEntry.getUsername()), encryptionService.decrypt(encryptedEntry.getPassword()), encryptionService.decrypt(encryptedEntry.getDescription()), encryptionService.decrypt(encryptedEntry.getUrl()), encryptedEntry.getCategoryID(), encryptedEntry.getDbID(), encryptedEntry.getEntryID(), encryptedEntry.getTimestamp());
            arrayListDecrypted.add(decryptedEntry);
        }
        return arrayListDecrypted;
    }


    /**
     * Kein Zugriff via View
     * Entry-Objekt zur Liste hinzufügen
     */
    private void addEntryToList(Entry entry) {
        entryListEntrysTable.addEntry(entry);
    }


    /**
     * Zugriff via View
     * Neuen Eintrag erstelen
     */
    public void newEntry(String title, String username, String password, String description, String url, int categoryID) throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        Entry newEntry = new Entry(title, username, password, description, url, categoryID);
        entryListEntrysTable.addEntry(newEntry);
        Entry newEntryEncrypted = createEncryptedEntry(newEntry);

        dbService.insertEntry(newEntryEncrypted);
    }


    /**
     * Zugriff via View
     * Bestehenden Datensatz ändern
     */
    public boolean updateEntry(String entryID, String title, String username, String password, String url, String descripton, int categoryID) throws SQLException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        ArrayList<Entry> entryArrayList = (ArrayList<Entry>) this.entryListEntrysTable.getEntryObjectList();
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

    /** Kein Zugriff via View
     * Ausgabe alle Entries aus Tabelle Entry auf Console
     */
    protected void soutEntryList() {
        ArrayList<Entry> entryArrayList = (ArrayList<Entry>) this.entryListEntrysTable.getEntryObjectList();
        for (Entry entry : entryArrayList) {
            System.out.println(entry);
        }
    }

    /** Kein Zugriff via View
     * Ausgabe alle Entries aus Tabelle Recycle_Bin auf Console
     */
    protected void soutEntryListRecycleBin() throws SQLException {
        ArrayList<Entry> entryArrayListRecycleBin = (ArrayList<Entry>) this.entryListRecycleBinTable.getEntryObjectList();
        for (Entry entry : entryArrayListRecycleBin) {
            System.out.println(entry);
        }
    }


    /** Kein Zugriff via View
     * Ausgabe alle Entries aus Tabelle Recycle_Bin mit Kategorie -1 (Status gelöscht) auf Console
     */
    protected void soutEntryListRecycleBinRemoved() throws SQLException {
        ArrayList<Entry> removedEntryArrayList = getEntryListRecycleBinTableRemoved();
        for (Entry entry : removedEntryArrayList) {
            System.out.println(entry);
        }
    }



    /**
     * Löscht einen Entry aus der Liste und Datenbank. Zudem wird die Category_ID für Einträge im Recycle_Bin auf -1 gesetzt.
     * Kategorie -1 bedeutet gelöscht in Entry-Tabelle
     */
    public boolean removeEntry(String entryIdAsString) throws SQLException {
        ArrayList<Entry> entryArrayList = (ArrayList<Entry>) this.entryListEntrysTable.getEntryObjectList();
        Iterator<Entry> iterator = entryArrayList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getEntryIDasString().equals(entryIdAsString)) {
                dbService.updateRecycleBinForRemovedEntrys(entryIdAsString);
                dbService.removeEntry(entryIdAsString);
                iterator.remove();
                System.out.println("##Status## Entry erfolgreich gelöscht!");
                return true;
            }
        }
        System.out.println("##Status## Entry konnte nicht gelöscht werden!");
        return false;
    }




}
