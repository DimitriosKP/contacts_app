package repositories;

public class ContactRepository {

    String query = "";

    public ContactRepository() {

    }

    public String getInsertQuery() {
        return query = "INSERT INTO contact_table (id, owner_id, firstname, lastname, day, month, year, phone, email, address, city, postcode ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    }

    public String getLoadQuery() {
        return query = "SELECT c.id, c.owner_id, u.id, u.username, c.firstname, c.lastname, c.day, c.month, c.year, c.phone, c.email, c.address, c.city, c.postcode " +
                "FROM contact_table c " +
                "JOIN users u ON c.owner_id = u.id";
    }

    public String getDeleteQuery() {
        return query = "DELETE FROM contact_table WHERE id = ";
    }

    public String getUpdateQuery() {
        return query = "UPDATE contact_table SET firstname = ?, lastname = ?, day = ?, month = ?, year = ?, phone = ?, email = ?, address = ?, city = ?, postcode = ? WHERE id = ?";
    }

    public String getCntctId() {
        return query = "SELECT id FROM contact_table";
    }

    public String getNextId() {
        return  query = "SELECT id FROM contact_table";
    }
}
