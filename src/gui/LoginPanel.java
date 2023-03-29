package gui;

import api.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;

public class LoginPanel extends JPanel {
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icon.png")));

    JLabel lblTitle = new JLabel("Contact App");
    JLabel lblUsername = new JLabel("Username");
    JLabel lblPassword = new JLabel("Password");
    JTextField txtUsername = new JTextField("");
    JPasswordField txtPassword = new JPasswordField("");
    JButton btnLogin = new JButton("Login");
    JButton btnExit = new JButton("Exit");
    JLabel lblError = new JLabel("");
    JLabel lblRegister = new JLabel("<html><u>Create an account</u></html>");

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


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblError.setText("");
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                if(Users.loginUser(username, password)) {
                    //successful login

                    try {
                        new ContactsFrame();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    JComponent comp = (JComponent) e.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                }
                else {
                    lblError.setText("Your details are not valid");
                }
            }
        });

        btnExit.setBounds(200, 170,100,30);
        add(btnExit);

        lblError.setBounds(0,200,360,30);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setForeground(Color.red);
        add(lblError);

        lblRegister.setBounds(0,220,360,30);
        lblRegister.setForeground(Color.BLUE);
        lblRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblRegister);
        lblRegister.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                RegisterPanel.showRegisterForm();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void showLoginForm() {
        LoginPanel lgp = new LoginPanel();
        JFrame jf = new JFrame();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(SearchPanel.class.getClassLoader().getResource("icon.png")));

        jf.setTitle("Contact App");
        jf.add(lgp);
        jf.setSize(new Dimension(360, 300));
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setIconImage(icon.getImage());
        jf.setIconImage(icon.getImage());
    }
}