package jpasswortbunker.mgm.model;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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



}
