package jpasswortbunker.mgm.model;

import java.sql.*;

public class DBService {
    private Connection connection;
    private PreparedStatement pStatInsert, pStatSelect, pStatSelectLast;
    private static final String URL = "jdbc:sqlite:ressources/passwordbunker.db";

    public DBService() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.pStatInsert = connection.prepareStatement("Insert into Entrys(DB_ID , Entry_ID, Title, Username, Password, URL, Description, Categorie_ID ) VALUES (?,?,?,?,?,?,?,?)");
        this.pStatSelect = connection.prepareStatement("SELECT DB_ID, Entry_ID, Title, Username, Password, URL, Description, Categorie_ID");

    }

    public void save() {


    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
            connection = null;
    }


}
