import api.Users;
import api.Contacts;
import gui.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Users.load();
        Contacts.load();
        LoginPanel.showLoginForm();
    }
}