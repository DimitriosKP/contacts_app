package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SearchPanel extends JPanel {
    static ActionListener  _onSearchListener = null;
    private static JFrame _search_frame;
    private static JPanel _search_panel;
    Map<String, String> _search_terms = new HashMap<>();
    public SearchPanel(){
        int width=600;
        int top = 10;
        int left = 40;

        _search_panel = this;
        setLayout(null);

        JLabel lblTitle = new JLabel("Search");
        JLabel lblFName = new JLabel("Name");
        JLabel lblLName = new JLabel("Surname");
        JLabel lblPhone = new JLabel("Phone number");
        JLabel lblEmail = new JLabel("Email");
        JLabel lblAddress = new JLabel("Address");
        JLabel lblCity = new JLabel("City");
        JLabel lblPostcode = new JLabel("Postcode");

        JTextField txtFName = new JTextField();
        JTextField txtLName = new JTextField();
        JTextField txtPhone = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtAddress = new JTextField();
        JTextField txtCity = new JTextField();
        JTextField txtPostcode = new JTextField();

        lblTitle.setFont(new Font("Verdana",Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0,top, width,30);
        add(lblTitle);

        top+=40;    // 120
        lblFName.setBounds(left,top,100,30);
        add(lblFName);

        top+=30;    // 150
        txtFName.setBounds(left,top,width-100,30);
        add(txtFName);

        top+=40;    // 120
        lblLName.setBounds(left,top,100,30);
        add(lblLName);

        top+=30;    // 150
        txtLName.setBounds(left,top,width-100,30);
        add(txtLName);

        top+=40;    // 120
        lblPhone.setBounds(left,top,100,30);
        add(lblPhone);

        top+=30;    // 150
        txtPhone.setBounds(left,top,width-100,30);
        add(txtPhone);

        top+=40;    // 120
        lblEmail.setBounds(left,top,100,30);
        add(lblEmail);

        top+=30;    // 150
        txtEmail.setBounds(left,top,width-100,30);
        add(txtEmail);


        top+=40;    // 190
        lblAddress.setBounds(left,top,100,30);
        add(lblAddress);

        lblCity.setBounds(275,top,100,30);
        add(lblCity);

        lblPostcode.setBounds(440,top,100,30);
        add(lblPostcode);

        top+=30;    // 210
        txtAddress.setBounds(left,top,220,30);
        add(txtAddress);

        txtCity.setBounds(275,top,150,30);
        add(txtCity);

        txtPostcode.setBounds(440,top,100,30);
        add(txtPostcode);

        top+=40;    // 250

        top+=50;
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(150, top,300,30);
        add(btnSearch);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _search_terms.put("firstname", txtFName.getText());
                _search_terms.put("lastname", txtLName.getText());
                _search_terms.put("phone", txtPhone.getText());
                _search_terms.put("email", txtEmail.getText());
                _search_terms.put("address", txtAddress.getText());
                _search_terms.put("postcode", txtPostcode.getText());
                _search_terms.put("city", txtCity.getText());

                if (_onSearchListener != null){
                    _onSearchListener.actionPerformed(new ActionEvent(_search_panel, 1, "SEARCH"));
                }
                _search_frame.dispose();
            }
        });

    }

    public Map<String,String> getSearchTerms(){
        return _search_terms;
    }
    public static void showSearchPanel(ActionListener onSearchListener){
        int width = 600;
        int height = 600;
        _onSearchListener = onSearchListener;

        if (_search_frame != null) {
            _search_frame.dispose();
        }
        SearchPanel panel = new SearchPanel();
        _search_frame = new JFrame();
        _search_frame.setTitle("Search");

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setPreferredSize(new Dimension(width - 50, 1000));

        _search_frame.add(new JScrollPane(scroll));
        _search_frame.setSize(new Dimension(width, height));

        _search_frame.setResizable(false);

        _search_frame.setLocationRelativeTo(null);
        _search_frame.setVisible(true);
    }
}