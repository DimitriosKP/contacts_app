package api;

public class Connect {
    private final String url;
    public Connect() throws ClassNotFoundException {
        // Load the database driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Connect to the database
        url = "jdbc:mysql://localhost/contacts";
    }
    public String getURL(){
        return url;
    }
}