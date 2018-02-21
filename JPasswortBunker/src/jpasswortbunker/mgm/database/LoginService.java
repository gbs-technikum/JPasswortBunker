package jpasswortbunker.mgm.database;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

public class LoginService {

    private static final String URL = "jdbc:sqlite:\\";

    private Connection connection;

    public LoginService() throws SQLException {
        this.connection = DriverManager.getConnection(URL);

    }


    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


}

//Alle Lesen
//Alle löschen
//einzelen Löschen
//einzeln eintragen
//einzelne ändern
