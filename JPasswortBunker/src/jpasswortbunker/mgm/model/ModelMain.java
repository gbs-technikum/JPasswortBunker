package jpasswortbunker.mgm.model;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ModelMain {

    private EntryList entryList;
    private PasswordObject masterPassword;
    private EncryptionService encryptionService;


    public ModelMain() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.entryList = new EntryList();
        PasswordObject.getInstance().setPassword("test");
        this.encryptionService = new EncryptionService();

    }

    public EntryList getEntryList() {
        return entryList;
    }

    //##############################################################################################
    //Einträge zur Liste hinzufügen
    public void addEntryToList(Entry entry) {
        entryList.addEntry(entry);
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
