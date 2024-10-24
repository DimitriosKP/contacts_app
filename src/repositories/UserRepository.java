package repositories;

public class UserRepository {

    String query = "";

    public UserRepository() {

    }

    public String getInsertQuery() {
      return query = "INSERT INTO users (id, username, password, firstname, lastname) VALUES (?, ?, ?, ?, ?)";
    }

    public String getLoadQuery() {
        return query = "SELECT id, username, password, firstname, lastname FROM users";
    }

    public String getNextId() {
        return query = "SELECT id FROM users";
    }
}
