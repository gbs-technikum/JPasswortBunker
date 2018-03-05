
package jpasswortbunker.mgm.model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class Entry {

    private String title, description, url, username, password;
    private int dbID, categoryID;
    private UUID entryID;
    private long timestamp;
    private DBService dbService;

    private Entry() throws SQLException {
        setMetaData();
    }

    public Entry(String title, String username, String password) throws SQLException {
        this();
        this.title = title;
        this.username = username;
        this.password = password;
        setMetaData();
    }


    public Entry(String title, String username, String password, String description) throws SQLException {
        this(title, username, password);
        this.description = description;
        setMetaData();
    }


    public Entry(String title, String username, String password, String description, String url) throws SQLException {
        this(title, username, password, description);
        this.url = url;
        setMetaData();
    }


    public Entry(String title, String username, String password, String description, String url, int categoryID) throws SQLException {
        this(title, username, password, description, url);
        this.categoryID = categoryID;
        setMetaData();
    }


    public Entry(String title, String username, String password, String description, String url, int categoryID, int dbID, UUID entryID, long timestamp) throws SQLException {
        this.title = title;
        this.description = description;
        this.url = url;
        this.username = username;
        this.password = password;
        this.dbID = dbID;
        this.categoryID = categoryID;
        this.entryID = entryID;
        this.timestamp = timestamp;
    }


    private void setMetaData() throws SQLException {
        dbService = new DBService();
        dbID = dbService.getNextDbId();
        dbService.close();
        dbService = null;

        if (entryID == null) {
            this.entryID = UUID.randomUUID();
        }

        if (timestamp == 0) {
            this.timestamp = this.createTimeStamp();
        }

    }

    public long createTimeStamp(){
        long newTimestamp = System.currentTimeMillis() / 1000L;
        return newTimestamp;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public UUID getEntryID() {
        return entryID;
    }

    public String getEntryIDasString(){
        return this.entryID.toString();
    }

    public void setEntryID(UUID entryID) {
        this.entryID = entryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        String ausgabe = this.dbID + " " + this.entryID + " " + this.title + " " + this.username + " " + this.password + " " + this.url + " " + this.description + " " + this.categoryID + " " + this.timestamp;

        return ausgabe;
    }
}
