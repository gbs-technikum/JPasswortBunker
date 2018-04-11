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


        System.out.println(System.getProperty("user.dir"));

        //Beschreibung: Instanzierung von ModelMain
        ModelMain modelMain = new ModelMain();


        //Beschreibung: Prüfen ob in Datenbank bereits ein MasterPasswort existiert
        modelMain.checkIfMasterPasswortExistsInDB();


        //Beschreibung: Zufallspasswort erzeugen
        //modelMain.createPassword();


        //Beschreibung: Masterpassword übergeben
        modelMain.initMasterPassword("test");


        //Beschreibung: Prüfen ob richtigs MasterPasswort übergeben wurde (Oben eingegebenes Passwort wird mit DB abgeglichen)
        System.out.print("Check ob MasterPassword in DB mit Eingabe übereinstimmt: ");
        System.out.println(modelMain.checkIfMasterPasswordIsCorrect());


        //Beschreibung: Zeitspanne in Sekunden für Zwischenablage aus DB abfragen bzw. Wert setzen
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
        //modelMain.newEntry("TestForDeleteWhileUpdate", "marcelP", "abc", "bla bla bla", "www.geilhub.com", 2);
        //modelMain.newEntry("Eintrag-ohne-Description2", "marcel", "abc", "null", "www.coolhub.com", 6);


        //Beschreibung: Bestehenden Eintrag abändern (alle Datenfelder müssen übergeben werden)
        //modelMain.updateEntry("742582fe-cb14-41fb-a9d1-1c8b4f7a54a8", "DeleteTestRestore4update2", "hans3", "abc", "www.gayhub.com", "bla bla bla", 9);


        //Beschreibung: Eintrag löschen aus Entrys-Tabelle via EntryID
        //modelMain.removeEntry("8bcde5bf-f1be-4fd5-9bf7-64aeefbed7ec");


        //Beschreibung: Einträge endgültig aus Recycle_Bin Tabelle löschen via EntryID
        //modelMain.removeEntriesFromRecycleBinFinal("71b6e4ea-fc39-4fb8-8fff-9be472eeb556");


        //Beschreibung: Eintrag von Papierkorb wiederherstellen anhand von entryID und Timestamp
        //modelMain.restoreEntryFromRecycleBin("1d382a53-23d7-460f-bf63-dbaf2aaf4468", 1521021055);


        //Beschreibung: Masterpassword neu setzen (alle bestehenden Einträge werden neu verschlüsselt)
        /*modelMain.renewMasterPassword("test");
        modelMain.getEntryListEntrysTable();
        modelMain.getEntryListRecycleBinTable();*/


        //Beschreibung: Alle Kategorien als ArrayList holen (Positionsnummer = Kategorienummer | Position 0 = Uncategorized
        //modelMain.getCategoryListFromDB();


        //Beschreibung: Neue Kategorie hinzufügen - liefert "False" zurück, sofern der übergebene Name bereits existiert
        //System.out.println(modelMain.addNewCategoryToDB("NeuerCategorieName"));


        //Beschreibung: Bestehende Kategorie löschen - liefert "False" zurück, sofern noch Entries mit dieser Kategorie verknüpft sind
        //modelMain.removeCategoryFromDB(10);


        //Beschreibung: Maximale Anzahl von BackupEntries für RecycleBin setzen
        //modelMain.setNumberOfBackupEntiresToDB(3);


        //Beschreibung: Maximale Anzahl von BackupEntries in RecycleBin abfragen
        //modelMain.getNumberOfBackupEntriesFromDB();


        //Beschreibung: Länge für Zufallspasswörter setzen
        //modelMain.setLengthOfRandomPasswordsToDB(30);


        //Beschreibung: Länge für Zufallspasswörter von DB abfragen
        //System.out.println(modelMain.getLengthOfRandomPasswordsFromDB());


        //modelMain.restoreEntryFromRecycleBin("f99cb4b0-dbf9-4880-8915-fe8ab1386ae3", 1521378808);
        System.out.println("######################");

        for(Entry entry: modelMain.getEntryListRecycleBinTableLatestTimestamp()){
            System.out.println(entry);
        };
        System.out.println("######################");

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
        /*System.out.println("\n");
        System.out.println("###############Ausgabe alle Entries aus Recycle_Bin mit Status gelöscht###################");
        modelMain.soutEntryListRecycleBinRemoved();*/


        //Beschreibung: Alle Entries in der EntryList (Quelle Tabelle Recycle_Bin) mit Kategorie -1 (gelöscht) auf der Console ausgeben
        /*System.out.println("\n");
        System.out.println("###############Ausgabe alle Entries aus Recycle_Bin für übergebene entryID ausgaben###################");
        modelMain.soutEntryListRecycleBinForEntryID("2e264826-6f2a-462a-a114-68c14da385fa");*/


    }


}
