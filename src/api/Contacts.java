package api;

import javax.swing.*;
import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class Contacts {
    private static List<Contact> _contacts;
    private static List<User> _users;

    /**
     * Saves the new contacts to database.
     *
     * @return True if saved
     */
    public static boolean store(Contact newContact) throws ClassNotFoundException, SQLException {
        Connect connection = new Connect();

        String query = "INSERT INTO contact_table (owner_id, firstname, lastname, day, month, year, phone, email, address, city, postcode ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        try (Connection conn = DriverManager.getConnection(connection.getURL(), "root", "password");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, Users.LoggedUser.getID());
            pstmt.setString(2, newContact.getFirstname());
            pstmt.setString(3, newContact.getLastname());
            pstmt.setInt(4, newContact.getDay());
            pstmt.setInt(5, newContact.getMonth());
            pstmt.setInt(6, newContact.getYear());
            pstmt.setString(7, newContact.getPhone());
            pstmt.setString(8, newContact.getEmail());
            pstmt.setString(9, newContact.getAddress());
            pstmt.setString(10, newContact.getCity());
            pstmt.setString(11, newContact.getPostcode());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // handle exception
            return false;
        }
        return true;
    }

    public static void load(){
        try {
            _contacts = new LinkedList<>();
            Connect connection = new Connect();
            Connection conn = DriverManager.getConnection(connection.getURL(), "root", "password");

            // Create a statement object
            Statement stmt = conn.createStatement();

            // Execute the SQL query and get the result set
            ResultSet rs = stmt.executeQuery("SELECT c.id, c.owner_id, u.id, u.username, c.firstname, c.lastname, c.day, c.month, c.year, c.phone, c.email, c.address, c.city, c.postcode " +
                    "FROM contact_table c " +
                    "JOIN users u ON c.owner_id = u.id");

            // Loop through the result set and create the Contact List _contacts
            while (rs.next()) {
                int owner_id = rs.getInt("owner_id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String day = rs.getString("day");
                String month = rs.getString("month");
                String year = rs.getString("year");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String city = rs.getString("city");
                String postcode = rs.getString("postcode");

                Contact c = new Contact(owner_id, firstname, lastname, day, month, year, phone, email, address, city, postcode);
                _contacts.add(c);
            }
            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            _contacts = null;
            showMessageDialog(null, "Error due to contacts loading", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean update(Contact newContact) throws ClassNotFoundException, SQLException {
        Connect connection = new Connect();

        String query = "UPDATE contact_table SET firstname = ?, lastname = ?, day = ?, month = ?, year = ?, phone = ?, email = ?, address = ?, city = ?, postcode = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(connection.getURL(), "root", "password");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newContact.getFirstname());
            pstmt.setString(2, newContact.getLastname());
            pstmt.setInt(3, newContact.getDay());
            pstmt.setInt(4, newContact.getMonth());
            pstmt.setInt(5, newContact.getYear());
            pstmt.setString(6, newContact.getPhone());
            pstmt.setString(7, newContact.getEmail());
            pstmt.setString(8, newContact.getAddress());
            pstmt.setString(9, newContact.getCity());
            pstmt.setString(10, newContact.getPostcode());
            pstmt.setInt(11, getContactsID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
                // handle exception
                return false;
        }
        return true;
    }

    /**
     * Delete the contact from the system
     */
    //I must fix the delete Method, because when I delete a contact, does not disappear from _contact_frame.
    public static boolean deleteContact(int id) throws SQLException, ClassNotFoundException {
        Connect connection = new Connect();
        Connection conn = DriverManager.getConnection(connection.getURL(), "root", "password");

        // Create a statement object
        Statement stmt = conn.createStatement();

        // Execute the SQL query to delete the contact with the given ID
        String query = "DELETE FROM contact_table WHERE id = " + id;
        int numRowsAffected = stmt.executeUpdate(query);
        // Check if delete was successful
        return numRowsAffected == 1;
    }

    public static int getContactsID() throws ClassNotFoundException, SQLException {
        Connect connection = new Connect();

        String query = "SELECT id FROM contact_table";

        try (Connection conn = DriverManager.getConnection(connection.getURL(), "root", "password");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getInt("id");
            // Close the result set
            rs.close();
        } catch (SQLException e) {
            // handle exception
            return 0;
        }
        return 1;
    }

    /**
     * Add a contact to system
     *
     * @param contact the object of new contact
     */
    public static boolean addContact(Contact contact) {
        if (_contacts == null) load();
        _contacts.add(contact);
        return true;
    }

    /**
     * Check if contact has birthday
     *
     * @param _day
     * @param _month
     * @return true if is contacts birthday
     */
    public static boolean checkBirthday(int _day, int _month){
        Calendar calendar = Calendar.getInstance(); // create a calendar instance
        int day = calendar.get(Calendar.DAY_OF_MONTH); // get the day of the month
        int month = calendar.get(Calendar.MONTH) + 1; // get the month
        return (day == _day && month == _month);
    }
    /**
     * Returns a list of Contact objects with all registered contacts
     * @return List of objects of type Contact
     */
    public static List<Contact> getContacts() throws SQLException, ClassNotFoundException {
        if (_contacts == null) load();
        return _contacts;
    }
}