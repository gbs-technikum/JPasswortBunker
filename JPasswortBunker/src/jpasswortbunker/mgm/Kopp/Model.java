package jpasswortbunker.mgm.Kopp;


import java.util.ArrayList;
import java.util.Iterator;

public final class Model {

    private String text;
    private jpasswortbunker.mgm.Kopp.Presenter presenter;
    private ArrayList<jpasswortbunker.mgm.Kopp.Entry> entryList;
    private String masterpassword = "dddde";


    public Model(jpasswortbunker.mgm.Kopp.Presenter presenter) {
        this.presenter = presenter;
        this.entryList = new ArrayList<>();
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


//    public void updateGui(){
//        presenter.updateGui();
//    }

    //Einfache Überprüfung ob Masterpasswort gesetzt ist, muss noch umgebaut werden
    public boolean checkSetMasterpassword() {
        return masterpassword != null;
    }

    //Überprüft ob MasterPassword mit eingegebenen übereinstimmt
    public boolean checkMasterpasswortEquals(String password) {
        return masterpassword.equals(password);
    }

    //überprüft ob die zwei Eingegebenen Passwörter übereinstimmen
    //Verwendung für Masterpasswort und Eintrag
    private boolean equalsPassword(String password1, String password2) {
        return password1.equals(password2);
    }

    //Masterpasswort setzten
    public boolean setMasterpassword(String password1, String password2) {
        if (equalsPassword(password1, password2)) {
            //Eintrag in die Datenbank schreiben
            masterpassword = password1;
            System.out.println("MasterPasswort wurde gesetzt");
            return true;
        }
        System.out.println("MasterPassword konnte nicht gesetzt werden -> Eingaben nicht gleich");
        return false;
    }

    //Gibt komplette Liste zurück
    public ArrayList<jpasswortbunker.mgm.Kopp.Entry> getEntryList() {
        return entryList;
    }

    //Gibt Eintrag anhand AarrayIndex zurück
    public void getEntryByArrayIndex(int index) {
        entryList.get(0);
    }

    //Gibt Eintrag anhand der EntryID zurück
    public jpasswortbunker.mgm.Kopp.Entry getEntryByEntryID(int id) {
        Iterator<jpasswortbunker.mgm.Kopp.Entry> entryListIterator= entryList.iterator();
        while (entryListIterator.hasNext()) {
            if (entryListIterator.next().getEntryID() == id) {
                return entryListIterator.next();
            }
        }
        return null;
    }

    //Gibt Eintrag anhand von Titel zurück
    public jpasswortbunker.mgm.Kopp.Entry getEntry(String title) {
        Iterator<jpasswortbunker.mgm.Kopp.Entry> entryListIterator= entryList.iterator();
        while (entryListIterator.hasNext()) {
            if (entryListIterator.next().getTitle().equals(title)) {
                return entryListIterator.next();
            }
        }
        return null;
    }

    //Eintrag zur Liste hinzufügen
    public void addEntry(jpasswortbunker.mgm.Kopp.Entry entry) {
        entryList.add(entry);
    }

    public void addEntry(String title, String username, String password, String repeatPassword, String url, String description, int categorieID) {
        entryList.add(new jpasswortbunker.mgm.Kopp.Entry(title, username, password, url, description, categorieID));
    }

    //Testeinträge
    public void testEintraege() {
        entryList.add(new jpasswortbunker.mgm.Kopp.Entry("Netflix", "meinName", "dasPasswort", "www.netflix.com", "kaka", 1));
        entryList.add(new jpasswortbunker.mgm.Kopp.Entry("maxdome", "meinName", "dasPasswort", "www.netflix.com", "kaka", 1));
        entryList.add(new jpasswortbunker.mgm.Kopp.Entry("Youtube", "meinName", "dasPasswort", "www.netflix.com", "kaka", 1));
        entryList.add(new jpasswortbunker.mgm.Kopp.Entry("Facebook", "meinName", "dasPasswort", "www.netflix.com", "kaka", 3));
        entryList.add(new jpasswortbunker.mgm.Kopp.Entry("Bitcoin", "meinName", "dasPasswort", "www.netflix.com", "kaka", 2));
    }

    public void test() {
        System.out.println("testMethode Model");
    }

}
