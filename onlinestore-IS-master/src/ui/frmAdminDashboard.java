package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class frmAdminDashboard extends JFrame {
    public frmAdminDashboard() {
        setTitle("📊 Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 240, 245));
        setLayout(new BorderLayout(10, 10));

        Font font = new Font("Segoe UI", Font.BOLD, 16);

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        gridPanel.setBackground(new Color(255, 240, 245));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton btnOrders = createButton("📦 View Orders", font);
        JButton btnDiscounts = createButton("🎟️ Manage Discounts", font);
        JButton btnInventory = createButton("📦 Inventory", font);
        JButton btnBack = createButton("🔙 Back", font);

        gridPanel.add(btnOrders);
        gridPanel.add(btnDiscounts);
        gridPanel.add(btnInventory);
        gridPanel.add(btnBack);

        add(gridPanel, BorderLayout.CENTER);

        btnOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new frmOrder(frmAdminDashboard.this);
                setVisible(false);
            }
        });

        btnDiscounts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new frmDiscount(frmAdminDashboard.this);
                setVisible(false);
            }
        });

        btnInventory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new frmInventory(); // بدون parent چون نیازی نداره برگرده
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private JButton createButton(String text, Font font) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(255, 228, 240));
        return btn;
    }

    public static void main(String[] args) {
        new frmAdminDashboard();
    }
}
