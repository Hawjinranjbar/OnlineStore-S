package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.DiscountManager;
import common.Discount;

public class frmDiscount extends JFrame {
    private JTextField txtId, txtCode, txtPercent, txtCategory, txtMinAmount, txtIsActive;
    private JButton btnAdd;
    private JTextArea txtList;
    private DiscountManager dm;

    public frmDiscount() {
        setTitle("🎟️ Admin Panel - Manage Discounts");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245)); // پس زمینه‌ی صورتی روشن

        dm = new DiscountManager();

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("🎯 Discount Details"));
        inputPanel.setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        txtId = new JTextField();
        txtCode = new JTextField();
        txtPercent = new JTextField();
        txtCategory = new JTextField();
        txtMinAmount = new JTextField();
        txtIsActive = new JTextField();

        JTextField[] fields = {txtId, txtCode, txtPercent, txtCategory, txtMinAmount, txtIsActive};
        for (JTextField field : fields) {
            field.setFont(font);
        }

        inputPanel.add(new JLabel("🆔 Discount ID:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("🏷️ Discount Code:")); inputPanel.add(txtCode);
        inputPanel.add(new JLabel("🔢 Discount Percent:")); inputPanel.add(txtPercent);
        inputPanel.add(new JLabel("🛒 Applicable Category:")); inputPanel.add(txtCategory);
        inputPanel.add(new JLabel("💰 Minimum Purchase:")); inputPanel.add(txtMinAmount);
        inputPanel.add(new JLabel("✅ Is Active (true/false):")); inputPanel.add(txtIsActive);

        btnAdd = new JButton("➕ Add Discount");
        btnAdd.setBackground(new Color(204, 255, 204)); // سبز روشن
        btnAdd.setFont(font);

        txtList = new JTextArea();
        txtList.setEditable(false);
        txtList.setFont(font);
        txtList.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(txtList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📜 Discount List"));
        scrollPane.setPreferredSize(new Dimension(700, 250));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnAdd);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Discount d = getDiscountFromInput();
                    dm.Insert(d);
                    refreshList();
                    JOptionPane.showMessageDialog(frmDiscount.this, "✅ Discount added!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmDiscount.this, "❌ Error adding discount: " + ex.getMessage());
                }
            }
        });

        refreshList();
        setVisible(true);
    }

    private Discount getDiscountFromInput() {
        return new Discount(
                Integer.parseInt(txtId.getText()),
                txtCode.getText(),
                Integer.parseInt(txtPercent.getText()),
                txtCategory.getText(),
                Double.parseDouble(txtMinAmount.getText()),
                Boolean.parseBoolean(txtIsActive.getText())
        );
    }

    private void refreshList() {
        Discount[] discounts = dm.SelectAll();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < discounts.length; i++) {
            Discount d = discounts[i];
            if (d != null) {
                sb.append(i).append(". ")
                        .append(d.getDiscountCode())
                        .append(" | 🔥 ").append(d.getDiscountPercent()).append("%")
                        .append(" | 🛍️ ").append(d.getApplicableCategory())
                        .append(" | 💵 Min: ").append(d.getMinPurchaseAmount())
                        .append(" | ✅ Active: ").append(d.isActive())
                        .append("\n");
            }
        }
        txtList.setText(sb.toString());
    }

    public static void main(String[] args) {
        new frmDiscount();
    }
}
