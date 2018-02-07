package jpasswortbunker.mgm.entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class EntryDB {

    private ObservableList<Entry> entryList;

    public EntryDB() {
        this.entryList = FXCollections.observableArrayList();
    }

    public void addEntry(Entry entry) {
        entryList.addAll(entry);
    }

    public void addEntry(String title, String username, String password, String url,
                         String description, int categorieID) {
        entryList.add(new Entry(title, username, password, url, description, categorieID));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntryDB entryDB = (EntryDB) o;
        return Objects.equals(entryList, entryDB.entryList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(entryList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("All Entrys:");

        for (Entry entry:entryList) {
            sb.append(entry.toString());
        }
        return sb.toString();
    }
}
