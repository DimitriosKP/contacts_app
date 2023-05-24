package api;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Users {
    private static List<User> _users;
    public static User LoggedUser = null;

    /**
     * Saves the new contacts to database.
     *
     * @return True if saved
     */
    public static boolean store() throws ClassNotFoundException {
        Connect connection = new Connect();

        if (_users.isEmpty()) return true;
        for (User u : _users) {
            String query = "INSERT INTO users (id, username, password, firstname, lastname) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, u.getID());
                pstmt.setString(2, u.getUsername());
                pstmt.setString(3, u.getPassword());
                pstmt.setString(4, u.getFirstname());
                pstmt.setString(5, u.getLastname());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                // handle exception
            }
        }
        return true;
    }

        /**
         * Load the new contacts from database.
         *
         * @return True if saved
         */
    public static void load() {
        try {
            _users = new LinkedList<>();
            Connect connection = new Connect();
            Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());

            // Create a statement object
            Statement stmt = conn.createStatement();

            // Execute the SQL query and get the result set
            ResultSet rs = stmt.executeQuery("SELECT id, username, password, firstname, lastname FROM users");

            // Loop through the result set and create the Contact List _contacts
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                User u = new User(id, username, password, firstname, lastname);
                _users.add(u);
            }
            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            _users = null;
            showMessageDialog(null, "Error due to users loading", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Check if the username exists in database.
     *
     * @param username
     * @return true if exists.
     */
    public static boolean usernameExists(String username) {
        for (User u : _users) {
            if (u.getUsername().equals(username.trim())) {
                return true;
            }
        }
        return false;
    }

    public static int getNextUserId() throws SQLException, ClassNotFoundException {
        Connect connection = new Connect();
        Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());
        List<Integer> ids = new ArrayList<>();

        // Create a statement object
        Statement stmt = conn.createStatement();

        //Select all the id numbers from users table
        String query = "SELECT id FROM users";
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()){
            ids.add(rs.getInt("id"));
        }

        // Close the result set, statement, and connection
        rs.close();
        stmt.close();
        conn.close();
        if (ids.isEmpty()) {
            return 1;
        } else {
            return Collections.max(ids) + 1;
        }
    }

    /**
     * Check if the password is the same with the cpassword.
     *
     * @return True if saved
     */
    public static boolean checkPassword(String password, String cpassword) {
        return password.equals(cpassword);
    }

    /**
     * Check users username and password.
     *
     * @param username
     * @param password
     *
     * @return True if the parameters are in the database. True if the parameters are in the database. So the user is logged in.
     */
    public static boolean loginUser(String username, String password) {
        LoggedUser = null;

        if (_users == null) Users.load();
        for(User u : _users) {
            if (u.getUsername().equals(username.trim())){
                if (u.getPassword().equals(password.trim())){
                    LoggedUser = u;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Create a new User object.
     *
     * @param username
     * @param password
     * @param firstname
     * @param lastname
     *
     * Checks if the username is already exist by using the function usernameExists.
     *
     * @return the store of the new user
     */
    public static boolean registerUser(String username, String password, String firstname, String lastname) throws Exception {
        if (_users == null) Users.load();

        if (usernameExists(username)) {
            throw new Exception("This username is already exists");
        }
        User user = new User(getNextUserId(), username, password, firstname, lastname);
        _users.add(user);

        return Users.store();
    }

    /**
     * Checks the strength of the password.
     *
     * @param pass
     *
     * @return 0 if the field is blank.
     * @return 1 if the password contains lowercase-uppercase letters, numbers, symbols.
     * @return 2 if the password contains lowercase-uppercase letters, symbols.
     * @return 3 if the password contains lowercase-uppercase letters, numbers.
     * @return 4 if the password contains, symbols.
     */
    public static int checkStrongPassword(JTextField pass) {
        String password = pass.getText();
        // Check for at least one uppercase letter
        if (password.isBlank())
            return 0;
        // Check for at least one uppercase letter
        if (password.matches(".*[A-Z].*") && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*") && password.matches(".*\\d.*"))
            return 1;
        // Check for at least one special character
        if (password.matches(".*[A-Z].*") && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))
            return 2;
        // Check for at least one number
        if (password.matches(".*[A-Z].*") && password.matches(".*\\d.*"))
            return 3;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*") && password.matches(".*\\d.*"))
            return 4;
        // Password meets all requirements
        return 5;
    }
}