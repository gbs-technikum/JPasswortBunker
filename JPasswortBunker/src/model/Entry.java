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




}
