package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

import managers.OrderManager;
import managers.AddressManager;
import managers.CustomerManager;
import common.*;

public class frmOrder extends JFrame {
    private JTextArea txtOrderList;      // لیست فاکتورها
    private JButton btnRefresh, btnBack; // دکمه‌ها
    private OrderManager om;
    private AddressManager am;
    private CustomerManager cm;
    private JFrame parent;               // فرم قبلی

    private boolean showAllOrders = true;  // مشخص‌کننده اینکه همه سفارش‌ها نمایش داده بشن یا فقط سفارش‌های کاربر

    // کانستراکتور پیش‌فرض (برای frmAdminDashboard)
    public frmOrder(JFrame parent) {
        this(parent, true); // پیش‌فرض: نمایش همه سفارش‌ها
    }

    // کانستراکتور اصلی با کنترل نمایش سفارش‌ها
    public frmOrder(JFrame parent, boolean showAllOrders) {
        this.parent = parent;
        this.showAllOrders = showAllOrders;

        setTitle("📦 Orders List");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        // ساخت کلاس‌های مدیریتی
        om = new OrderManager();
        am = new AddressManager();
        cm = new CustomerManager();

        // ساخت فونت
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        // ناحیه لیست سفارش‌ها
        txtOrderList = new JTextArea();
        txtOrderList.setEditable(false);
        txtOrderList.setFont(font);
        JScrollPane scrollPane = new JScrollPane(txtOrderList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Orders Overview"));

        // دکمه‌های پایین
        btnRefresh = new JButton("🔄 Refresh Orders");
        btnRefresh.setFont(font);
        btnRefresh.setBackground(new Color(204, 229, 255));
        btnRefresh.addActionListener(new RefreshButtonListener());

        btnBack = new JButton("🔙 Back");
        btnBack.setFont(font);
        btnBack.setBackground(new Color(255, 228, 240));
        btnBack.addActionListener(new BackButtonListener());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnBack);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshOrderList(); // نمایش اولیه سفارش‌ها
        setVisible(true);
    }

    // نمایش لیست سفارش‌ها
    private void refreshOrderList() {
        Order[] orders = om.SelectAll();           // گرفتن همه سفارش‌ها از فایل
        Address[] addresses = am.SelectAll();      // گرفتن همه آدرس‌ها
        Customer[] customers = cm.SelectAll();     // گرفتن همه مشتری‌ها

        StringBuilder sb = new StringBuilder();

        // گرفتن آیدی مشتری اگه لازم باشه فقط سفارش خودش رو ببینیم
        int currentCustomerId = -1;
        if (!showAllOrders && frmLoginCustomer.loggedInCustomer != null) {
            currentCustomerId = frmLoginCustomer.loggedInCustomer.getId();
        }

        boolean found = false; // بررسی اینکه چیزی پیدا شده یا نه

        for (int i = 0; i < orders.length; i++) {
            Order o = orders[i];

            if (o != null) {
                // فیلتر برای حالت مشتری لاگین‌شده
                if (!showAllOrders && o.getCustomerId() != currentCustomerId) {
                    continue; // این سفارش متعلق به مشتری لاگین‌شده نیست
                }

                found = true; // حداقل یک سفارش پیدا شده

                // پیدا کردن اطلاعات مشتری
                Customer customer = null;
                for (Customer c : customers) {
                    if (c != null && c.getId() == o.getCustomerId()) {
                        customer = c;
                        break;
                    }
                }

                // پیدا کردن اطلاعات آدرس
                Address address = null;
                for (Address a : addresses) {
                    if (a != null && a.getId() == o.getAddressId()) {
                        address = a;
                        break;
                    }
                }

                // ساخت متن نمایش فاکتور
                sb.append(i + 1).append(". 🧾 Order ID: ").append(o.getOrderId()).append("\n");

                if (customer != null) {
                    sb.append("👤 ").append(customer.getName())
                            .append(" | 📧 ").append(customer.getEmail())
                            .append(" | 📱 ").append(customer.getPhone()).append("\n");
                } else {
                    sb.append("👤 Customer ID: ").append(o.getCustomerId()).append(" (Unknown)\n");
                }

                if (address != null) {
                    sb.append("🏠 Address: ").append(address.getCity()).append(", ")
                            .append(address.getStreet()).append("\n")
                            .append("📬 Postal Code: ").append(address.getPostalCode()).append("\n")
                            .append("📝 Details: ").append(address.getDetails()).append("\n");
                } else {
                    sb.append("🏠 Address ID: ").append(o.getAddressId()).append(" (Unknown)\n");
                }

                sb.append("💰 Total: ").append(formatPrice(o.getTotalAmount())).append(" Toman\n")
                        .append("🎟️ Discount Code: ").append(o.getDiscountCode()).append("\n")
                        .append("🛒 Items: ").append(o.getCartItems()).append("\n")
                        .append("📅 Date: ").append(o.getOrderDate()).append("\n")
                        .append("--------------------------------------------------\n");
            }
        }

        if (!found) {
            sb.append("🥺 No orders found.");
        }

        txtOrderList.setText(sb.toString());
    }

    // فرمت عددی برای قیمت
    private String formatPrice(double price) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        return nf.format(price);
    }

    // دکمه Refresh
    private class RefreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshOrderList();
        }
    }

    // دکمه برگشت
    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            if (parent != null) parent.setVisible(true);
        }
    }

    // اجرای مستقل فرم
    public static void main(String[] args) {
        new frmOrder(null, true); // پیش‌فرض: همه سفارش‌ها رو نشون بده
    }
}
