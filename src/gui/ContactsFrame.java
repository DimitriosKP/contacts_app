package gui;
import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ContactsFrame extends JFrame implements ActionListener {
    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));

    CardLayout cl = new CardLayout();
    JPanel contactsPanel;
    JScrollPane scrollPane;

    ContactsFrame _contactsFrame;
    JButton btnSearch;
    boolean showingSearchResults=false;

    public ContactsFrame() throws SQLException, ClassNotFoundException {
        setTitle(Users.LoggedUser.getUsername()+"'s contacts");
        setSize(new Dimension(800, 600));
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
    }

    /**
    * Creates the action bar of the logged-in user
    * */
    private void createActionsPanel() {
        JPanel actionsPanel = new JPanel();

        if (Users.LoggedUser.isUser()) {
            btnSearch = new JButton("Search");
            actionsPanel.add(btnSearch);

            btnSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!showingSearchResults) {
                        SearchPanel.showSearchPanel(_contactsFrame);
                    }
                    else {
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
        }

        if (Users.LoggedUser.isUser()) {
            JButton btnAddNewContact = new JButton("Add new contact");
            actionsPanel.add(btnAddNewContact);

            btnAddNewContact.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ContactPanel.showContactForm(null, ContactPanel.VIEW_TYPE.NEW, _contactsFrame);
                }
            });
        }

        JButton btnLogout = new JButton("Logout");
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
            if (Users.LoggedUser.isUser()) {
                if (!c.isOwner(Users.LoggedUser.getID())) continue;
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

            JLabel lblAddress = new JLabel(c.getAddress() + ", " + c.getCity() + ", " + c.getPostcode());
            lblAddress.setFont(new Font("Verdana", Font.PLAIN , 14));
            g.fill = GridBagConstraints.HORIZONTAL;
            g.gridx = 2;
            g.gridy = 1;
            g.gridheight = 0;
            g.weightx = 0.2;
            pane.add(lblAddress, g);

            JButton btnEdit = new JButton("Edit");
            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        ContactsFrame cf = new ContactsFrame();
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

        if (e.getActionCommand().equals("SEARCH")){
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