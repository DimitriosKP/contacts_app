package api;


public class Contact {
    private int _id;
    private int _ownerID = 0;
    private String _firstname = "";
    private String _lastname = "";
    private int _day;
    private int _month;
    private int _year;
    private String _phone = "";
    private String _email = "";
    private String _address= "";
    private String _city= "";
    private String _postcode= "";
    private String _birthday = "";
    /**
     * Creates a new contact
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
    public Contact(int owner_id, String firstname, String lastname, String day, String month, String year, String phone, String email, String address, String city, String postcode) {
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
    public void setId(int id){
        _id = id;
    }

    /**
     * Returns the contact's ID
     *
     * @return The contact's ID
     */
    public int getID(){
        return _id;
    }

    /**
     * Returns the contact's owners ID
     *
     * @return The contact's owners ID
     */
    public int getOwnerID(){
        return _id;
    }

    /**
     * Sets the Owner's user_id
     *
     * @param user_id The Owner's user_id
     */
    public void setOwner(int user_id){
        _ownerID = user_id;
    }
    public int getOwner(){
        return _ownerID;
    }
    public boolean isOwner(int user_id){
        return _ownerID == user_id;
    }

    /**
     * Sets the contact's firstname
     *
     * @param firstname The contact's first name
     */
    public void setFirstname(String firstname){ _firstname = firstname.trim(); }

    /**
     * Sets the contact's surname
     *
     * @param lastname The contact's last name
     */
    public void setLastname(String lastname){ _lastname = lastname.trim(); }

    /**
     * Sets the contact's phone number
     *
     * @param phone
     */
    public void setPhone(String phone) {
        _phone = phone;
    }

    /**
     * Sets the contact's email address
     *
     * @param email The contact's email address
     */
    public void setEmail(String email) {
        _email = email.trim();
    }

    /**
     * returns the contact's address
     *
     * @return the contact's address
     */
    public String getPhone() {
        return _phone;
    }

    /**
     * returns the contact's email address
     *
     * @return the contact's email address
     */
    public String getEmail() {
        return _email;
    }

    /**
     * returns the contact's first name
     *
     * @return the first name
     */
    public String getFirstname() {
        return _firstname;
    }

    /**
     * returns the contact's last name
     *
     * @return the last name
     */
    public String getLastname() {
        return _lastname;
    }
    /**
     * Sets the contact's address
     *
     * @param address the new address of user
     */
    public void setAddress(String address){
        _address = address.trim();
    }

    /**
     * returns the contact's address
     *
     * @return the users address
     */
    public String getAddress(){
        return _address;
    }


    /**
     * Sets the contact's city
     *
     * @param city the new city of city
     */
    public void setCity(String city){
        _city = city.trim();
    }

    /**
     * returns the contact's city
     *
     * @return the contact's city
     */
    public String getCity(){
        return _city;
    }

    /**
     * Sets the contact's postcode
     *
     * @param postcode The new postcode of the user
     */
    public void setPostcode(String postcode){ _postcode = postcode.trim(); }

    /**
     * Returns the contact's postcode
     *
     * @return The contact's postcode
     */
    public String getPostcode(){
        return _postcode;
    }

    /**
     * Sets the contact's day of birth
     *
     * @param day contact's day of birth
     */
    public void setDay(int day){
        _day = day;
    }

    /**
     * Sets the contact's month of birth
     *
     * @param month contact's day of birth
     */
    public void setMonth(int month){
        _month = month;
    }

    /**
     * Sets the contact's year of birth
     *
     * @param year contact's day of birth
     */
    public void setYear(int year){
        _year = year;
    }

    /**
     * Returns the contact's day of birth
     *
     * @return The contact's _day
     */
    public int getDay(){
        return _day;
    }

    /**
     * Returns the contact's month of birth
     *
     * @return The contact's month of birth
     */
    public int getMonth(){
        return _month;
    }

    /**
     * Returns the contact's year of birth
     *
     * @return The contact's year of birth
     */
    public int getYear(){
        return _year;
    }

    /**
     * Sets the contact's birthday as string
     *
     * @param _day
     * @param _month
     * @param _year
     *
     * @return The contact's birthday as string
     */
    public String setBirthday(int _day, int _month, int _year) {
        _birthday = _day + "/" + _month + "/" + _year;
        return _birthday;
    }
}
