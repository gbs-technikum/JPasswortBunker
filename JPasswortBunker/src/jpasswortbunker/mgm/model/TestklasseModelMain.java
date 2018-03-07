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

        //Beschreibung: Instanzierung von ModelMain
        ModelMain modelMain = new ModelMain();


        //Beschreibung: Masterpassword übergeben
        modelMain.initMasterPassword("test");


        //Beschreibung: Prüfen ob MasterPasswort richtig eingegeben wurde (Oben eingegebenes Passwort wird mit DB abgeglichen)
        System.out.println(modelMain.checkIfMasterPasswordIsCorrect());


        //Beschreibung: Instanzierung von EncryptionSerivce
        modelMain.initEncryptionService();


        //Beschreibung: Einträge aus Datenbank in EntryList laden (zur Initierung)
        modelMain.FillEntryListFromDb();


        //Beschreibung: ArrayList<Entry> mit allen Entry zurückgeben lassen
        modelMain.getEntryList();


        //Beschreibung: Neuen Eintrag erstellen (Wichtig: Leere Datenfelder müssen mit dem Wert 'null' übergeben werden)
        //modelMain.newEntry("PornHub", "marcel", "abc", "bla bla bla", "www.pornhub.com", 6);
        //modelMain.newEntry("Eintrag-ohne-Description2", "marcel", "abc", "null", "www.coolhub.com", 6);


        //Beschreibung: Bestehenden Eintrag abändern (alle Datenfelder müssen übergeben werden)
        //modelMain.updateEntry("2e264826-6f2a-462a-a114-68c14da385fa", "GayHub3", "marcel", "abc", "www.gayhub.com", "bla bla bla", 9);


        //Beschreibung: Eintrag löschen via EntryID
        //modelMain.removeEntry("ccd1efe1-dbd8-4ad3-8d5e-749a86c555b5");


        //Beschreibung: Alle Entries in der EntryList auf der Console ausgeben
        modelMain.soutEntryList();

        //ToDo Methode zum Zurückgeben aller bereits gelöschten Entries aus dem RecycleController fehlt
        //modelMain.getRemovedEntries()

        //ToDo Methode zum Abändern Masterpassword fehlt / bzw. funktioniert noch nicht
        //modelMain.renewMasterPassword("abc");


    }


}
