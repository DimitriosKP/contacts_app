import api.Users;
import api.Contacts;
import gui.LoginPanel;

public class Main {
    public static void main(String[] args) {
        Users.load();
        Contacts.load();
        LoginPanel.showLoginForm();
    }
}