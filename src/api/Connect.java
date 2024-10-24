package api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Connect {
    private final String url;

    public Connect() throws ClassNotFoundException {
        // Load the database driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the database
        url = "jdbc:mysql://localhost/contacts";
    }

    public String getURL() {
        return url;
    }

    public static String getDbUsername() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);
            return prop.getProperty("db.username");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDbPassword() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);
            return prop.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}