package gui;

import api.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import static javax.swing.JOptionPane.showMessageDialog;



public class RegisterPanel extends JPanel {

    JLabel lblTitle = new JLabel("Create new account");
    JLabel lblFirstname = new JLabel("First name");
    JLabel lblLastname = new JLabel("Last name");
    JLabel lblUsername = new JLabel("Username");
    JLabel lblPassword = new JLabel("Password");
    JLabel lblConfPassword = new JLabel("Confirm Password");

    JTextField txtFirstname = new JTextField("");
    JTextField txtLastname = new JTextField("");
    JTextField txtUsername = new JTextField("");
    JTextField txtPassword = new JTextField("");
    JTextField txtConfPassword = new JTextField("");

    JButton btnRegister = new JButton("Register");
    JLabel lblSignIn = new JLabel("<html><u>Do you already have an account? Sign In!</u></html>");

    JLabel lblError =new JLabel("");

    static JFrame _registerFrame = null;

    public RegisterPanel() {
        setLayout(null);

        lblTitle.setFont(new Font("Verdana",Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(20,10,360,30);
        add(lblTitle);

        lblFirstname.setBounds(50,70,100,30);
        add(lblFirstname);

        txtFirstname.setBounds(200,70,150,30);
        add(txtFirstname);

        lblLastname.setBounds(50,110,100,30);
        add(lblLastname);

        txtLastname.setBounds(200,110,150,30);
        add(txtLastname);

        lblUsername.setBounds(50,150,100,30);
        add(lblUsername);

        txtUsername.setBounds(200,150,150,30);
        add(txtUsername);

        lblPassword.setBounds(50,190,100,30);
        add(lblPassword);

        txtPassword.setBounds(200,190,150,30);
        add(txtPassword);

        lblConfPassword.setBounds(50,230,120,30);
        add(lblConfPassword);

        txtConfPassword.setBounds(200,230,150,30);
        add(txtConfPassword);

        btnRegister.setBounds(150,280,100,30);
        add(btnRegister);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblError.setText("");
                String firstname = txtFirstname.getText();
                String lastname = txtLastname.getText();
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                String cpassword = txtConfPassword.getText();

                if(firstname.isBlank()){
                    lblError.setText("Please enter your first name!");
                }
                if(lastname.isBlank()){
                    lblError.setText("Please enter your last name!");
                }
                if(username.isBlank()){
                    lblError.setText("Please enter your username!");
                }
                if(password.isBlank()){
                    lblError.setText("Please enter your password!");
                }
                if(cpassword.isBlank()){
                    lblError.setText("Please confirm your password!");
                }

                if(!Users.checkPassword(password, cpassword)) {
                    JOptionPane.showMessageDialog(null, "Password does not match. Please try again.");
                    txtConfPassword.setBounds(200,230,150,30);
                    txtConfPassword.requestFocus();
                    return;
                }

                boolean user_registered = false;
                try {
                    user_registered = Users.registerUser(username, password, firstname, lastname);
                } catch(Exception ex) {
                    lblError.setText(ex.getMessage());
                    return;
                }

                if(user_registered) {
                    showMessageDialog(null, "The registration complete successfully!");
                    _registerFrame.dispose();
                    _registerFrame = null;
                }
                else {
                    lblError.setText("An error occurred while registering the user");
                }
            }
        });

        lblSignIn.setBounds(20,330,360,30);
        lblSignIn.setForeground(Color.BLUE);
        lblSignIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblSignIn);
        lblSignIn.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                _registerFrame.dispose();
                _registerFrame = null;
            }
        });

        lblError.setBounds(0,320,360,30);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setForeground(Color.red);
        add(lblError);
    }

    public static void showRegisterForm() {
        RegisterPanel panel = new RegisterPanel();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(SearchPanel.class.getClassLoader().getResource("icon.png")));

        if(_registerFrame == null) {
            _registerFrame = new JFrame();
            _registerFrame.setTitle("Register");
            _registerFrame.add(panel);
            _registerFrame.setSize(new Dimension(400, 400));
            _registerFrame.setResizable(false);
        }
        _registerFrame.setLocationRelativeTo(null);
        _registerFrame.setVisible(true);
        _registerFrame.setIconImage(icon.getImage());
        _registerFrame.setIconImage(icon.getImage());
    }
}