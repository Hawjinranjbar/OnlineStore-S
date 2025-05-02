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
    private JTextArea txtOrderList;
    private JButton btnRefresh, btnBack;
    private OrderManager om;
    private AddressManager am;
    private CustomerManager cm;
    private JFrame parent; // 🔙 فرم قبل

    public frmOrder(JFrame parent) {
        this.parent = parent;

        setTitle("📦 Orders List");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        om = new OrderManager();
        am = new AddressManager();
        cm = new CustomerManager();

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        txtOrderList = new JTextArea();
        txtOrderList.setEditable(false);
        txtOrderList.setFont(font);
        JScrollPane scrollPane = new JScrollPane(txtOrderList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Orders Overview"));

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

        refreshOrderList();
        setVisible(true);
    }

    private void refreshOrderList() {
        Order[] orders = om.SelectAll();
        Address[] addresses = am.SelectAll();
        Customer[] customers = cm.SelectAll();

        StringBuilder sb = new StringBuilder();

        if (orders.length == 0) {
            sb.append("🥺 No orders found.");
        } else {
            for (int i = 0; i < orders.length; i++) {
                Order o = orders[i];
                if (o != null) {
                    Customer customer = null;
                    for (int j = 0; j < customers.length; j++) {
                        if (customers[j] != null && customers[j].getId() == o.getCustomerId()) {
                            customer = customers[j];
                            break;
                        }
                    }

                    Address address = null;
                    for (int j = 0; j < addresses.length; j++) {
                        if (addresses[j] != null && addresses[j].getId() == o.getAddressId()) {
                            address = addresses[j];
                            break;
                        }
                    }

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
                        sb.append("🏠 Address: ")
                                .append(address.getCity()).append(", ")
                                .append(address.getStreet()).append("\n")
                                .append("📬 Postal Code: ").append(address.getPostalCode()).append("\n")
                                .append("📝 Details: ").append(address.getDetails()).append("\n");
                    } else {
                        sb.append("🏠 Address ID: ").append(o.getAddressId()).append(" (Not Found)\n");
                    }

                    String formattedPrice = formatPrice(o.getTotalAmount());

                    sb.append("💰 Total: ").append(formattedPrice).append(" Toman\n")
                            .append("🎟️ Discount Code: ").append(o.getDiscountCode()).append("\n")
                            .append("🛒 Items: ").append(o.getCartItems()).append("\n")
                            .append("🕓 Date: ").append(o.getOrderDate()).append("\n")
                            .append("--------------------------------------------------\n");
                }
            }
        }

        txtOrderList.setText(sb.toString());
    }

    private String formatPrice(double price) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        return nf.format(price);
    }

    private class RefreshButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshOrderList();
        }
    }

    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            if (parent != null) {
                parent.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        new frmOrder(null); // تست مستقل
    }
}
