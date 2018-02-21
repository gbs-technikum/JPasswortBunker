package jpasswortbunker.mgm.entry;

public class Entry2 {

    private String name;
    private String nachname;
    private int alter;

    public Entry2() {

    }

    public Entry2(String name, String nachname, int alter) {
        this.name = name;
        this.nachname = nachname;
        this.alter = alter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }
}
