package jpasswortbunker.mgm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntryList {

    private List<Entry> entryList;


    public EntryList() {
        this.entryList = new ArrayList<>();
    }

    public List<Entry> getEntryList() {
        return entryList;
    }

    public Entry getEntry(UUID entryID) {
        Iterator<Entry> iterator = entryList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getEntryID() == entryID) ;
            return iterator.next();
        }
        return null;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }


    public void addEntry(Entry entry) {
        entryList.add(entry);
    }

    //Parameter Entry
    public Entry removeEntry(Entry entry) {
        entryList.remove(entry);
        return  entry;
    }

    //Parameter entryID
    public Entry removeEntry(UUID entryID) {
        Iterator<Entry> iterator = entryList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getEntryID() == entryID) ;
            Entry entry = iterator.next();
            entryList.remove(iterator.next());
            return entry;
        }
        return null;
    }

    //Parameter entryTitle
    public Entry removeEntry(String entryTitle) {
        Iterator<Entry> iterator = entryList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getTitle().equals(entryTitle)) ;
            Entry entry = iterator.next();
            entryList.remove(iterator.next());
            return entry;
        }
        return null;
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("dbID \t" + "entryID \t"
                + "Title\t" + "Username\t" + "Password\t" + "URL\t" + "Description\t" + "categoryID\t" + "Timestamp\n");
        for (Entry entry : entryList) {
            stringBuilder.append(entry.toString());
        }
        return stringBuilder.toString();
    }
}