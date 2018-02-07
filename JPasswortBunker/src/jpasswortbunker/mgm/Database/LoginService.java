package jpasswortbunker.mgm.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginService {

    private static final String URL = "jdbc:sqlite:\\PFADDB";

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
