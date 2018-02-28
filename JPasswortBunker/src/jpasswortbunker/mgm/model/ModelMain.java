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
        this.encryptionService = new EncryptionService();

    }

    public void addEntryToList(Entry entry) {
        entryList.addEntry(entry);
    }

    public void removeEntryFromList(Entry entry) {
        entryList.removeEntry(entry);
    }


    public void removeEntryFromList(UUID uuid) {
        entryList.removeEntry(uuid);
    }

    public void removeEntryFromList(String title) {
        entryList.removeEntry(title);
    }



}
