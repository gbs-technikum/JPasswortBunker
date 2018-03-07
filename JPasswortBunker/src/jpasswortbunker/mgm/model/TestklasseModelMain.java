package jpasswortbunker.mgm.model;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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


        //Beschreibung: Einträge aus Datenbank in EntryListen laden (zur Initierung)
        modelMain.FillEntryListFromDb();
        modelMain.FillEntryListFromRecycleBin();

        //Beschreibung: ArrayList<Entry> mit allen Entry zurückgeben lassen
        modelMain.getEntryListEntrysTable();


        //Beschreibung: Neuen Eintrag erstellen (Wichtig: Leere Datenfelder müssen mit dem Wert 'null' übergeben werden)
        //modelMain.newEntry("GeilHub", "marcelP", "abc", "bla bla bla", "www.geilhub.com", 2);
        //modelMain.newEntry("Eintrag-ohne-Description2", "marcel", "abc", "null", "www.coolhub.com", 6);


        //Beschreibung: Bestehenden Eintrag abändern (alle Datenfelder müssen übergeben werden)
        //modelMain.updateEntry("0b362aa1-e515-4735-afbd-ddf0522e9a9c", "GayHub3", "marcel", "abc", "www.gayhub.com", "bla bla bla", 9);


        //Beschreibung: Eintrag löschen via EntryID
        //modelMain.removeEntry("0b362aa1-e515-4735-afbd-ddf0522e9a9c");


        //Beschreibung: Alle Entries in der EntryList (Tabelle Entrys) auf der Console ausgeben
        modelMain.soutEntryList();

        //Beschreibung: Alle Entries in der EntryList (Quelle Tabelle Recycle_Bin) mit Kategorie -1 (gelöscht) auf der Console ausgeben
        modelMain.soutRemovedEntryList();


        //ToDo Methode zum Zurückgeben aller bereits gelöschten Entries aus dem Recycle_Bin fehlt
        //modelMain.getRemovedEntries()


        //ToDo Methode zum Zurückgeben der Backup-Einträge zu einer Eintry-ID aus Recycle_Bin fehlt
        //modelMain.


        //ToDo Methode zum Abändern Masterpassword fehlt / bzw. funktioniert noch nicht
        //modelMain.renewMasterPassword("abc");


    }


}
