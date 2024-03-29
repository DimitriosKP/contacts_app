package gui;
import api.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class ContactsFrame extends JFrame implements ActionListener {
    private static List<Contact> _contacts;

    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/icon.png")));
    CardLayout cl = new CardLayout();
    JPanel contactsPanel;
    JScrollPane scrollPane;
    ContactsFrame _contactsFrame;
    JButton btnSearch;
    JLabel label = new JLabel();
    boolean showingSearchResults=false;

    public ContactsFrame() throws SQLException, ClassNotFoundException {
        setTitle(Users.LoggedUser.getUsername()+"'s contacts");
        setSize(600,600);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setIconImage(icon.getImage());
        createActionsPanel();
        setIconImage(icon.getImage());

        contactsPanel = new JPanel(cl);
        scrollPane = new JScrollPane(createContactsList(Contacts.getContacts()));
        contactsPanel.add(scrollPane);
        add(contactsPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        _contactsFrame = this;
        _contactsFrame.reloadContacts();
    }

    /**
    * Creates the action bar of the logged-in user
    * */
    private void createActionsPanel() {
        JPanel actionsPanel = new JPanel();

        btnSearch = new JButton("Search");
        _contacts = Contacts.load();
        btnSearch.setEnabled(false);
        for(Contact contact : _contacts) {
            if (contact.get_ownerID() == Users.LoggedUser.getID())
                btnSearch.setEnabled(true);
            else
                btnSearch.setEnabled(false);
        }
        actionsPanel.add(btnSearch);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!showingSearchResults) {
                    SearchPanel.showSearchPanel(_contactsFrame);
                } else {
                    showingSearchResults = false;
                    btnSearch.setText("Search");
                    try {
                        reloadContacts();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });


        JButton btnAddNewContact = new JButton("Add new contact");
        actionsPanel.add(btnAddNewContact);
        btnAddNewContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _contactsFrame.dispose();
                ContactPanel.showContactForm(null, ContactPanel.VIEW_TYPE.NEW, _contactsFrame);
            }
        });

        JButton btnExport = new JButton("Export");
        _contacts = Contacts.load();
        btnExport.setEnabled(false);
        for(Contact contact : _contacts) {
            if (contact.get_ownerID() == Users.LoggedUser.getID())
                btnExport.setEnabled(true);
            else
                btnExport.setEnabled(false);
        }
        actionsPanel.add(btnExport);

        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch data from database using JDBC
                Connect connection = null;
                try {
                    connection = new Connect();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                String query = "SELECT firstname, lastname, phone, email, day, month, year, address, city, postcode FROM contact_table";

                try (Connection conn = DriverManager.getConnection(connection.getURL(),  Connect.getDbUsername(), Connect.getDbPassword());
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query)) {
                    // Format data as VCF and save to file in Downloads folder
                    String downloadsFolderPath = System.getProperty("user.home") + File.separator + "Downloads";
                    String filePath = downloadsFolderPath + File.separator + "data.vcf";
                    FileWriter fileWriter = new FileWriter(filePath);

                    while (rs.next()) {
                        String firstname = rs.getString("firstname");
                        String phone = rs.getString("phone");
                        String lastname = rs.getString("lastname");
                        String email = rs.getString("email");
                        String day = String.format("%02d", Integer.parseInt(rs.getString("day")));
                        String month = String.format("%02d", Integer.parseInt(rs.getString("month")));
                        String year = rs.getString("year");
                        String address = rs.getString("address");
                        String city = rs.getString("city");
                        String postcode = rs.getString("city");
                        String vcf = "BEGIN:VCARD\n"
                                + "VERSION:3.0\n"
                                + "FN;CHARSET=UTF-8:" + firstname + " " + lastname + "\n"
                                + "N:" + lastname + ";" + firstname + ";;" + "\n"
                                + "TEL;TYPE=CELL:" + phone + "\n"
                                + "EMAIL:" + email + "\n"
                                + "BDAY:" + year + month + day + "\n"
                                + "ADR;TYPE=HOME:" + address + ";" + city + ";" + postcode + "\n"
                                + "END:VCARD\n";
                        fileWriter.write(vcf);
                    }
                    fileWriter.close();
                    JOptionPane.showMessageDialog(contactsPanel, "Contacts exported to your Download folder as contacts.vcf", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(contactsPanel, "Failed to export data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnSettings = new JButton("Settings");
        actionsPanel.add(btnSettings);
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPanel.showRegisterForm();
            }
        });

        JButton btnLogout = new JButton("Logout");
        btnLogout.setForeground(Color.red);
        actionsPanel.add(btnLogout);

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Users.LoggedUser = null;
                LoginPanel.showLoginForm();
                _contactsFrame.dispose();
            }
        });

        add(actionsPanel, BorderLayout.NORTH);

        // Create a Timer to update the JLabel every second
        LocalDate date = LocalDate.now();
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormatter.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalTime now = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedTime = now.format(formatter);

                // Update the text of the JLabel with the current time and date
                label.setText(formattedTime + ", " + formattedDate);
            }
        });
        timer.start();
        add(label, BorderLayout.SOUTH);
    }

    /**
     * It creates a panel in a grid layout and puts the contacts in lines
     *
     * @return Returns the contact details panel
     */
    public JPanel createContactsList(List<Contact> contacts) {
        JPanel contactsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        for(Contact c : contacts) {
            if (!c.isOwner(Users.LoggedUser.getID()))
                continue;

            if (Contacts.checkBirthday(c.getDay(), c.getMonth())) {
                showMessageDialog(null, "It's " + c.getFirstname() + " " + c.getLastname() + "'s birthday today!", "Birthday!", JOptionPane.INFORMATION_MESSAGE);
            }

            JPanel pane = new JPanel(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();

            // We create for each contact a line in our scrollpanel
            JLabel lblName = new JLabel(c.getFirstname() + " " + c.getLastname());
            lblName.setFont(new Font("Verdana",Font.BOLD, 24));
            lblName.setSize(300,20);
            g.fill = GridBagConstraints.HORIZONTAL;
            g.gridx = 0;
            g.gridy = 0;
            g.insets = new Insets(0,10,0,0);
            pane.add(lblName, g);

            JLabel lblPhone = new JLabel(c.getPhone() );
            lblPhone.setFont(new Font("Verdana",Font.BOLD, 20));
            g.fill = GridBagConstraints.HORIZONTAL;
            g.gridx = 1;
            g.gridy = 1;
            g.weightx = 1;
            pane.add(lblPhone, g);

            JLabel lblEmail = new JLabel(c.getEmail() );
            lblEmail.setFont(new Font("Verdana", Font.PLAIN , 14));
            g.fill = GridBagConstraints.HORIZONTAL;
            g.gridx = 0;
            g.gridy = 1;
            g.insets = new Insets(0,10,0,0);
            pane.add(lblEmail, g);

            JLabel lblBirthday = new JLabel("Birthday at " + c.getDay()+"/"+c.getMonth()+"/"+c.getYear());
            lblBirthday.setFont(new Font("Verdana", Font.PLAIN , 12));
            g.fill = GridBagConstraints.HORIZONTAL;
            g.gridx = 2;
            g.gridy = 0;
            g.gridheight = 0;
            g.weightx = 0.2;
            pane.add(lblBirthday, g);

            JLabel lblAddress = new JLabel((!c.getAddress().isBlank() ? c.getAddress() + ", " + c.getCity() + ", " + c.getPostcode() : c.getCity() + ", " + c.getPostcode()));
            lblAddress.setFont(new Font("Verdana", Font.PLAIN , 14));
            g.fill = GridBagConstraints.HORIZONTAL;
            g.gridx = 2;
            g.gridy = 1;
            g.gridheight = 0;
            g.weightx = 0.2;
            pane.add(lblAddress, g);

            JLabel lblDateOfSave = new JLabel("The contact saved at: " + c.getDateOfSave());
            lblDateOfSave.setFont(new Font("Verdana", Font.ITALIC , 10));
            g.fill = GridBagConstraints.HORIZONTAL;
            g.gridx = 0;
            g.gridy = 2;
            g.gridheight = 0;
            g.weightx = 0.2;
            pane.add(lblDateOfSave, g);

            JButton btnEdit = new JButton("Edit");
            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _contactsFrame.dispose();
                    try {
                        new ContactsFrame();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            pane.setPreferredSize(new Dimension(this.getWidth()-50,100));

            pane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pane.setBorder(BorderFactory.createRaisedBevelBorder());

            pane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    _contactsFrame.dispose();
                    ContactPanel.VIEW_TYPE view_type = ContactPanel.VIEW_TYPE.VIEW;
                    // After modifying the accommodations, we need to reload the form with the new data
                    // So we pass the current Frame that implements ActionListener as the ActionListenter argument
                    ContactPanel.showContactForm(c, view_type, _contactsFrame);
                }
            });

            // effects when clicked
            pane.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    var jp = (JPanel) e.getSource();
                    jp.setBorder(BorderFactory.createLoweredBevelBorder());
                    jp.invalidate();
                    jp.validate();
                }
            });
            pane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    var jp = (JPanel) e.getSource();
                    jp.setBorder(BorderFactory.createRaisedBevelBorder());
                    jp.invalidate();
                    jp.validate();
                }
            });
            contactsPanel.add(pane, gbc);
        }
        return contactsPanel;
    }
    /**
     * Creates the list of all contacts after modification
     *
     */
    public void reloadContacts() throws SQLException, ClassNotFoundException {
        reloadContacts(Contacts.getContacts());
    }

    /**
     * Creates the list of contacts we define
     *
     * @param contacts The contacts we want to be listed
     */
    public void reloadContacts(List<Contact> contacts) {
        contactsPanel.remove(scrollPane);
        scrollPane = new JScrollPane(createContactsList(contacts));
        contactsPanel.add(scrollPane);
        contactsPanel.invalidate();
        contactsPanel.validate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e == null) return;

        if (e.getActionCommand().equals("SEARCH")) {
            Map<String,String> terms = ((SearchPanel)e.getSource()).getSearchTerms();

            List<Contact> contacts;   // We want a copy of the original list, so we don't affect it
            try {
                contacts = new LinkedList<>(Contacts.getContacts());
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            List<Contact> to_remove = new LinkedList<>();
            for (Contact c : contacts){
                if (terms.containsKey("firstname") && !terms.get("firstname").isBlank()) {
                    if (!c.getFirstname().toLowerCase().contains(terms.get("firstname").toLowerCase())) {
                        to_remove.add(c);
                    }
                }
                if (terms.containsKey("lastname") && !terms.get("lastname").isBlank()) {
                    if (!c.getLastname().toLowerCase().contains(terms.get("lastname").toLowerCase())) {
                        to_remove.add(c);
                    }
                }
                if (terms.containsKey("phone") && !terms.get("phone").isBlank()) {
                    if (!c.getPhone().toLowerCase().contains(terms.get("phone").toLowerCase())) {
                        to_remove.add(c);
                    }
                }
                if (terms.containsKey("address") && !terms.get("address").isBlank()) {
                    if (!c.getAddress().toLowerCase().contains(terms.get("address").toLowerCase())) {
                        to_remove.add(c);
                    }
                }
                if (terms.containsKey("city") && !terms.get("city").isBlank()) {
                    if (!c.getCity().toLowerCase().contains(terms.get("city").toLowerCase())) {
                        to_remove.add(c);
                    }
                }
                if (terms.containsKey("postcode") && !terms.get("postcode").isBlank()) {
                    if (!c.getPostcode().toLowerCase().contains(terms.get("postcode").toLowerCase())) {
                        to_remove.add(c);
                    }
                }
            }
            contacts.removeAll(to_remove);
            reloadContacts(contacts);

            showingSearchResults=true;
            btnSearch.setText("All contacts");
            return;
        }
        try {
            reloadContacts();
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}