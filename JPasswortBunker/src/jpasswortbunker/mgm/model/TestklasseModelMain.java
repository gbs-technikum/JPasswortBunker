package jpasswortbunker.mgm.model;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class TestklasseModelMain {

    public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, SQLException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {


        //Beschreibung: Instanzierung von ModelMain
        ModelMain modelMain = new ModelMain();


        //Beschreibung: Prüfen ob in Datenbank bereits ein MasterPasswort existiert
        modelMain.checkIfMasterPasswortExistsInDB();


        //Beschreibung: Zufallspasswort erzeugen
        //modelMain.createPassword();


        //Beschreibung: Masterpassword übergeben
        modelMain.initMasterPassword("test");


        //Beschreibung: Prüfen ob richtigs MasterPasswort übergeben wurde (Oben eingegebenes Passwort wird mit DB abgeglichen)
        System.out.println(modelMain.checkIfMasterPasswordIsCorrect());


        //Beschreibung: Zeitspanne in Sekunden für Zwischenablage aus DB abfragen bzw. Wert setzen (Defaultwert = 60)
        modelMain.getTimePeriodForClipboardFromDB();
        //modelMain.setTimePeriodForClipboardToDB(60);


        //Beschreibung: Instanzierung von EncryptionSerivce
        modelMain.initEncryptionService();


        //Beschreibung: Einträge aus Datenbank in EntryListen laden (zur Initierung)
        modelMain.FillEntryListFromDb();
        modelMain.FillEntryListFromRecycleBin();


        //Beschreibung: ArrayList<Entry> mit allen Entrys aus Tabelle Entry zurückgeben lassen
        modelMain.getEntryListEntrysTable();


        //Beschreibung: ArrayList<Entry> mit allen Entrys aus Tabelle Recycle_Bin zurückgeben lassen
        modelMain.getEntryListRecycleBinTable();


        //Beschreibung: ArrayList<Entry> mit allen Entrys aus Tabelle Recycle_Bin zurückgeben lassen, die der übergebenen entryID entsprechen
        modelMain.getEntrysFromRecycleBinForEntryID("2e264826-6f2a-462a-a114-68c14da385fa");


        //Beschreibung: ArrayList<Entry> mit allen Entrys aus Tabelle Recycle_Bin mit Kategorie -1 (Status gelöscht) zurückgeben lassen
        modelMain.getEntryListRecycleBinTableRemoved();


        //Beschreibung: Neuen Eintrag erstellen (Wichtig: Leere Datenfelder müssen mit dem Wert 'null' übergeben werden)
        //modelMain.newEntry("DeleteTest2", "marcelP", "abc", "bla bla bla", "www.geilhub.com", 2);
        //modelMain.newEntry("Eintrag-ohne-Description2", "marcel", "abc", "null", "www.coolhub.com", 6);


        //Beschreibung: Bestehenden Eintrag abändern (alle Datenfelder müssen übergeben werden)
        //modelMain.updateEntry("382d44bb-eb25-4a09-8c03-5c43b2d71979", "DeleteTestgeaendert", "marcel", "abc", "www.gayhub.com", "bla bla bla", 9);


        //Beschreibung: Eintrag löschen via EntryID
        //modelMain.removeEntry("382d44bb-eb25-4a09-8c03-5c43b2d71979");


        //Beschreibung: Masterpassword neu setzen (alle bestehenden Einträge werden neu verschlüsselt)
        //modelMain.renewMasterPassword("neuesPasswort");


        //Beschreibung: Alle Kategorien holen
        System.out.println("Alle Kategorien ausgeben:");
        System.out.println(modelMain.addNewCategoriyToDB("Testeintrag aus Testklasse2"));
        List<String> kategorieliste = modelMain.getCategoryListFromDB();
        for (String s : kategorieliste) {
            System.out.println(s);
        }



        //ToDo Klasse zum wiederherstellen eines gelöschten BackupEintrages


        //ToDo Speicherort für Datenbank export prüfen


        //Todo Max. drei Änderungen in Recycle_Bin schreiben


        //Todo Methoden zum Hinzufügen / Entfernen / Abfragen von Kategorien




        //#######################Testausgabe auf Console################################
        //==============================================================================


        //Beschreibung: Alle Entries in der EntryList (Tabelle Entrys) auf der Console ausgeben
        System.out.println("\n");
        System.out.println("###############Ausgabe alle Entries aus Entry-Table###################");
        modelMain.soutEntryList();


        //Beschreibung: Alle Entries in der EntryList (Quelle Tabelle Recycle_Bin) auf der Console ausgeben
        System.out.println("\n");
        System.out.println("###############Ausgabe alle Entries aus Recycle_Bin###################");
        modelMain.soutEntryListRecycleBin();


        //Beschreibung: Alle Entries in der EntryList (Quelle Tabelle Recycle_Bin) mit Kategorie -1 (gelöscht) auf der Console ausgeben
        System.out.println("\n");
        System.out.println("###############Ausgabe alle Entries aus Recycle_Bin mit Status gelöscht###################");
        modelMain.soutEntryListRecycleBinRemoved();


        //Beschreibung: Alle Entries in der EntryList (Quelle Tabelle Recycle_Bin) mit Kategorie -1 (gelöscht) auf der Console ausgeben
        System.out.println("\n");
        System.out.println("###############Ausgabe alle Entries aus Recycle_Bin für übergebene entryID ausgaben###################");
        modelMain.soutEntryListRecycleBinForEntryID("2e264826-6f2a-462a-a114-68c14da385fa");


    }


}
