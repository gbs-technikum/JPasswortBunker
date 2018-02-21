package jpasswortbunker.mgm.model;

public class ModelMain {

    private EntryList entryList;
    private String MasterPassword;



    public ModelMain() {
        this.entryList = new EntryList();
    }

    public void addEntryToList(Entry entry) {
        entryList.addEntry(entry);
    }


}
