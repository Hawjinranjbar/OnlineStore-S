package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.CustomerManager;
import common.Customer;

public class frmLoginCustomer extends JFrame {
    public static Customer loggedInCustomer = null; // مشتری واردشده رو به صورت عمومی نگه می‌داره

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnBack;
    private CustomerManager cm;
    private String source; // مشخص می‌کنه از کجا اومده (مثلاً "cart")
    private JFrame parent; // فرم قبلی (مثلاً فروشگاه یا سبد)

    // کانستراکتورهای مختلف برای سازگاری با حالت‌های مختلف
    public frmLoginCustomer() {
        this(null, "default");
    }

    public frmLoginCustomer(String source) {
        this(null, source);
    }

    public frmLoginCustomer(JFrame parent) {
        this(parent, "default");
    }

    // کانستراکتور اصلی که بقیه بهش وصل می‌شن
    public frmLoginCustomer(JFrame parent, String source) {
        this.parent = parent;
        this.source = source;

        // تنظیمات کلی فرم
        setTitle("🔐 Customer Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // فقط این فرم بسته بشه
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        cm = new CustomerManager(); // بارگذاری مشتری‌ها

        // ساخت پنل اطلاعات ورود
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(255, 240, 245));
        panel.setBorder(BorderFactory.createTitledBorder("Login Info"));

        txtEmail = new JTextField();
        txtPassword = new JPasswordField();

        // افزودن فیلدهای ورود به فرم
        panel.add(new JLabel("📧 Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("🔑 Password:"));
        panel.add(txtPassword);

        // دکمه لاگین
        btnLogin = new JButton("🔓 Login");
        btnLogin.setBackground(new Color(204, 255, 204));
        btnLogin.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btnLogin.addActionListener(new LoginButtonListener());

        // دکمه بازگشت
        btnBack = new JButton("🔙 Back");
        btnBack.setBackground(new Color(255, 229, 204));
        btnBack.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btnBack.addActionListener(new BackButtonListener());

        // پنل دکمه‌ها
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 240, 245));
        btnPanel.add(btnLogin);
        btnPanel.add(btnBack);

        // اضافه کردن پنل‌ها به فرم
        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true); // نمایش فرم
    }

    // کلاس داخلی برای دکمه لاگین
    private class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());

            // چک کردن اطلاعات با داده‌های customer.txt
            Customer customer = cm.findByEmailAndPassword(email, password);

            if (customer != null) {
                // اگر لاگین موفق بود
                loggedInCustomer = customer; // ذخیره مشتری وارد شده
                JOptionPane.showMessageDialog(frmLoginCustomer.this,
                        "✅ Login successful!\nWelcome, " + customer.getName());

                dispose(); // بستن فرم لاگین

                // اگر از سمت سبد خرید اومده باشه، بره به فرم سبد
                if (source.equalsIgnoreCase("cart")) {
                    new frmCart(frmLoginCustomer.this.parent);
                } else {
                    // در غیر اینصورت، فقط برگرد به فرم قبلی
                    if (parent != null) {
                        parent.setVisible(true);
                    }
                }
            } else {
                // اگر ایمیل یا رمز اشتباه بود
                JOptionPane.showMessageDialog(frmLoginCustomer.this, "❌ Invalid email or password.");
            }
        }
    }

    // کلاس داخلی برای دکمه برگشت
    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose(); // بستن فرم
            if (parent != null) {
                parent.setVisible(true); // باز کردن فرم قبلی اگه بود
            }
        }
    }

    // اجرای تست مستقل
    public static void main(String[] args) {
        new frmLoginCustomer();
    }
}
