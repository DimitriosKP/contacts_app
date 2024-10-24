package api;

public class User {

    private final int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    /**
     *  The User's Constructor
     *
     * @param id
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     */
    public User(int id, String username, String password, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Sets the user's firstname
     *
     * @param firstname The user's first name
     */
    public void setFirstname(String firstname){ firstName = firstname.trim(); }

    /**
     * Sets the user's firstname
     *
     * @param lastname The user's last name
     */
    public void setLastname(String lastname){ lastName = lastname.trim(); }

    /**
     * Sets the user's password
     *
     * @param password The user's password
     */
    public void setPassword(String password){ password = password.trim(); }

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
        return this.username;
    }

    /**
     * Returns the user's firstname
     *
     * @return the user's firstname
     */
    public String getFirstname() {
        return firstName;
    }

    /**
     * Returns the user's lastname
     *
     * @return the user's lastname
     */
    public String getLastname() {
        return lastName;
    }

    /**
     * Returns the user's password
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }
}