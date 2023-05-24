package api;

public class User {

    private final int id;
    private String Username;
    private String Password;
    private String Firstname;
    private String Lastname;


    public User(int id, String Username, String Password, String Firstname, String Lastname) {
        this.id = id;
        this.Username = Username;
        this.Password = Password;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
    }
    public void setFirstname(String firstname){ Firstname = firstname.trim(); }
    public void setLastname(String lastname){ Lastname = lastname.trim(); }
    public void setPassword(String password){ Password = password.trim(); }
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