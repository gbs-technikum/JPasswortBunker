package model;

import java.net.URL;

public class Entry {

    private String title, description, url, username, password;
    private int dbID, entryID, categoryID;

    public Entry(String title, String description, String url, String username, String password, int dbID, int entryID, int categoryID) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.username = username;
        this.password = password;
        this.dbID = dbID;
        this.entryID = entryID;
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public int getDbID() {
        return dbID;
    }

    private void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public int getEntryID() {
        return entryID;
    }

    private void setEntryID(int entryID) {
        this.entryID = entryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    private void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void updateEntry(String title, String url, String description, String username, String password){
        this.setTitle(title);
        this.setUrl(url);
        this.setDescription(description);
        this.setUsername(username);
        this.setPassword(password);


    }

}
