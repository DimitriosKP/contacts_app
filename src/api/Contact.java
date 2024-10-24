package api;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Contact {
    private int id;
    private int ownerID = 0;
    private String firstname = "";
    private String lastname = "";
    private int day;
    private int month;
    private int year;
    private String phone = "";
    private String email = "";
    private String address = "";
    private String city = "";
    private String postcode = "";
    private String birthday = "";

    /**
     * Creates a new contact
     *
     * @param id        contact's id
     * @param owner_id  contact's owner ID
     * @param firstname contact's first name
     * @param lastname  contact's last name
     * @param day       contact's day of birth
     * @param month     contact's month of birth
     * @param year      contact's year of birth
     * @param phone     contact's number
     * @param email     contact's email address
     * @param address   address of the user
     * @param city      contact's city
     * @param postcode  contact's postcode
     */
    public Contact(int id, int owner_id, String firstname, String lastname, String day, String month, String year, String phone, String email, String address, String city, String postcode) {
        setId(id);
        setOwner(owner_id);
        setFirstname(firstname);
        setLastname(lastname);
        setDay(Integer.parseInt(day));
        setMonth(Integer.parseInt(month));
        setYear(Integer.parseInt(year));
        setPhone(phone);
        setEmail(email);
        setAddress(address);
        setCity(city);
        setPostcode(postcode);
    }

    /**
     * Sets the contact's id
     *
     * @param id The contact's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the Owner's user_id
     *
     * @param user_id The Owner's user_id
     */
    public void setOwner(int user_id) {
        this.ownerID = user_id;
    }

    public boolean isOwner(int user_id) {
        return this.ownerID == user_id;
    }

    /**
     * Sets the contact's firstname
     *
     * @param firstname The contact's first name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname.trim();
    }

    /**
     * Sets the contact's surname
     *
     * @param lastname The contact's last name
     */
    public void setLastname(String lastname) {
        this.lastname = lastname.trim();
    }

    /**
     * Sets the contact's phone number
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets the contact's email address
     *
     * @param email The contact's email address
     */
    public void setEmail(String email) {
        this.email = email.trim();
    }

    /**
     * Returns the contact's address
     *
     * @return the contact's address
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Returns the contact's id
     *
     * @return the contact's id
     */
    public int get_id() {
        return this.id;
    }

    /**
     * Returns the contact's owner id
     *
     * @return the contact's owner id
     */
    public int get_ownerID() {
        return this.ownerID;
    }

    /**
     * Returns the contact's email address
     *
     * @return the contact's email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Returns the contact's first name
     *
     * @return the first name
     */
    public String getFirstname() {
        return this.firstname;
    }

    /**
     * Returns the contact's last name
     *
     * @return the last name
     */
    public String getLastname() {
        return this.lastname;
    }

    /**
     * Sets the contact's address
     *
     * @param address the new address of user
     */
    public void setAddress(String address) {
        this.address = address.trim();
    }

    /**
     * Returns the contact's address
     *
     * @return the users address
     */
    public String getAddress() {
        return this.address;
    }


    /**
     * Sets the contact's city
     *
     * @param city the new city of city
     */
    public void setCity(String city) {
        this.city = city.trim();
    }

    /**
     * Returns the contact's city
     *
     * @return the contact's city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Sets the contact's postcode
     *
     * @param postcode The new postcode of the user
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode.trim();
    }

    /**
     * Returns the contact's postcode
     *
     * @return The contact's postcode
     */
    public String getPostcode() {
        return this.postcode;
    }

    /**
     * Sets the contact's day of birth
     *
     * @param day contact's day of birth
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Sets the contact's month of birth
     *
     * @param month contact's day of birth
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Sets the contact's year of birth
     *
     * @param year contact's day of birth
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the contact's day of birth
     *
     * @return The contact's _day
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Returns the contact's month of birth
     *
     * @return The contact's month of birth
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Returns the contact's year of birth
     *
     * @return The contact's year of birth
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Sets the contact's birthday as string
     *
     * @param _day
     * @param _month
     * @param _year
     * @return The contact's birthday as string
     */
    public String setBirthday(int _day, int _month, int _year) {
        this.birthday = _day + "/" + _month + "/" + _year;
        return this.birthday;
    }

    /**
     * Returns the date that the contact was saved
     *
     * @return the date that the contact was saved
     */
    public String getDateOfSave() {
        LocalDate currentDate = LocalDate.now();
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormatter.format(Date.valueOf(currentDate));
    }
}