package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

import managers.OrderManager;
import managers.AddressManager;
import managers.CustomerManager;

import common.Order;
import common.Address;
import common.Customer;

public class frmOrder extends JFrame {
    // 🎯 کامپوننت‌های گرافیکی
    private JTextArea txtOrderList;
    private JButton btnRefresh, btnBack;

    // 🎛️ مدیرها برای خوندن داده‌ها
    private OrderManager om;
    private AddressManager am;
    private CustomerManager cm;

    private JFrame parent; // فرم قبلی (برای برگشت)

    public frmOrder(JFrame parent) {
        this.parent = parent;

        setTitle("📦 Orders List"); // عنوان فرم
        setSize(800, 600); // اندازه فرم
        setLocationRelativeTo(null); // وسط صفحه
        setDefaultCloseOperation(EXIT_ON_CLOSE); // بستن کامل فرم
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        // 🛠️ اتصال به لایه مدیریت فایل‌ها
        om = new OrderManager();
        am = new AddressManager();
        cm = new CustomerManager();

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        // 📜 جعبه نمایش لیست سفارش‌ها
        txtOrderList = new JTextArea();
        txtOrderList.setEditable(false);
        txtOrderList.setFont(font);
        JScrollPane scrollPane = new JScrollPane(txtOrderList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Orders Overview"));

        // 🔄 دکمه‌های پایین صفحه
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

        // ⬇️ اضافه کردن به فرم
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 🚀 اجرای اولیه: لود لیست سفارش‌ها
        refreshOrderList();
        setVisible(true);
    }

    // 🔁 متد اصلی نمایش لیست سفارش‌ها
    private void refreshOrderList() {
        Order[] orders = om.SelectAll();     // همه سفارش‌ها
        Address[] addresses = am.SelectAll(); // همه آدرس‌ها
        Customer[] customers = cm.SelectAll(); // همه مشتری‌ها

        StringBuilder sb = new StringBuilder();

        if (orders.length == 0) {
            sb.append("🥺 No orders found.");
        } else {
            for (int i = 0; i < orders.length; i++) {
                Order o = orders[i];

                if (o != null) {
                    // پیدا کردن مشتری بر اساس customerId
                    Customer customer = null;
                    for (int j = 0; j < customers.length; j++) {
                        if (customers[j] != null && customers[j].getId() == o.getCustomerId()) {
                            customer = customers[j];
                            break;
                        }
                    }

                    // پیدا کردن آدرس بر اساس addressId
                    Address address = null;
                    for (int j = 0; j < addresses.length; j++) {
                        if (addresses[j] != null && addresses[j].getId() == o.getAddressId()) {
                            address = addresses[j];
                            break;
                        }
                    }

                    // 📝 نوشتن اطلاعات سفارش
                    sb.append(i + 1).append(". ")
                            .append("🆔 Order ID: ").append(o.getOrderId()).append("\n");

                    if (customer != null) {
                        sb.append("👤 Customer: ").append(customer.getName())
                                .append(" | 📧 ").append(customer.getEmail())
                                .append(" | 📱 ").append(customer.getPhone()).append("\n");
                    } else {
                        sb.append("👤 Customer ID: ").append(o.getCustomerId()).append(" (Not Found)\n");
                    }

                    if (address != null) {
                        sb.append("🏠 Address: ").append(address.getCity()).append(", ").append(address.getStreet()).append("\n")
                                .append("📬 Postal Code: ").append(address.getPostalCode()).append("\n")
                                .append("📝 Details: ").append(address.getDetails()).append("\n");
                    } else {
                        sb.append("🏠 Address ID: ").append(o.getAddressId()).append(" (Not Found)\n");
                    }

                    // 💰 قیمت و باقی اطلاعات
                    String formattedPrice = formatPrice(o.getTotalAmount());

                    sb.append("💰 Total: ").append(formattedPrice).append(" Toman\n")
                            .append("🎟️ Discount Code: ").append(o.getDiscountCode()).append("\n")
                            .append("🛒 Items: ").append(o.getCartItems()).append("\n")
                            .append("🕓 Date: ").append(o.getOrderDate()).append("\n")
                            .append("--------------------------------------------------\n");
                }
            }
        }

        // نمایش داخل فرم
        txtOrderList.setText(sb.toString());
    }

    // 🎯 فرمت قشنگ برای پول
    private String formatPrice(double price) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        return nf.format(price);
    }

    // ⤴️ دکمه ریفرش
    private class RefreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshOrderList();
        }
    }

    // ⬅️ دکمه برگشت
    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose(); // بستن فرم
            if (parent != null) parent.setVisible(true); // برگشت به فرم قبل
        }
    }

    // 🔰 اجرای مستقل فرم
    public static void main(String[] args) {
        new frmOrder(null);
    }
}
