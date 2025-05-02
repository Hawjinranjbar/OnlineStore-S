
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.AdminManager;
import common.Admin;

public class frmLoginAdmin extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMessage;
    private AdminManager adminManager;

    public frmLoginAdmin() {
        setTitle("🔐 Admin Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        adminManager = new AdminManager();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Admin Credentials"));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");
        lblMessage = new JLabel(" ", SwingConstants.CENTER);
        lblMessage.setForeground(Color.RED);

        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(btnLogin, BorderLayout.CENTER);
        bottomPanel.add(lblMessage, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        setVisible(true);
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.equals("selin") && password.equals("1234")) {
            JOptionPane.showMessageDialog(this, "✅ Login successful! Welcome Admin.");
            dispose();
            new frmAdminDashboard();
        } else {
            lblMessage.setText("❌ Invalid username or password.");
        }
    }

    public static void main(String[] args) {
        new frmLoginAdmin();
    }
    }