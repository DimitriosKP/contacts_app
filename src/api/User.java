package api;

public class User {

    private final int id;
    private String Username;
    private String Password;
    private String Firstname;
    private String Lastname;

    /**
     *  The User's Constructor
     *
     * @param id
     * @param Username
     * @param Password
     * @param Firstname
     * @param Lastname
     */
    public User(int id, String Username, String Password, String Firstname, String Lastname) {
        this.id = id;
        this.Username = Username;
        this.Password = Password;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
    }

    /**
     * Sets the user's firstname
     *
     * @param firstname The user's first name
     */
    public void setFirstname(String firstname){ Firstname = firstname.trim(); }

    /**
     * Sets the user's firstname
     *
     * @param lastname The user's last name
     */
    public void setLastname(String lastname){ Lastname = lastname.trim(); }

    /**
     * Sets the user's password
     *
     * @param password The user's password
     */
    public void setPassword(String password){ Password = password.trim(); }

    /**
     * Returns the user's id
     *
     * @return the user's id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns the user's username
     *
     * @return the user's username
     */
    public String getUsername() {
        return this.Username;
    }

    /**
     * Returns the user's firstname
     *
     * @return the user's firstname
     */
    public String getFirstname() {
        return Firstname;
    }

    /**
     * Returns the user's lastname
     *
     * @return the user's lastname
     */
    public String getLastname() {
        return Lastname;
    }

    /**
     * Returns the user's password
     *
     * @return the user's password
     */
    public String getPassword() {
        return Password;
    }
}