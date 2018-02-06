package jpasswortbunker.mgm.entry;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Entry extends RecursiveTreeObject<Entry> {

    private IntegerProperty dbID;
    private IntegerProperty entryID;
    private StringProperty title;
    private StringProperty username;
    private StringProperty password;
    private StringProperty url;
    private StringProperty description;
    private IntegerProperty categorieID;


    public Entry(String title, String username, String password, String url, String description, int categorieID) {
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.url = new SimpleStringProperty(url);
        this.description = new SimpleStringProperty(description);
        this.categorieID = new SimpleIntegerProperty(categorieID);
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

    public int getEntryID() {
        return entryID.get();
    }

    public IntegerProperty entryIDProperty() {
        return entryID;
    }

    public void setEntryID(int entryID) {
        this.entryID.set(entryID);
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

    public int getCategorieID() {
        return categorieID.get();
    }

    public IntegerProperty categorieIDProperty() {
        return categorieID;
    }

    public void setCategorieID(int categorieID) {
        this.categorieID.set(categorieID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(dbID, entry.dbID) &&
                Objects.equals(entryID, entry.entryID) &&
                Objects.equals(title, entry.title) &&
                Objects.equals(username, entry.username) &&
                Objects.equals(password, entry.password) &&
                Objects.equals(url, entry.url) &&
                Objects.equals(description, entry.description) &&
                Objects.equals(categorieID, entry.categorieID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dbID, entryID, title, username, password, url, description, categorieID);
    }

    @Override
    public String toString() {
        return "Entry " +
                "\t dbID: " + dbID +
                "\t entryID: " + entryID +
                "\t title: " + title.getValue() +
                "\t username: " + username.getValue() +
                "\t password: " + password.getValue() +
                "\t url: " + url.getValue() +
                "\t description: " + description.getValue() +
                "\t categorieID: " + categorieID.getValue() +
                '}';
    }
}