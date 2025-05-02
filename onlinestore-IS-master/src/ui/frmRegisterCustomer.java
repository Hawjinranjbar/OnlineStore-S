package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.CustomerManager;
import common.Customer;

public class frmRegisterCustomer extends JFrame {
    // اجزای فرم
    private JTextField txtId, txtName, txtPhone, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;
    private JLabel lblResult;
    private CustomerManager cm;
    private JFrame parent; // فرم قبلی که ازش اومده

    // سازنده فرم
    public frmRegisterCustomer(JFrame parent) {
        this.parent = parent;

        // تنظیمات اولیه فرم
        setTitle("🧍 Customer Registration");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // فقط فرم خودش بسته شه
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        cm = new CustomerManager(); // ساخت کلاس مدیریت مشتری‌ها
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        // پنل ورودی اطلاعات
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 240, 245));
        inputPanel.setBorder(BorderFactory.createTitledBorder("📝 Register New Customer"));

        // فیلدهای ورودی
        txtId = new JTextField();
        txtName = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();

        // تنظیم فونت برای همه فیلدها
        JTextField[] fields = {txtId, txtName, txtPhone, txtEmail, txtPassword};
        for (int i = 0; i < fields.length; i++) {
            fields[i].setFont(font);
        }

        // اضافه کردن فیلدها به فرم
        inputPanel.add(new JLabel("🆔 ID:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("👤 Name:")); inputPanel.add(txtName);
        inputPanel.add(new JLabel("📱 Phone:")); inputPanel.add(txtPhone);
        inputPanel.add(new JLabel("📧 Email:")); inputPanel.add(txtEmail);
        inputPanel.add(new JLabel("🔑 Password:")); inputPanel.add(txtPassword);

        // دکمه ثبت‌نام
        btnRegister = new JButton("➕ Register");
        btnRegister.setBackground(new Color(204, 255, 204));
        btnRegister.setFont(font);
        btnRegister.addActionListener(new RegisterButtonListener());

        // دکمه برگشت
        btnBack = new JButton("🔙 Back to Menu");
        btnBack.setBackground(new Color(255, 229, 204));
        btnBack.setFont(font);
        btnBack.addActionListener(new BackButtonListener());

        // لیبل پیام پایین فرم
        lblResult = new JLabel(" ", SwingConstants.CENTER);
        lblResult.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        lblResult.setForeground(new Color(102, 0, 102));

        // پنل دکمه‌ها
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        // اضافه کردن بخش‌ها به فرم
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(lblResult, BorderLayout.SOUTH);

        setVisible(true);
    }

    // رویداد ثبت‌نام
    private class RegisterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                // ساخت آبجکت Customer از فیلدهای فرم
                Customer c = new Customer(
                        Integer.parseInt(txtId.getText()),
                        txtName.getText(),
                        txtPhone.getText(),
                        txtEmail.getText(),
                        new String(txtPassword.getPassword())
                );

                // ذخیره در فایل
                cm.Insert(c);

                // نمایش پیام موفقیت
                JOptionPane.showMessageDialog(frmRegisterCustomer.this, "✅ Customer Registered!");

                // بعد از ثبت‌نام، کاربر می‌ره برای وارد کردن آدرس
                new frmAddAddress(frmRegisterCustomer.this);
                dispose(); // بستن فرم ثبت‌نام

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmRegisterCustomer.this, "❌ Error registering customer.");
            }
        }
    }

    // برگشت به فرم قبلی (مثلاً frmMain)
    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose(); // بستن فرم ثبت‌نام
            if (parent != null) {
                parent.setVisible(true); // برگشت به فرم قبلی
            }
        }
    }

    // اجرای مستقل برای تست فرم
    public static void main(String[] args) {
        new frmRegisterCustomer(null);
    }
}
