package gui;

import api.Contacts;
import api.Contact;
import api.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import static javax.swing.JOptionPane.showMessageDialog;

public class ContactPanel extends JPanel {
    static JFrame _contact_frame;
    ActionListener _onChangeListener = null;

    // The form will be used to view, edit and create a new contact
    // When creating it, we will initialize it according to the value of VIEW_TYPE
    public enum VIEW_TYPE {
        VIEW,
        NEW,
        EDIT
    }

    private Contact _contact;

    JTextField txtFname = new JTextField();
    JTextField txtLname = new JTextField();
    JComboBox<Integer> day = new JComboBox<>();
    JComboBox<Integer> month = new JComboBox<>();
    JComboBox<Integer> year = new JComboBox<>();
    JTextField txtPhone = new JTextField();
    JTextField txtEmail = new JTextField();
    JTextField txtAddress = new JTextField();
    JTextField txtCity = new JTextField();
    JTextField txtPostcode = new JTextField();
    JTextField txtBirthday = new JTextField();


    /**
     * Create the form where a contact will be viewed/edited/created
     * @param contact the contact, if it is a contact view or edit
     * @param view_type the view type of the form view/edit/create
     * @param onChangeListener an ActionListener to update the main form with changes made to the contact
     */
    public ContactPanel(Contact contact, VIEW_TYPE view_type, ActionListener onChangeListener) {
        int width = 600;

        JLabel lblBs = new JLabel("/");
        JLabel lblBs2 = new JLabel("/");

        JLabel lblTitle = new JLabel();
        JLabel lblFName = new JLabel("Name");
        JLabel lblLName = new JLabel("Surname");
        JLabel lblPhone = new JLabel("Phone number");
        JLabel lblEmail = new JLabel("Email address");
        JLabel lblAddress = new JLabel("Home Address");
        JLabel lblCity = new JLabel("City");
        JLabel lblPostcode = new JLabel("Postcode");
        JLabel lblBirthday = new JLabel("Birthday");

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");

        setLayout(null);
        _contact = contact;
        _onChangeListener = onChangeListener;

        if (view_type == VIEW_TYPE.EDIT) {
            lblTitle.setText("Edit Contact");
        }
        else if (view_type == VIEW_TYPE.NEW) {
            lblTitle.setText("Add Contact");
        }
        else{
            lblTitle.setText("Show Contact");
        }

        int top = 10;
        int left = 40;

        lblTitle.setFont(new Font("Verdana",Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0,top, width,30);
        add(lblTitle);

        top+=40;    // 120
        lblFName.setBounds(left,top,100,30);
        add(lblFName);

        top+=30;    // 150
        txtFname.setBounds(left,top, 250,30);
        add(txtFname);

        top+=40;    // 120
        lblLName.setBounds(left,top,100,30);
        add(lblLName);

        top+=30;    // 150
        txtLname.setBounds(left,top,250,30);
        add(txtLname);

        top+=40;    // 190
        lblPhone.setBounds(left,top,100,30);
        add(lblPhone);

        top+=30;    // 150
        txtPhone.setBounds(left,top,100,30);
        add(txtPhone);

        top+=40;    // 190
        lblEmail.setBounds(left,top,100,30);
        add(lblEmail);

        top+=30;    // 150
        txtEmail.setBounds(left,top,250,30);
        add(txtEmail);

        top+=40;
        lblBirthday.setBounds(left,top,50,30);
        add(lblBirthday);

        if (view_type == VIEW_TYPE.VIEW) {
            top += 30;
            txtBirthday.setBounds(left, top, 200, 30);
            txtBirthday.setText(_contact.setBirthday(_contact.getDay(), _contact.getMonth(), _contact.getYear()));
            txtBirthday.setEditable(false);
            add(txtBirthday);
        }
        else if (view_type == VIEW_TYPE.NEW || view_type == VIEW_TYPE.EDIT) {
            for (int i = 1; i <= 31; i++) { day.addItem(i); }
            for (int i = 1; i <= 12; i++) { month.addItem(i); }
            for (int i = 1951; i <= LocalDate.now().getYear(); i++) { year.addItem(i); }

            top += 30;
            day.setBounds(left, top, 50, 30);
            add(day);

            left += 60;
            lblBs.setBounds(left, top, 50, 30);
            lblBs.setFont(new Font("Verdana", Font.PLAIN, 20));
            add(lblBs);

            left += 20;
            month.setBounds(left, top, 50, 30);
            add(month);

            left += 60;
            lblBs2.setBounds(left, top, 50, 30);
            lblBs2.setFont(new Font("Verdana", Font.PLAIN, 20));
            add(lblBs2);

            left += 20;
            year.setBounds(left, top, 80, 30);
            add(year);
        }

        left=40;
        top+=40;    // 190
        lblAddress.setBounds(left,top,100,30);
        add(lblAddress);

        lblCity.setBounds(275,top,100,30);
        add(lblCity);

        lblPostcode.setBounds(440,top,100,30);
        add(lblPostcode);

        top+=30;    // 190
        txtAddress.setBounds(left,top,220,30);
        add(txtAddress);

        txtCity.setBounds(275,top,150,30);
        add(txtCity);

        txtPostcode.setBounds(440,top,100,30);
        add(txtPostcode);

        top+=60;

        // Save and cancel button will only appear in case of edit or new entry
        if (view_type == VIEW_TYPE.EDIT) {
            btnSave.setBounds(left + 180, top, 120, 30);
            add(btnSave);

            btnCancel.setBounds(left + 380, top, 120, 30);
            add(btnCancel);

            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        store(view_type);
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _contact_frame.dispose();
                }
            });
        }
        if(view_type == VIEW_TYPE.NEW){
            btnSave.setBounds(left + 180, top, 120, 30);
            add(btnSave);

            btnCancel.setBounds(left + 380, top, 120, 30);
            add(btnCancel);

            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        store(view_type);
                    } catch (ClassNotFoundException | SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _contact_frame.dispose();
                }
            });

        }
        if (view_type == VIEW_TYPE.VIEW) {
            btnEdit.setBounds(left + 180, top, 120, 30);
            add(btnEdit);

            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ContactPanel.showContactForm(contact, ContactPanel.VIEW_TYPE.EDIT, onChangeListener);
                }
            });
        }

        // The delete button will only appear in case of editing
        if (view_type == VIEW_TYPE.EDIT) {
            btnDelete.setBounds(left, top, 120, 30);
            btnDelete.setForeground(Color.RED);
            add(btnDelete);

            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the contact?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            if(Contacts.deleteContact(Contacts.getContactsID()))
                                showMessageDialog(null, "The contact deleted successfully", "Delete", JOptionPane.INFORMATION_MESSAGE);
                            else
                                showMessageDialog(null, "Error due to delete contact", "Error", JOptionPane.ERROR_MESSAGE);

                            Contacts.load();

                            if (_onChangeListener != null) {
                                _onChangeListener.actionPerformed(new ActionEvent(_contact_frame, 1, ""));
                            }

                            _contact_frame.dispose();
                            _contact_frame = null;
                        } catch (SQLException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }

        // Initialize the fields with the existing values if a contact is being viewed or edited.
        if (view_type == VIEW_TYPE.EDIT || view_type == VIEW_TYPE.VIEW) {
            fillValues(view_type == VIEW_TYPE.EDIT);
        }
    }

    /**
     * When creating it, we will initialize it according to the value of VIEW_TYPE
     * Initializes the form according to whether the fields can be edited or not
     * @param can_edit If the user can modify the field values
     */
    private void fillValues(boolean can_edit) {
        txtFname.setText(_contact.getFirstname());
        txtFname.setEditable(can_edit);

        txtLname.setText(_contact.getLastname());
        txtLname.setEditable(can_edit);

        day.setSelectedIndex(selectedDay(_contact.getDay()));
        month.setSelectedIndex(selectedMonth(_contact.getMonth()));
        year.setSelectedIndex(selectedYear(_contact.getYear()));

        txtPhone.setText(_contact.getPhone());
        txtPhone.setEditable(can_edit);

        txtEmail.setText(_contact.getEmail());
        txtEmail.setEditable(can_edit);

        txtAddress.setText(_contact.getAddress());
        txtAddress.setEditable(can_edit);

        txtCity.setText(_contact.getCity());
        txtCity.setEditable(can_edit);

        txtPostcode.setText(_contact.getPostcode());
        txtPostcode.setEditable(can_edit);

    }
    private int selectedDay(int value) {
        for (int i=0; i < day.getItemCount(); i++) {
            if (day.getItemAt(i).equals(value)) {
                day.setSelectedIndex(i);
                return i;
            }
        }
        return -1;
    }
    private int selectedMonth(int value) {
        for (int i=0; i < month.getItemCount(); i++) {
            if (month.getItemAt(i).equals(value)) {
                month.setSelectedIndex(i);
                return i;
            }
        }
        return -1;
    }
    private int selectedYear(int value) {
        for (int i=0; i < year.getItemCount(); i++) {
            if (year.getItemAt(i).equals(value)) {
                year.setSelectedIndex(i);
                return i;
            }
        }
        return -1;
    }

    private void store(VIEW_TYPE view_type) throws SQLException, ClassNotFoundException {
        if (txtFname.getText().isBlank()) {
            showMessageDialog(null, "Please enter contacts first name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtLname.getText().isBlank()) {
            showMessageDialog(null, "Please enter contacts last name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*
        if (txtAddress.getText().isBlank()){
            showMessageDialog(null, "Please enter the contacts address", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }*/

        if (txtPhone.getText().isBlank()) {
            showMessageDialog(null, "Please enter a phone number", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!txtPhone.getText().matches("[0-9]+")) {
            showMessageDialog(null, "Your phone number is not correct", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtPhone.getText().startsWith("69")) {
            if(txtPhone.getText().length() != 10) {
                showMessageDialog(null, "Your phone number is not correct", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (txtEmail.getText().isBlank()){
            showMessageDialog(null, "Please enter an email address", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!txtEmail.getText().contains("@")) {
            showMessageDialog(null, "Please check your email address", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (_contact == null) {
            _contact = new Contact(Users.LoggedUser.getID(), txtFname.getText(), txtLname.getText(), day.getSelectedItem().toString(), month.getSelectedItem().toString(), year.getSelectedItem().toString(), txtPhone.getText(), txtEmail.getText(), txtAddress.getText(), txtCity.getText(), txtPostcode.getText());
            if (!Contacts.addContact(_contact)) {
                showMessageDialog(null, "Failed to store contact", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            _contact.setOwner(Users.LoggedUser.getID());
            _contact.setFirstname(txtFname.getText());
            _contact.setLastname(txtLname.getText());
            _contact.setDay(Integer.parseInt(day.getSelectedItem().toString()));
            _contact.setMonth(Integer.parseInt(month.getSelectedItem().toString()));
            _contact.setYear(Integer.parseInt(year.getSelectedItem().toString()));
            _contact.setPhone(txtPhone.getText());
            _contact.setEmail(txtEmail.getText());
            _contact.setAddress(txtAddress.getText());
            _contact.setCity(txtCity.getText());
            _contact.setPostcode(txtPostcode.getText());
        }

        // Αποθηκεύουμε τις αλλαγές
        if(view_type == VIEW_TYPE.NEW) {
            if (!Contacts.store(_contact)) {
                showMessageDialog(null, "Save failed", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (_onChangeListener != null) {
                _onChangeListener.actionPerformed(new ActionEvent(_contact_frame, 1, ""));
            }
            showMessageDialog(null, "The contact saved successfully", "Save", JOptionPane.INFORMATION_MESSAGE);
        }
        if (view_type == VIEW_TYPE.EDIT){
            if (!Contacts.update(_contact)) {
                showMessageDialog(null, "Update failed", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (_onChangeListener != null) {
                _onChangeListener.actionPerformed(new ActionEvent(_contact_frame, 1, ""));
            }
            showMessageDialog(null, "The changes are saved successfully", "Save", JOptionPane.INFORMATION_MESSAGE);
        }

        // We need to inform the main form that a change has been made so that it reloads the results.
        // We do it through the listener we defined when creating the window

        _contact_frame.dispose();
        _contact_frame=null;
    }

    public static void showContactForm(Contact contact, VIEW_TYPE view_type, ActionListener onChangeListener) {
        int width = 600;
        int height = 600;

        if (_contact_frame != null) {
            _contact_frame.dispose();
        }
        ContactPanel panel = new ContactPanel(contact, view_type, onChangeListener );
        _contact_frame = new JFrame();

        _contact_frame.setTitle("Contact");

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setPreferredSize(new Dimension(width - 50, 100));

        _contact_frame.add(new JScrollPane(scroll));
        _contact_frame.setSize(new Dimension(width, height));

        _contact_frame.setResizable(false);

        _contact_frame.setLocationRelativeTo(null);
        _contact_frame.setVisible(true);
    }
}