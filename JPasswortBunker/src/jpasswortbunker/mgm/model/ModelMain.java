package jpasswortbunker.mgm.model;

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

    private PasswordObject masterPassword;
    private EntryList entryList;
    private EncryptionService encryptionService;
    private DBService dbService;


    public ModelMain() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
        PasswordObject.getInstance().setPassword("test");
        this.entryList = new EntryList();
        this.encryptionService = new EncryptionService();
        this.dbService = new DBService();
    }

    /**ArrayList aus Klasse EntryList zurückgeben
     *
     * @return
     */
    public ArrayList<Entry> getEntryList() {
        ArrayList<Entry> arrayList = (ArrayList<Entry>) this.entryList.getEntryObjectList();
        return arrayList;
    }

    /**Objekt aus Datenbank holen und in Liste schreiben
     *
     * @throws SQLException
     */
    public void FillEntryListFromDb() throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        ArrayList<Entry> arrayListEncrypted;
        arrayListEncrypted = dbService.readAllEntries();
        ArrayList<Entry> arrayListDecrypted = new ArrayList<>();

        for (Entry encryptedEntry : arrayListEncrypted) {
            Entry decryptedEntry = new Entry(encryptionService.decrypt(encryptedEntry.getTitle()),encryptionService.decrypt(encryptedEntry.getUsername()),encryptionService.decrypt(encryptedEntry.getPassword()),encryptionService.decrypt(encryptedEntry.getDescription()),encryptionService.decrypt(encryptedEntry.getUrl()),encryptedEntry.getCategoryID(),encryptedEntry.getDbID(),encryptedEntry.getEntryID(),encryptedEntry.getTimestamp());
            arrayListDecrypted.add(decryptedEntry);
        }
        this.entryList.setEntryObjectList(arrayListDecrypted);
    }


    /**Entry-Objekt zur Liste hinzufügen
     *
     * @param entry
     */
    public void addEntryToList(Entry entry) {
        entryList.addEntry(entry);
    }


    public void newEntry(String title, String username, String password, String description, String url, int categoryID) throws SQLException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        Entry newEntry = new Entry(title, username, password, description, url, categoryID);
        entryList.addEntry(newEntry);
        Entry newEntryEncrypted = new Entry(encryptionService.encrypt(title), encryptionService.encrypt(username), encryptionService.encrypt(password), encryptionService.encrypt(description), encryptionService.encrypt(url), categoryID);
        dbService.insertEntry(newEntryEncrypted);
    }



    /*public void addEntryToList(String title, String username, String password, String repeatPassword) {
        if (equalsPassword(password, repeatPassword)) {
            entryList.addEntry(new Entry(title, username, password));
        }
    }


    public void addEntryToList(String title, String username, String password, String repeatPassword, String description, String url) {
        if (equalsPassword(password, repeatPassword)) {
            entryList.addEntry(new Entry(title, username, password, description, url));
        }
    }

    public void addEntryToList(String title, String username, String password, String repeatPassword, String description, String url, int categoryID) {
        if (equalsPassword(password, repeatPassword)) {
            entryList.addEntry(new Entry(title, username, password, description, url, categoryID));
        }
    }*/

    //überprüfung ob die eingegebene Passwörter übereinstimmen
    //Wird für Entry's und Masterpasswort verwendet
    private boolean equalsPassword(String password, String repeatPassword) {
        if (password.equals(repeatPassword)) {
            return true;
        }
        System.out.println("Passwörter stimmen nicht überein, Entry wurde nicht erstellt");
        return false;
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
