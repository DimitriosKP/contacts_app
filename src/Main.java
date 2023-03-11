import api.Users;
import api.Contacts;
import gui.LoginPanel;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Users.load();
        Contacts.load();

        LoginPanel.showLoginForm();
    }
}