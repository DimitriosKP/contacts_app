package api;

import repositories.ContactRepository;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Contacts {
    private static List<Contact> contacts;
    private static List<User> users;
    private static ContactRepository contactRepository = new ContactRepository();

    /**
     * Saves the new contacts to database.
     *
     * @return True if saved
     */
    public static boolean store(Contact newContact) throws ClassNotFoundException {
        Connect connection = new Connect();

        try (Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());
             PreparedStatement pstmt = conn.prepareStatement(contactRepository.getInsertQuery())) {
            pstmt.setInt(1, Contacts.getNextContactsId());
            pstmt.setInt(2, Users.LoggedUser.getID());
            pstmt.setString(3, newContact.getFirstname());
            pstmt.setString(4, newContact.getLastname());
            pstmt.setInt(5, newContact.getDay());
            pstmt.setInt(6, newContact.getMonth());
            pstmt.setInt(7, newContact.getYear());
            pstmt.setString(8, newContact.getPhone());
            pstmt.setString(9, newContact.getEmail());
            pstmt.setString(10, newContact.getAddress());
            pstmt.setString(11, newContact.getCity());
            pstmt.setString(12, newContact.getPostcode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // handle exception
            return false;
        }
        return true;
    }

    /**
     * Load the contacts from database.
     *
     * @return the List with contacts
     */
    public static List<Contact> load() {
        try {
            contacts = new LinkedList<>();
            Connect connection = new Connect();
            Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());

            // Create a statement object
            Statement stmt = conn.createStatement();

            // Execute the SQL query and get the result set
            ResultSet rs = stmt.executeQuery(contactRepository.getLoadQuery());

            // Loop through the result set and create the Contact List _contacts
            while (rs.next()) {
                int id = rs.getInt("id");
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

                Contact c = new Contact(id, owner_id, firstname, lastname, day, month, year, phone, email, address, city, postcode);
                contacts.add(c);
            }
            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            contacts = null;
            showMessageDialog(null, "Error due to contacts loading", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return contacts;
    }

    /**
     * Update the contact to database.
     *
     * @return True if the contact has updated
     */
    public static boolean update(Contact newContact) throws ClassNotFoundException {
        Connect connection = new Connect();

        try (Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());
             PreparedStatement pstmt = conn.prepareStatement(contactRepository.getUpdateQuery())) {
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
            pstmt.setInt(11, getContactID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            // handle exception
            return false;
        }
        return true;
    }

    /**
     * Delete contact from database.
     *
     * @return True if the contact has deleted
     */
    //Delete the contact from the system
    public static boolean deleteContact(int id) throws SQLException, ClassNotFoundException {
        Connect connection = new Connect();
        Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());

        // Create a statement object
        Statement stmt = conn.createStatement();

        // Execute the SQL query to delete the contact with the given ID
        int numRowsAffected = stmt.executeUpdate(contactRepository.getDeleteQuery() + id);
        // Check if delete was successful
        return numRowsAffected == 1;  //true
    }

    /**
     * Founds Founds the next id number for the contact
     *
     * @return The next id number for the contact
     */
    public static int getNextContactsId() throws SQLException, ClassNotFoundException {
        Connect connection = new Connect();
        Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());
        List<Integer> ids = new ArrayList<>();

        // Create a statement object
        Statement stmt = conn.createStatement();

        //Select all the id numbers from contacts table
        ResultSet rs = stmt.executeQuery(contactRepository.getNextId());

        while (rs.next()) {
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
     * Returns the contact's id
     *
     * @return The contact's id number
     */
    public static int getContactID() throws ClassNotFoundException, SQLException {
        Connect connection = new Connect();

        try (Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());
             PreparedStatement pstmt = conn.prepareStatement(contactRepository.getCntctId())) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getInt("id");
            // Close the result set
            rs.close();
        } catch (SQLException e) {
            // handle exception
            return 0;
        }
        return Contacts.getContactID();
    }

    /**
     * Add a contact to system
     *
     * @param contact the object of new contact
     */
    public static boolean addContact(Contact contact) {
        if (contacts == null) load();
        contacts.add(contact);
        return true;
    }

    /**
     * Check if contact has birthday
     *
     * @param _day
     * @param _month
     * @return true if is contacts birthday
     */
    public static boolean checkBirthday(int _day, int _month) {
        Calendar calendar = Calendar.getInstance(); // create a calendar instance
        int day = calendar.get(Calendar.DAY_OF_MONTH); // get the day of the month
        int month = calendar.get(Calendar.MONTH) + 1; // get the month
        return (day == _day && month == _month);
    }

    /**
     * Returns a list of Contact objects with all registered contacts
     *
     * @return List of objects of type Contact
     */
    public static List<Contact> getContacts() throws SQLException, ClassNotFoundException {
        if (contacts == null) load();
        return contacts;
    }
}