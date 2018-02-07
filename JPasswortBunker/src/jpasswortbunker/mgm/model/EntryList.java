package jpasswortbunker.mgm.model;

import java.util.ArrayList;
import java.util.List;

public class EntryList {

    private List<Entry> entryList;

    public EntryList() {
        this.entryList = new ArrayList<>();
    }

    public void addEntry(Entry entry){
        entryList.add(entry);
    }


//    public void newEntry(){
//        entryList.add(new Entry());
//    }


}
