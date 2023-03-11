package api;

public class User {

    private final int id;
    private final String Username;
    private final String Password;
    private final String Firstname;
    private final String Lastname;


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
    public String getPassword() {
        return Password;
    }
    public boolean isUser(){
        return true;
    }
}