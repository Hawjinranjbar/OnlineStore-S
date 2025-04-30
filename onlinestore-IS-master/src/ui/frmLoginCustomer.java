package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.CustomerManager;
import common.Customer;

public class frmLoginCustomer extends JFrame {
    public static Customer loggedInCustomer = null;

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnBack;
    private CustomerManager cm;
    private String source;

    public frmLoginCustomer() {
        this("default");
    }

    public frmLoginCustomer(String source) {
        this.source = source;

        setTitle("🔐 Customer Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        cm = new CustomerManager();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(255, 240, 245));
        panel.setBorder(BorderFactory.createTitledBorder("Login Info"));

        txtEmail = new JTextField();
        txtPassword = new JPasswordField();

        panel.add(new JLabel("📧 Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("🔑 Password:"));
        panel.add(txtPassword);

        btnLogin = new JButton("🔓 Login");
        btnLogin.setBackground(new Color(204, 255, 204));
        btnLogin.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btnLogin.addActionListener(new LoginButtonListener());

        btnBack = new JButton("🔙 Back to Menu");
        btnBack.setBackground(new Color(255, 229, 204));
        btnBack.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btnBack.addActionListener(new BackButtonListener());

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 240, 245));
        btnPanel.add(btnLogin);
        btnPanel.add(btnBack);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());

            Customer customer = cm.findByEmailAndPassword(email, password);

            if (customer != null) {
                loggedInCustomer = customer;
                JOptionPane.showMessageDialog(frmLoginCustomer.this,
                        "✅ Login successful!\nWelcome, " + customer.getName());

                dispose();

                if (source.equalsIgnoreCase("cart")) {
                    new frmCart();
                }
            } else {
                JOptionPane.showMessageDialog(frmLoginCustomer.this, "❌ Invalid email or password.");
            }
        }
    }

    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            new frmMain();
        }
    }

    public static void main(String[] args) {
        new frmLoginCustomer();
    }
}
