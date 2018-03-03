package jpasswortbunker.mgm.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class DBService {


    private static final String URL = "jdbc:sqlite:C:\\Users\\guenther\\IdeaProjects\\JPasswortBunker\\jPasswordBunker.db";
    //Pfad DB im Ressource Folder:
    //private static final String URL = "jdbc:sqlite:ressources/passwordbunker.db";
    private Connection connection;
    private Statement statement;


    public DBService() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.statement = connection.createStatement();
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


    public ArrayList<Entry> readAllEntries() throws SQLException {
        String sql = "SELECT * FROM Entrys";
        ArrayList<Entry> entryArrayList = readEntries(sql);
        return entryArrayList;
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
        String sql = "SELECT MAX(DB_ID) FROM Entrys;";
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


    public void setMasterPasswordToDB(String password) throws SQLException {
        String sql = "update Masterkey set password = '" + password + "' where id = 1";
        this.statement.execute(sql);
        statement.close();
    }


    public void insertEntryInRecycleBin(Entry entry) throws SQLException {
        String sql = "insert into Recycle_Bin (Entry_ID, Title, Username, Password, URL, Description, Categorie_ID, timestamp) values('" + entry.getEntryID() + "','" + entry.getTitle() + "','" + entry.getUsername() + "','" + entry.getPassword() + "','" + entry.getUrl() + "','" + entry.getDescription() + "','" + entry.getCategoryID() + "','" + entry.getTimestamp() + "');";
        this.statement.execute(sql);
        statement.close();
    }

    public void reEnryptEntry(String title, String username, String password, String description, String url, UUID entryID) throws SQLException {
        String sql = "update Entrys set Title = '" + title + "' where DB_ID = '" + entryID.toString() + "'";
        this.statement.execute(sql);
        statement.close();
    }
}
