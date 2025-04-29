package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.CustomerManager;
import common.Customer;
import ui.frmLoginCustomer;

public class frmRegisterCustomer extends JFrame {
    private JTextField txtId, txtName, txtPhone, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JLabel lblResult;
    private CustomerManager cm;

    public frmRegisterCustomer() {
        setTitle("🧍 Customer Registration");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        cm = new CustomerManager();
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 240, 245));
        inputPanel.setBorder(BorderFactory.createTitledBorder("📝 Register New Customer"));

        txtId = new JTextField();
        txtName = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();

        JTextField[] fields = {txtId, txtName, txtPhone, txtEmail, txtPassword};
        for (JTextField field : fields) field.setFont(font);

        inputPanel.add(new JLabel("🆔 ID:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("👤 Name:")); inputPanel.add(txtName);
        inputPanel.add(new JLabel("📱 Phone:")); inputPanel.add(txtPhone);
        inputPanel.add(new JLabel("📧 Email:")); inputPanel.add(txtEmail);
        inputPanel.add(new JLabel("🔑 Password:")); inputPanel.add(txtPassword);

        btnRegister = new JButton("➕ Register");
        btnRegister.setBackground(new Color(204, 255, 204));
        btnRegister.setFont(font);

        lblResult = new JLabel(" ", SwingConstants.CENTER);
        lblResult.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        lblResult.setForeground(new Color(102, 0, 102));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnRegister);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(lblResult, BorderLayout.SOUTH);

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerCustomer();
            }
        });

        setVisible(true);
    }

    private void registerCustomer() {
        try {
            Customer c = new Customer(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    txtPhone.getText(),
                    txtEmail.getText(),
                    new String(txtPassword.getPassword())
            );

            cm.Insert(c);

            frmLoginCustomer.loggedInCustomer = c; // اگر خواستی همزمان لاگین کنه

            lblResult.setText("<html>✅ Registered Successfully!<br>"
                    + "👤 Name: " + c.getName() + "<br>"
                    + "📧 Email: " + c.getEmail() + "<br>"
                    + "📱 Phone: " + c.getPhone() + "</html>");

            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    public static void main(String[] args) {
        new frmRegisterCustomer();
    }
}
