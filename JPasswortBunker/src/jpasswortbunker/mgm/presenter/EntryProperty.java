package jpasswortbunker.mgm.presenter;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;
import java.util.UUID;

public class EntryProperty extends RecursiveTreeObject<EntryProperty> {

    private IntegerProperty dbID, categoryID;
    private UUID entryID;
    private StringProperty title, username, password, url, description;



    public EntryProperty(int dbID, UUID entryID, String title, String username, String password, String url, String description, int categorieID) {
        this.dbID = new SimpleIntegerProperty(dbID);
        this.entryID = entryID;
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.url = new SimpleStringProperty(url);
        this.description = new SimpleStringProperty(description);
        this.categoryID = new SimpleIntegerProperty(categorieID);
    }

    public EntryProperty(EntryProperty entryProperty) {
        this.dbID = new SimpleIntegerProperty(entryProperty.getDbID());
        this.entryID = entryProperty.getEntryID();
        this.title = new SimpleStringProperty(entryProperty.getTitle());
        this.username = new SimpleStringProperty(entryProperty.getUsername());
        this.password = new SimpleStringProperty(entryProperty.getPassword());
        this.url = new SimpleStringProperty(entryProperty.getUrl());
        this.description = new SimpleStringProperty(entryProperty.getDescription());
        this.categoryID = new SimpleIntegerProperty(entryProperty.getCategoryID());
    }

    public int getDbID() {
        return dbID.get();
    }

    public IntegerProperty dbIDProperty() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID.set(dbID);
    }

    public int getCategoryID() {
        return categoryID.get();
    }

    public IntegerProperty categoryIDProperty() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID.set(categoryID);
    }

    public UUID getEntryID() {
        return entryID;
    }

    public void setEntryID(UUID entryID) {
        this.entryID = entryID;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntryProperty entryProperty = (EntryProperty) o;
        return Objects.equals(dbID, entryProperty.dbID) &&
                Objects.equals(categoryID, entryProperty.categoryID) &&
                Objects.equals(entryID, entryProperty.entryID) &&
                Objects.equals(title, entryProperty.title) &&
                Objects.equals(username, entryProperty.username) &&
                Objects.equals(password, entryProperty.password) &&
                Objects.equals(url, entryProperty.url) &&
                Objects.equals(description, entryProperty.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dbID, categoryID, entryID, title, username, password, url, description);
    }

    @Override
    public String toString() {
        return "EntryProperty " +
                "\t dbID: " + dbID +
                "\t entryID: " + entryID +
                "\t title: " + title.getValue() +
                "\t username: " + username.getValue() +
                "\t password: " + password.getValue() +
                "\t url: " + url.getValue() +
                "\t description: " + description.getValue() +
                "\t categorieID: " + categoryID.getValue() +
                '}';
    }
}