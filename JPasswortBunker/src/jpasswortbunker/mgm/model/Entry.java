
package jpasswortbunker.mgm.model;

import java.sql.Timestamp;
import java.util.UUID;

public class Entry {

    private String title, description, url, username, password;
    private int dbID, categoryID;
    private UUID entryID;
    private long timestamp;


    private Entry(){
        this.entryID = UUID.randomUUID();
        this.categoryID = 0;
        timestamp = System.currentTimeMillis() / 1000L;
    }

    public Entry(String title, String username, String password){
        this();
        this.title = title;
        this.username = username;
        this.password = password;
    }

    public Entry(String title, String username, String password, String description) {
        this(title, username, password);
        this.description = description;
    }


    public Entry(String title, String username, String password, String description, String url) {
        this(title, username, password, description);
        this.url = url;
    }


    public Entry(String title, String username, String password, String description, String url, int categoryID) {
        this(title, username, password, description, url);
        this.categoryID = categoryID;
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

    public void setEntryID(UUID entryID) {
        this.entryID = entryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }


}
