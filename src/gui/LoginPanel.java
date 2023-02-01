package gui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    JLabel lblTitle = new JLabel("Contact App");
    JLabel lblUsername = new JLabel("Username");
    JLabel lblPassword = new JLabel("Password");
    JTextField txtUsername = new JTextField("");
    JTextField txtPassword = new JTextField("");
    JButton btnLogin = new JButton("Login");
    JButton btnExit = new JButton("Exit");
    JLabel lblError = new JLabel("");
    JLabel lblRegister = new JLabel("<html><u>Register</u></html>");

    public LoginPanel() {
        setLayout(null);

        lblTitle.setFont(new Font("Verdana",Font.BOLD, 20));

        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0,10,360,30);
        add(lblTitle);

        lblUsername.setBounds(50,70,100,30);
        add(lblUsername);

        lblPassword.setBounds(50,110,100,30);
        add(lblPassword);

        txtUsername.setBounds(150,70,150,30);
        add(txtUsername);

        txtPassword.setBounds(150,110,150,30);
        add(txtPassword);

        btnLogin.setBounds(50,170,100,30);
        add(btnLogin);


    }
}
