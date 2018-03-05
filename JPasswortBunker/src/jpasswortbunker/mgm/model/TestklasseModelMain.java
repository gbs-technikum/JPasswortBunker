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

public class TestklasseModelMain {

    public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {

        //Instanz von ModelMain erstellen und Masterpasswort übergeben
        ModelMain modelMain = new ModelMain("test");


        //Prüfen ob MasterPasswort richtig eingegeben wurde (Oben eingegebenes Passwort wird mit DB abgeglichen)
        System.out.println(modelMain.checkIfMasterPasswordIsCorrect());


        //Einträge aus DB in EntryList laden (initial)
        modelMain.FillEntryListFromDb();


        //Neuen Eintrag erstellen
        //modelMain.newEntry("PornHub", "marcel", "abc", "bla bla bla", "www.pornhub.com", 6);

//ToDo Funktion funktioniert noch nicht!!
        //Bestehenden Eintrag abändern
        modelMain.updateEntry("9c7269fb-41ae-4d76-a761-c79ed16f65c9", "GayHub", "marcel", "abc", "www.gayhub.com", "bla bla bla", 9);

        //EntryList holen und alle Entries auf der Console ausgeben
        ArrayList<Entry> arrayList = modelMain.getEntryList();
        for (Entry entry : arrayList) {
            System.out.println(entry);
        }

        //modelMain.renewMasterPassword("abc");


    }


}
