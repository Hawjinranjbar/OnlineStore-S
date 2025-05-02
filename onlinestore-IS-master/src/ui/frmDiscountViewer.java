





package ui;

import javax.swing.*;
import java.awt.*;
import common.Discount;
import managers.DiscountManager;

public class frmDiscountViewer extends JFrame {
    private JTextArea txtList;
    private DiscountManager dm;

    public frmDiscountViewer() {
        setTitle("🎟️ Available Discounts");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        dm = new DiscountManager();

        JLabel lblTitle = new JLabel("🎁 Active & Inactive Discounts", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(120, 0, 120));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        txtList = new JTextArea();
        txtList.setEditable(false);
        txtList.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        txtList.setBackground(new Color(255, 250, 252));
        txtList.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(txtList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Discount List"));

        JButton btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnRefresh.setBackground(new Color(204, 255, 229));
        btnRefresh.addActionListener(e -> refreshList());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.add(btnRefresh);

        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshList();
        setVisible(true);
    }

    private void refreshList() {
        Discount[] discounts = dm.SelectAll();
        StringBuilder sb = new StringBuilder();

        if (discounts.length == 0) {
            sb.append("🥺 No discounts available yet!");
        } else {
            for (int i = 0; i < discounts.length; i++) {
                Discount d = discounts[i];
                if (d != null) {
                    sb.append(i + 1).append(". ")
                            .append("🏷️ Code: ").append(d.getDiscountCode())
                            .append(" | 🔢 ").append(d.getDiscountPercent()).append("%")
                            .append(" | ").append(d.isActive() ? "✅ Active" : "🚫 Inactive")
                            .append("\n----------------------------------\n");
                }
            }
        }

        txtList.setText(sb.toString());
    }

    public static void main(String[] args) {
        new frmDiscountViewer();
    }
}




