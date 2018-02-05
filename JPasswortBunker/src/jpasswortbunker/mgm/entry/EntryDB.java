package jpasswortbunker.mgm.entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntryDB {

    private ObservableList<Entry> entryList;

    public EntryDB(ObservableList<Entry> entrys) {
        this.entryList = FXCollections.observableArrayList();
    }

    public void addEntry(Entry entry) {
        entryList.addAll(entry);
    }


}
