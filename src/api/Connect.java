package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private final String url;
    public Connect() throws ClassNotFoundException, SQLException {
        // Load the database driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the database
        url = "jdbc:mysql://localhost/contacts";

    }
    public String getURL(){
        return url;
    }
}
