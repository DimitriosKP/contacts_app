package api;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class Contacts {
    private static List<Contact> _contacts;
    static String DELIMITER = ";";
    private Date birthday;


    public static void load() {
        try {
            File projectDir = new File(System.getProperty("user.dir"));


            BufferedReader br = Files.newBufferedReader(Paths.get(projectDir + "/src/data/contacts.txt"));

            _contacts = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("#")) continue;

                String[] tokens = line.split(DELIMITER);

                Contact c = new Contact(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8], tokens[9], tokens[10]);

                _contacts.add(c);
            }
            br.close();
        } catch (Exception e) {
            _contacts = null;
            showMessageDialog(null, "Error due to contacts loading", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Finds the next free number to use as a contact ID. Each contact has a unique ID
     *
     * @return a new ID contact
     */
    public static int getNextContactId() {
        if (_contacts == null) load();

        List<Integer> ids = new ArrayList<>();

        for (Contact c : _contacts) {
            ids.add(c.getID());
        }

        return Collections.max(ids) + 1;
    }

    public void Birthday(int day, int month, int year) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.birthday = formatter.parse(day + "/" + month + "/" + year);
    }

    /**
     * Add a contact to system
     *
     * @param contact the object of new contact
     */
    public static boolean addContact(Contact contact) {
        if (_contacts == null) load();
        _contacts.add(contact);

        return store();
    }


    /**
     * Deletes a contact from the system
     *
     * @param contact_id the contacts ID
     * @return True if contact deleted
     */
    public static boolean deleteContact(int contact_id){
        if (_contacts == null)
            load();
        List<Contact> tmp = new ArrayList<>();

        for(Contact c: _contacts){
            if (c.getID() != contact_id){
                tmp.add(c);
            }
        }

        _contacts = tmp;

        store();

        return getContact(contact_id) == null;
    }


    /**
     * Saves the new contacts to a TXT file.
     *
     * @return True if saved
     */
    public static boolean store() {
        if (_contacts.isEmpty()) return true;

        try {
            File projectDir = new File(System.getProperty("user.dir"));
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(projectDir + "/src/data/contacts.txt"));

            writer.write("#ID;Firstname;Lastname;Day;Month;Year;Phone;Email;Address;City;Postcode");
            writer.newLine();

            for (Contact c : _contacts) {
                String line = "";
                line += c.getID() + DELIMITER +
                        c.getFirstname() + DELIMITER +
                        c.getLastname() + DELIMITER +
                        c.getDay() + DELIMITER +
                        c.getMonth() + DELIMITER +
                        c.getYear() + DELIMITER +
                        c.getPhone() + DELIMITER +
                        c.getEmail() + DELIMITER +
                        c.getAddress() + DELIMITER +
                        c.getCity() + DELIMITER +
                        c.getPostcode() + DELIMITER;

                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * It returns an object of type Contact based on the ID we have given. Returns null if not present.
     *
     * @param ID the contact ID
     * @return An object of type Contact or null
     */
    public static Contact getContact(int ID) {
        if (_contacts == null) load();

        for (Contact c : _contacts) {
            if (c.getID() == ID) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns a list of Contact objects with all registered contacts
     * @return List of objects of type Contact
     */
    public static List<Contact> getContacts() {
        if (_contacts == null) load();
        return _contacts;
    }
}