package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.OrderManager;
import common.Order;

public class frmOrder extends JFrame {
    private JTextArea txtOrderList;
    private JButton btnRefresh;
    private OrderManager om;

    public frmOrder() {
        setTitle("📦 Orders List");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        om = new OrderManager();

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

        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshOrderList();
            }
        });

        refreshOrderList();
        setVisible(true);
    }

    private void refreshOrderList() {
        Order[] orders = om.SelectAll();
        StringBuilder sb = new StringBuilder();

        if (orders.length == 0) {
            sb.append("🥺 No orders found.");
        } else {
            for (int i = 0; i < orders.length; i++) {
                Order o = orders[i];
                if (o != null) {
                    sb.append(i + 1).append(". ")
                            .append("🆔 Order ID: ").append(o.getOrderId()).append("\n")
                            .append("👤 Customer ID: ").append(o.getCustomerId()).append("\n")
                            .append("🏠 Address ID: ").append(o.getAddressId()).append("\n")
                            .append("💰 Total: ").append(o.getTotalAmount()).append(" Toman\n")
                            .append("🎟️ Discount Code: ").append(o.getDiscountCode()).append("\n")
                            .append("🛒 Items: ").append(o.getCartItems()).append("\n")
                            .append("🕓 Date: ").append(o.getOrderDate()).append("\n")
                            .append("----------------------------------------\n");
                }
            }
        }

        txtOrderList.setText(sb.toString());
    }

    public static void main(String[] args) {
        new frmOrder();
    }
}
