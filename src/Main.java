import api.Users;
import gui.LoginPanel;
public class Main {
    public static void main(String[] args) {
        Users.load();

        LoginPanel.showLoginForm();
    }
}