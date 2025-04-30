package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.CustomerManager;
import common.Customer;

public class frmRegisterCustomer extends JFrame {
    private JTextField txtId, txtName, txtPhone, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;
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
        btnRegister.addActionListener(new RegisterButtonListener());

        btnBack = new JButton("🔙 Back to Menu");
        btnBack.setBackground(new Color(255, 229, 204));
        btnBack.setFont(font);
        btnBack.addActionListener(new BackButtonListener());

        lblResult = new JLabel(" ", SwingConstants.CENTER);
        lblResult.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        lblResult.setForeground(new Color(102, 0, 102));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(lblResult, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class RegisterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Customer c = new Customer(
                        Integer.parseInt(txtId.getText()),
                        txtName.getText(),
                        txtPhone.getText(),
                        txtEmail.getText(),
                        new String(txtPassword.getPassword())
                );

                cm.Insert(c);
                JOptionPane.showMessageDialog(frmRegisterCustomer.this, "✅ Customer Registered!");

                new frmAddAddress(); // بعد از ثبت‌نام، فرم آدرس باز میشه
                dispose(); // فرم ثبت‌نام بسته میشه
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmRegisterCustomer.this, "❌ Error registering customer.");
            }
        }
    }

    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            new frmMain(); // بازگشت به منو
        }
    }

    public static void main(String[] args) {
        new frmRegisterCustomer();
    }
}
