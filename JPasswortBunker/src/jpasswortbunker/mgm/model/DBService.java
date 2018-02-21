package jpasswortbunker.mgm.model;

import javax.swing.border.TitledBorder;
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

//    public void save() {
//        pStatInsert.setInt(1, DB_ID);
//        pStatInsert.setInt(2, Entry_ID);
//        pStatInsert.setString(3, Title);
//        pStatInsert.setString(4, Username);
//        pStatInsert.setString(5, Password);
//        pStatInsert.setString(6, URL);
//        pStatInsert.setString(7, Description);
//        pStatInsert.setString(8, Categorie);
//    }

    public void close() throws SQLException {
        if (connection != null) {

            connection.close();
        }

        connection = null;
    }


    @Override
    public String
    toString() {
        return "DBService{" +
                "connection=" + connection +
                ", pStatInsert=" + pStatInsert +
                ", pStatSelect=" + pStatSelect +
                ", pStatSelectLast=" + pStatSelectLast +
                '}';
    }

    public static void main(String[] args) throws SQLException {
        DBService dbService = new DBService();

    }
}
