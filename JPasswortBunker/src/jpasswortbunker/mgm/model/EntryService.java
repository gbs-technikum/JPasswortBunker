package jpasswortbunker.mgm.model;

public class EntryService {

    public Entry newEntry(String title, String description, String url, String username, String password, int dbID, int entryID, int categoryID) {
        Entry entry = new Entry(title, description, url, username, password, dbID, entryID, categoryID);
        return entry;
    }




//    public Entry updateEntry(Entry entry) {
//        entry.updateEntry();
//    }




}
