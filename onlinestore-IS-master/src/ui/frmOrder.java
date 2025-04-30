package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import managers.OrderManager;
import managers.AddressManager;
import managers.CustomerManager;

import common.Order;
import common.Address;
import common.Customer;

public class frmOrder extends JFrame {
    private JTextArea txtOrderList;
    private JButton btnRefresh;
    private OrderManager om;
    private AddressManager am;
    private CustomerManager cm;

    public frmOrder() {
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

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.add(btnRefresh);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> refreshOrderList());

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
                    // گرفتن اطلاعات مشتری
                    Customer customer = null;
                    for (Customer c : customers) {
                        if (c != null && c.getId() == o.getCustomerId()) {
                            customer = c;
                            break;
                        }
                    }

                    // گرفتن اطلاعات آدرس
                    Address address = null;
                    for (Address a : addresses) {
                        if (a != null && a.getId() == o.getAddressId()) {
                            address = a;
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

                    sb.append("💰 Total: ").append(o.getTotalAmount()).append(" Toman\n")
                            .append("🎟️ Discount Code: ").append(o.getDiscountCode()).append("\n")
                            .append("🛒 Items: ").append(o.getCartItems()).append("\n")
                            .append("🕓 Date: ").append(o.getOrderDate()).append("\n")
                            .append("--------------------------------------------------\n");
                }
            }
        }

        txtOrderList.setText(sb.toString());
    }

    public static void main(String[] args) {
        new frmOrder();
    }
}
