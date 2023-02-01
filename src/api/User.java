package api;

public class User {

    private final int id;
    private final String Username;
    private final String Password;
    private final String Firstname;
    private final String Lastname;
    private String CPassword;


    public User(int id, String Username, String Password, String Firstname, String Lastname) {
        this.id = id;
        this.Username = Username;
        this.Password = Password;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
    }

    public int getID() {
        return this.id;
    }
    public String getUsername() {
        return this.Username;
    }
    public String getFirstname() {
        return Firstname;
    }
    public String getLastname() {
        return Lastname;
    }
    public String setCPassword(String CPassword) {
        this.CPassword = CPassword;
        return CPassword;
    }
    public String getPassword() {
        return CPassword;
    }
    public String getCPassword() {
        return CPassword;
    }
}