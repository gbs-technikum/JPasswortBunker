package jpasswortbunker.mgm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntryList {

    private List<Entry> entryObjectList;


    public EntryList() {
        this.entryObjectList = new ArrayList<>();
    }

    public List<Entry> getEntryObjectList() {
        return entryObjectList;
    }

    public Entry getEntry(UUID entryID) {
        Iterator<Entry> iterator = entryObjectList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getEntryID() == entryID) ;
            return iterator.next();
        }
        return null;
    }

    public void setEntryObjectList(ArrayList<Entry> arrayList) {
        this.entryObjectList = arrayList;
    }


    public void addEntry(Entry entry) {
        entryObjectList.add(entry);
    }

    //Parameter Entry
    public Entry removeEntry(Entry entry) {
        entryObjectList.remove(entry);
        return entry;
    }

    //Parameter entryID
    public Entry removeEntry(UUID entryID) {
        Iterator<Entry> iterator = entryObjectList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getEntryID() == entryID) ;
            Entry entry = iterator.next();
            entryObjectList.remove(iterator.next());
            return entry;
        }
        return null;
    }

    //Parameter entryTitle
    public Entry removeEntry(String entryTitle) {
        Iterator<Entry> iterator = entryObjectList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getTitle().equals(entryTitle)) ;
            Entry entry = iterator.next();
            entryObjectList.remove(iterator.next());
            return entry;
        }
        return null;
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("dbID \t" + "entryID \t"
                + "Title\t" + "Username\t" + "Password\t" + "URL\t" + "Description\t" + "categoryID\t" + "Timestamp\n");
        for (Entry entry : entryObjectList) {
            stringBuilder.append(entry.toString());
        }
        return stringBuilder.toString();
    }
}