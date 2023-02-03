package api;


public class Contact {
    private final int _id;

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
     * @param ID user's ID
     * @param firstname user's first name
     * @param lastname user's last name
     * @param phone user's number
     * @param email user's email address
     * @param address address of the user
     * @param city user's city
     * @param postcode user's postcode
     */
    public Contact(int ID, String firstname, String lastname, String day, String month, String year, String phone, String email, String address, String city, String postcode) {
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

        _id = ID;
    }

    /**
     * Returns the user's ID
     *
     * @return The user's ID
     */
    public int getID(){
        return _id;
    }

    /**
     * Sets the user's first name
     *
     * @param firstname The new first name of user
     */
    public void setFirstname(String firstname){

        _firstname = firstname.trim();
    }

    /**
     * Sets the user's surname
     *
     * @param lastname The new surname of user
     */
    public void setLastname(String lastname){

        _lastname = lastname.trim();
    }

    public void setPhone(String phone) {
        _phone = phone;
    }

    public void setEmail(String email) {
        _email = email.trim();
    }

    public String getPhone() {
        return _phone;
    }

    public String getEmail() {
        return _email;
    }

    /**
     * returns the user's first name
     *
     * @return the first name
     */
    public String getFirstname() {
        return _firstname;
    }

    /**
     * returns the user's last name
     *
     * @return the last name
     */
    public String getLastname() {
        return _lastname;
    }


    /**
     * Sets the user's address
     *
     * @param address the new address of user
     */
    public void setAddress(String address){
        _address = address.trim();
    }

    /**
     * returns the user's address
     *
     * @return the users address
     */
    public String getAddress(){
        return _address;
    }


    /**
     * Sets the user's city
     *
     * @param city the new city of city
     */
    public void setCity(String city){
        _city = city.trim();
    }
    /**
     * returns the user's city
     *
     * @return the user's city
     */
    public String getCity(){
        return _city;
    }

    /**
     * Sets the user's postcode
     *
     * @param postcode The new postcode of the user
     */
    public void setPostcode(String postcode){

        _postcode = postcode.trim();
    }
    /**
     * Returns the user's postcode
     *
     * @return The user's postcode
     */
    public String getPostcode(){
        return _postcode;
    }
    public void setDay(int day){
        _day = day;
    }
    public void setMonth(int month){
        _month = month;
    }
    public void setYear(int year){
        _year = year;
    }
    public int getDay(){
        return _day;
    }
    public int getMonth(){
        return _month;
    }
    public int getYear(){
        return _year;
    }
    public String setBirthday(int _day, int _month, int _year) {
        _birthday = _day + "/" + _month + "/" + _year;
        return _birthday;
    }
    public String getBirthday(String _birthday) {
        return _birthday;
    }
}
