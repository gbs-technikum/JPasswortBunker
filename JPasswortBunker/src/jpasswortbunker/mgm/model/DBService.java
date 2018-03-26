package jpasswortbunker.mgm.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class DBService {


    //private static final String URL = "jdbc:sqlite:C:\\Users\\guenther\\IdeaProjects\\JPasswortBunker\\jPasswordBunker.db";

    //Pfad zu aktueller Testdatenbank im Projektordner
    private static final String URL = "jdbc:sqlite:"+System.getProperty("user.dir")+"/jPasswortBunker.db";
    //private static final String URL = "jdbc:sqlite:jPasswortBunker.db";
    //private static final String URL = "jdbc:sqlite::resource:jPasswortBunker.db";
    //private static final String URL = "jdbc:sqlite:Database:jPasswortBunker.db";


    private Connection connection;
    private Statement statement;


    public DBService() throws SQLException {
        //Java-Property Temp-Verzeichnis für Sqlite setzen:
        System.setProperty("java.io.tmpdir", System.getProperty("user.dir"));
        //System.setProperty("org.sqlite.tmpdir", System.getProperty("user.dir"));

        this.connection = DriverManager.getConnection(URL);
        this.statement = connection.createStatement();


    }


    public ArrayList<Entry> readAllEntries() throws SQLException {
        String sql = "SELECT * FROM Entrys";
        ArrayList<Entry> entryArrayList = readEntries(sql);
        return entryArrayList;
    }


    public ArrayList<Entry> readAllEntriesFromRecycleBin() throws SQLException {
        //String sql = "SELECT * FROM Recycle_Bin where Categorie_ID = '-1'";
        String sql = "SELECT * FROM Recycle_Bin";
        ArrayList<Entry> entryArrayList = readEntries(sql);
        return entryArrayList;
    }


    public ArrayList<Entry> readEntries(String sql) throws SQLException {

        ResultSet resultSet = this.statement.executeQuery(sql);

        Entry entry = null;
        List<Entry> entryArrayList = new ArrayList<>();

        while (resultSet.next()) {
            int db_id = resultSet.getInt(1);
            UUID entry_id = UUID.fromString(resultSet.getString(2));
            String title = resultSet.getString(3);
            String username = resultSet.getString(4);
            String password = resultSet.getString(5);
            String url = resultSet.getString(6);
            String description = resultSet.getString(7);
            int categorie_id = resultSet.getInt(8);
            long timestamp = resultSet.getLong(9);
            entry = new Entry(title, username, password, description, url, categorie_id, db_id, entry_id, timestamp);
            entryArrayList.add(entry);

        }
        resultSet.close();
        statement.close();
        return (ArrayList<Entry>) entryArrayList;
    }


    public Entry readSingleEntry(int id) throws SQLException {
        String sql = "SELECT * FROM Entrys WHERE DB_ID = " + String.valueOf(id);
        Entry entry = null;
        ArrayList<Entry> entryArrayList = readEntries(sql);
        Iterator<Entry> iterator = entryArrayList.iterator();
        if (iterator.hasNext()) {
            entry = iterator.next();
        }
        return entry;
    }


    public Entry readSingleEntryFromRecycleBin(String entryId, long timestamp) throws SQLException {
        Entry entry = null;
        String sql = "SELECT * FROM Recycle_Bin WHERE Entry_ID = '" + entryId + "' and timestamp = '" + timestamp + "'";
        ResultSet resultSet = this.statement.executeQuery(sql);
        entry = buildEntryFromDB(resultSet);
        return entry;
    }


    public Entry buildEntryFromDB(ResultSet resultSet) throws SQLException {
        Entry entry = null;
        if (resultSet.next()) {
            int db_id = resultSet.getInt(1);
            UUID entry_id = UUID.fromString(resultSet.getString(2));
            String title = resultSet.getString(3);
            String username = resultSet.getString(4);
            String password = resultSet.getString(5);
            String url = resultSet.getString(6);
            String description = resultSet.getString(7);
            int categorie_id = resultSet.getInt(8);
            long timestamp = resultSet.getLong(9);
            entry = new Entry(title, username, password, description, url, categorie_id, db_id, entry_id, timestamp);
        }
        return entry;
    }


    public void insertEntry(Entry entry) throws SQLException {
        String sql = "insert into Entrys (Entry_ID, Title, Username, Password, URL, Description, Categorie_ID, timestamp) values('" + entry.getEntryID() + "','" + entry.getTitle() + "','" + entry.getUsername() + "','" + entry.getPassword() + "','" + entry.getUrl() + "','" + entry.getDescription() + "','" + entry.getCategoryID() + "','" + entry.getTimestamp() + "');";
        this.statement.execute(sql);
        statement.close();
    }


    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }

    public int getNextDbId() throws SQLException {
        String sql = "select seq from sqlite_sequence where name='Entrys'";
        ResultSet resultSet = this.statement.executeQuery(sql);
        int amountEntries = -1;
        if (resultSet.next()) {
            amountEntries = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        return ++amountEntries;
    }


    public String getMasterPasswordFromDB() throws SQLException {
        String sql = "select password from Masterkey where id = 1;";
        ResultSet resultSet = this.statement.executeQuery(sql);
        String password = "";
        if (resultSet.next()) {
            password = resultSet.getString(1);
        }
        resultSet.close();
        statement.close();
        return password;
    }


    public boolean checkIfMasterPasswordExistsInDB() throws SQLException {
        if (getMasterPasswordFromDB().equals("")) {
            return false;
        }
        return true;
    }


    public boolean setMasterPasswordToDB(String password) throws SQLException {
        String sql = "update Masterkey set password = '" + password + "' where id = 1";
        if (this.statement.execute(sql)) {
            statement.close();
            return true;
        }
        statement.close();
        return false;
    }


    public void insertEntryInRecycleBin(Entry entry) throws SQLException {
        String sql = "insert into Recycle_Bin (Entry_ID, Title, Username, Password, URL, Description, Categorie_ID, timestamp) values('" + entry.getEntryID() + "','" + entry.getTitle() + "','" + entry.getUsername() + "','" + entry.getPassword() + "','" + entry.getUrl() + "','" + entry.getDescription() + "','" + entry.getCategoryID() + "','" + entry.getTimestamp() + "');";
        this.statement.execute(sql);
        statement.close();
    }


    public void updateEntry(Entry entry) throws SQLException {
        String sqlUpdateTitle = "update Entrys set Title = '" + entry.getTitle() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateUsername = "update Entrys set Username = '" + entry.getUsername() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdatePassword = "update Entrys set Password = '" + entry.getPassword() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateUrl = "update Entrys set URL = '" + entry.getUrl() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateDescription = "update Entrys set Description = '" + entry.getDescription() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateCategory = "update Entrys set Categorie_ID = '" + entry.getCategoryID() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateTimeStamp = "update Entrys set timestamp = '" + entry.getTimestamp() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";

        this.statement.execute(sqlUpdateTitle);
        this.statement.execute(sqlUpdateUsername);
        this.statement.execute(sqlUpdatePassword);
        this.statement.execute(sqlUpdateDescription);
        this.statement.execute(sqlUpdateUrl);
        this.statement.execute(sqlUpdateCategory);
        this.statement.execute(sqlUpdateTimeStamp);

        statement.close();
    }


    public void reEncryptTable(Entry entry, String tableName) throws SQLException {
        String sqlUpdateTitle = "update " + tableName + " set Title = '" + entry.getTitle() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateUsername = "update " + tableName + " set Username = '" + entry.getUsername() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdatePassword = "update " + tableName + " set Password = '" + entry.getPassword() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateUrl = "update " + tableName + " set URL = '" + entry.getUrl() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateDescription = "update " + tableName + " set Description = '" + entry.getDescription() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";
        String sqlUpdateCategory = "update " + tableName + " set Categorie_ID = '" + entry.getCategoryID() + "' where Entry_ID = '" + entry.getEntryIDasString() + "'";

        this.statement.execute(sqlUpdateTitle);
        this.statement.execute(sqlUpdateUsername);
        this.statement.execute(sqlUpdatePassword);
        this.statement.execute(sqlUpdateDescription);
        this.statement.execute(sqlUpdateUrl);
        this.statement.execute(sqlUpdateCategory);

        statement.close();
    }


    public void removeEntry(String entryID) throws SQLException {
        String sql = "delete from Entrys where Entry_ID = '" + entryID + "'";
        this.statement.execute(sql);
        statement.close();
    }


    public void updateRecycleBinForRemovedEntrys(String entryID) throws SQLException {
        String sql = "update Recycle_Bin set Categorie_ID = '-1' where Entry_ID = '" + entryID + "'";
        this.statement.execute(sql);
        statement.close();
    }


    public int getTimePeriodForClipboardFromDB() throws SQLException {
        String sql = "select Cache_Time from System where id=1";
        ResultSet resultSet = this.statement.executeQuery(sql);
        int cacheTime = -1;
        if (resultSet.next()) {
            cacheTime = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        return cacheTime;
    }


    public void setTimePeriodForClipboardToDB(int timePeriodinSeconds) throws SQLException {
        String sql = "update System set Cache_Time = '" + timePeriodinSeconds + "' where id = 1";
        this.statement.execute(sql);
        statement.close();
    }



    public int getLenthOfRandomPasswordsFromDB() throws SQLException {
        String sql = "select LengthOfRandomPasswords from System where id=1";
        ResultSet resultSet = this.statement.executeQuery(sql);
        int cacheTime = -1;
        if (resultSet.next()) {
            cacheTime = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        return cacheTime;
    }


    public void setLengthOfRandomPasswordsToDB(int lengthOfRandomPasswords) throws SQLException {
        String sql = "update System set LengthOfRandomPasswords = '" + lengthOfRandomPasswords + "' where id = 1";
        this.statement.execute(sql);
        statement.close();
    }




    public int getNumberOfBackupEntiresFromDB() throws SQLException {
        String sql = "select NumberOfBackupEntries from System where id=1";
        ResultSet resultSet = this.statement.executeQuery(sql);
        int numberOfBackupEntries = -1;
        if (resultSet.next()) {
            numberOfBackupEntries = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        return numberOfBackupEntries;
    }



    public boolean setNumberOfBackupEntiresToDB(int numberEntries) throws SQLException {
        String sql = "update System set NumberOfBackupEntries = '" + numberEntries + "' where id = 1";
        this.statement.execute(sql);
        statement.close();
        return true;
    }



    public ArrayList<String> getCategoryListFromDB() throws SQLException {
        String sql = "select * from Categorie";
        ResultSet resultSet = this.statement.executeQuery(sql);
        ArrayList<String> categoryList = new ArrayList<>();
        String categoryName;
        while (resultSet.next()) {
            categoryName = resultSet.getString(2);
            categoryList.add(categoryName);
        }
        resultSet.close();
        statement.close();
        return categoryList;
    }


    public void addNewCategory(String name) throws SQLException {
        String sql = "insert into Categorie (Categorie_Name) VALUES ('" + name + "')";
        this.statement.execute(sql);
        statement.close();
    }


    public void removeCategory(int id) throws SQLException {
        String sql = "delete from Categorie where Categorie_id = " + id;
        this.statement.execute(sql);
        statement.close();
    }


    public void resetIdInRecycleBinForRestoredEntry(String entryID, int categoryId) throws SQLException {
        String sql = "update Recycle_Bin set Categorie_ID = '" + categoryId + "' where Entry_ID = '" + entryID + "'";
        this.statement.execute(sql);
        statement.close();
    }


    public void deleteEntrytoAvoidDuplicate(String entryId) throws SQLException {
        String sql = "delete from Entrys where Entry_ID = '" + entryId + "'";
        this.statement.execute(sql);
        statement.close();
    }


    public void deleteOldestEntryFromRecycleBin(String entryId, long timestamp) throws SQLException {
        String sql = "delete from Recycle_Bin where Entry_ID = '" + entryId + "' and timestamp = '" + timestamp + "'";
        this.statement.execute(sql);
        statement.close();
    }


    public int getNumberOfExistingRecycleBinEntriesForEntryId(String entryID) throws SQLException {
        String sql = "select count(*) from Recycle_Bin where Entry_ID = '" + entryID +"'";
        ResultSet resultSet = this.statement.executeQuery(sql);
        int numberOfEntriesInRecycleBinForEntryId = -1;
        if (resultSet.next()) {
            numberOfEntriesInRecycleBinForEntryId = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        return numberOfEntriesInRecycleBinForEntryId;
    }


    public int getOldestTimeStampForEntryIdFromRecycleBin(String entryID) throws SQLException {
        String sql = "select min(timestamp) from Recycle_Bin where Entry_id = '" + entryID + "'";
        ResultSet resultSet = this.statement.executeQuery(sql);
        int oldestTimestampOfEntriesInRecycleBinForEntryId = -1;
        if (resultSet.next()) {
            oldestTimestampOfEntriesInRecycleBinForEntryId = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        return oldestTimestampOfEntriesInRecycleBinForEntryId;
    }





}
