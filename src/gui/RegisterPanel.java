package gui;

import api.Connect;
import api.Users;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
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
    JButton btnUpdate = new JButton("Update");
    JButton btnCancel = new JButton("Cancel");
    JLabel lblSignIn = new JLabel("<html><u>Do you already have an account? Sign In!</u></html>");
    JLabel lblPasMessage = new JLabel("Hello");
    JLabel lblError =new JLabel("");

    static JFrame _registerFrame = null;
    ActionListener _onChangeListener = null;

    public enum VIEW_TYPE {
        VIEW,
        NEW,
        EDIT
    }

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

        lblLastname.setBounds(50,130,100,30);
        add(lblLastname);

        txtLastname.setBounds(200,130,150,30);
        add(txtLastname);

        lblUsername.setBounds(50,190,100,30);
        add(lblUsername);

        txtUsername.setBounds(200,190,150,30);
        add(txtUsername);

        lblPassword.setBounds(50,250,100,30);
        add(lblPassword);

        txtPassword.setBounds(200,250,150,30);
        add(txtPassword);

        lblPasMessage.setBounds(200, 275 , 150, 30);
        // Create a new DocumentListener
        DocumentListener passwordListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkPassword();
            }
            public void removeUpdate(DocumentEvent e) {
                checkPassword();
            }
            public void insertUpdate(DocumentEvent e) {
                checkPassword();
            }

            public void checkPassword() {
                // Check the strength of the password
                int strength = Users.checkStrongPassword(txtPassword);
                // Set the label text based on the password strength
                switch (strength) {
                    case 0:
                        lblPasMessage.setText("");
                        break;
                    case 1:
                        lblPasMessage.setText("Strong");
                        lblPasMessage.setForeground(Color.green);
                        break;
                    case 2:
                    case 3:
                    case 4:
                        lblPasMessage.setText("Medium");
                        lblPasMessage.setForeground(Color.orange);
                        break;
                    default:
                        lblPasMessage.setText("Weak");
                        lblPasMessage.setForeground(Color.red);
                        break;
                }
                // Add the label to the frame if it hasn't already been added
                if (!Arrays.asList(getComponents()).contains(lblPasMessage)) {
                    add(lblPasMessage);
                }
            }
        };

        // Add the DocumentListener to the JTextField
        txtPassword.getDocument().addDocumentListener(passwordListener);

        // Set the initial text of the label based on the strength of the password
        switch (Users.checkStrongPassword(txtPassword)) {
            case 0:
                lblPasMessage.setText("");
                add(lblPasMessage);
                break;
            case 1:
                lblPasMessage.setText("Strong");
                add(lblPasMessage);
                break;
            case 2 ,3, 4:
                lblPasMessage.setText("Medium");
                add(lblPasMessage);
                break;
            default:
                lblPasMessage.setText("Weak");
                add(lblPasMessage);
                break;
        }
        lblPasMessage.setBounds(200, 275, 150, 30);

        // Add the DocumentListener to the JTextField
        txtPassword.getDocument().addDocumentListener(passwordListener);

        lblConfPassword.setBounds(50,310,120,30);
        add(lblConfPassword);

        txtConfPassword.setBounds(200,310,150,30);
        add(txtConfPassword);

        if (Users.LoggedUser == null) {
            btnRegister.setBounds(150,370,100,30);
            add(btnRegister);
        } else {
            lblTitle.setText("Edit Contact");

            btnUpdate.setBounds(85, 370, 100, 30);
            add(btnUpdate);

            btnCancel.setBounds(200, 370, 100, 30);
            add(btnCancel);

            fillValues(Users.LoggedUser != null);

            btnUpdate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        update();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _registerFrame.dispose();
                }
            });
        }
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblError.setText("");
                String firstname = txtFirstname.getText();
                String lastname = txtLastname.getText();
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                String cpassword = txtConfPassword.getText();

                if (firstname.isBlank()) {
                    lblError.setText("Please enter your first name!");
                    return;
                }
                if (lastname.isBlank()) {
                    lblError.setText("Please enter your last name!");
                    return;
                }
                if (username.isBlank()) {
                    lblError.setText("Please enter your username!");
                    return;
                }
                if (password.isBlank()) {
                    lblError.setText("Please enter your password!");
                    return;
                }
                if (cpassword.isBlank()) {
                    lblError.setText("Please confirm your password!");
                    return;
                }
                if (!Users.checkPassword(password, cpassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!");
                    txtConfPassword.setBounds(200,310,150,30);
                    txtConfPassword.requestFocus();
                    return;
                }

                boolean user_registered = false;
                try {
                    user_registered = Users.registerUser(username, password, firstname, lastname);
                } catch (Exception ex) {
                    lblError.setText(ex.getMessage());
                    return;
                }

                if (user_registered) {
                    showMessageDialog(null, "The registration complete successfully!");
                    _registerFrame.dispose();
                    _registerFrame = null;
                } else {
                    lblError.setText("An error occurred while registering the user");
                }
            }
        });

        if (Users.LoggedUser == null) {
            lblSignIn.setBounds(20,430,360,30);
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
        }

        lblError.setBounds(15,400,360,30);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setForeground(Color.red);
        add(lblError);
    }

    private void fillValues(boolean can_edit) {
        txtFirstname.setText(Users.LoggedUser.getFirstname());
        txtFirstname.setEditable(can_edit);

        txtLastname.setText(Users.LoggedUser.getLastname());
        txtLastname.setEditable(can_edit);

        txtUsername.setText(Users.LoggedUser.getUsername());
        txtUsername.setEditable(can_edit);

        txtPassword.setText(Users.LoggedUser.getPassword());
        txtPassword.setEditable(can_edit);
    }

    /**
     * Update the users details
     *
     * @return true if the update successful, false in other case
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private boolean update() throws SQLException, ClassNotFoundException {
        if (txtFirstname.getText().isBlank()) {
            showMessageDialog(null, "Please enter your first name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtLastname.getText().isBlank()) {
            showMessageDialog(null, "Please enter your last name", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtUsername.getText().isBlank()) {
            showMessageDialog(null, "Please enter your username", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtPassword.getText().isBlank()) {
            showMessageDialog(null, "Please enter a password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtConfPassword.getText().isBlank()) {
            showMessageDialog(null, "Please confirm password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Users.LoggedUser.setFirstname(txtFirstname.getText());
        Users.LoggedUser.setLastname(txtLastname.getText());
        if (Users.checkPassword(txtPassword.getText(), txtConfPassword.getText())) {
            Users.LoggedUser.setPassword(txtPassword.getText());
        } else {
            showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (_onChangeListener != null) {
            _onChangeListener.actionPerformed(new ActionEvent(_registerFrame, 1, ""));
        }
        showMessageDialog(null, "The contact saved successfully", "Save", JOptionPane.INFORMATION_MESSAGE);
        _registerFrame.dispose();
        _registerFrame=null;

        Connect connection = new Connect();

        String query = "UPDATE users SET username = ?, firstname = ?, lastname = ?, password = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(connection.getURL(), Connect.getDbUsername(), Connect.getDbPassword());
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, txtUsername.getText());
            pstmt.setString(2, txtFirstname.getText());
            pstmt.setString(3, txtLastname.getText());
            pstmt.setString(4, txtPassword.getText());
            pstmt.setInt(5, Users.LoggedUser.getID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // handle exception
            return false;
        }

        return true;
    }

    public static void showRegisterForm() {
        RegisterPanel panel = new RegisterPanel();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(SearchPanel.class.getClassLoader().getResource("images/icon.png")));

        if (_registerFrame == null) {
            _registerFrame = new JFrame();
            _registerFrame.setTitle("Register");
            _registerFrame.add(panel);
            _registerFrame.setSize(new Dimension(400, 500));
            _registerFrame.setResizable(false);
        }
        _registerFrame.setLocationRelativeTo(null);
        _registerFrame.setVisible(true);
        _registerFrame.setIconImage(icon.getImage());
        _registerFrame.setIconImage(icon.getImage());
    }
}