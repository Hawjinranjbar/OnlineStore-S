package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.DiscountManager;
import common.Discount;

public class frmDiscount extends JFrame {
    private JTextField txtCode, txtPercent, txtIsActive;
    private JButton btnAdd, btnUpdate, btnDelete, btnBack;
    private JTextArea txtList;
    private DiscountManager dm;

    public frmDiscount() {
        setTitle("🎟️ Admin Panel - Manage Discounts");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        dm = new DiscountManager();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("🎯 Discount Details"));
        inputPanel.setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        txtCode = new JTextField();
        txtPercent = new JTextField();
        txtIsActive = new JTextField();
        txtCode.setFont(font);
        txtPercent.setFont(font);
        txtIsActive.setFont(font);

        inputPanel.add(new JLabel("🏷️ Discount Code:")); inputPanel.add(txtCode);
        inputPanel.add(new JLabel("🔢 Discount Percent:")); inputPanel.add(txtPercent);
        inputPanel.add(new JLabel("✅ Is Active (true/false):")); inputPanel.add(txtIsActive);

        btnAdd = new JButton("➕ Add");
        btnUpdate = new JButton("✏️ Update");
        btnDelete = new JButton("❌ Delete");
        btnBack = new JButton("🔙 Back to Menu");

        JButton[] buttons = {btnAdd, btnUpdate, btnDelete, btnBack};
        for (JButton b : buttons) {
            b.setFont(font);
        }

        btnAdd.setBackground(new Color(204, 255, 204));
        btnUpdate.setBackground(new Color(255, 255, 153));
        btnDelete.setBackground(new Color(255, 204, 204));
        btnBack.setBackground(new Color(204, 229, 255));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack); // 🔙 اضافه شد

        txtList = new JTextArea();
        txtList.setEditable(false);
        txtList.setFont(font);
        JScrollPane scrollPane = new JScrollPane(txtList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📜 Discount List"));
        scrollPane.setPreferredSize(new Dimension(650, 300));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        btnAdd.addActionListener(new AddDiscountListener());
        btnUpdate.addActionListener(new UpdateDiscountListener());
        btnDelete.addActionListener(new DeleteDiscountListener());
        btnBack.addActionListener(new BackButtonListener()); // 🔙 وصل شد

        refreshList();
        setVisible(true);
    }

    private Discount getDiscountFromInput() {
        if (txtCode.getText().trim().isEmpty() ||
                txtPercent.getText().trim().isEmpty() ||
                txtIsActive.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Please fill all fields!");
        }

        int percent = Integer.parseInt(txtPercent.getText());
        if (percent <= 0 || percent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 1 and 100.");
        }

        boolean active = Boolean.parseBoolean(txtIsActive.getText().toLowerCase());

        return new Discount(txtCode.getText(), percent, active);
    }

    private void refreshList() {
        Discount[] discounts = dm.SelectAll();
        StringBuilder sb = new StringBuilder();

        boolean anyDiscount = false;
        for (int i = 0; i < discounts.length; i++) {
            Discount d = discounts[i];
            if (d != null) {
                anyDiscount = true;
                sb.append(i).append(". ")
                        .append("🏷️ ").append(d.getDiscountCode())
                        .append(" | 🔥 ").append(d.getDiscountPercent()).append("%")
                        .append(" | ").append(d.isActive() ? "✅ Active" : "🚫 Inactive")
                        .append("\n");
            }
        }

        if (!anyDiscount) {
            sb.append("🥺 No discounts available yet!");
        }

        txtList.setText(sb.toString());
    }

    private void clearFields() {
        txtCode.setText("");
        txtPercent.setText("");
        txtIsActive.setText("");
    }

    private class AddDiscountListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Discount d = getDiscountFromInput();
                dm.Insert(d);
                refreshList();
                clearFields();
                JOptionPane.showMessageDialog(frmDiscount.this, "✅ Discount added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmDiscount.this, "❌ Error: " + ex.getMessage());
            }
        }
    }

    private class UpdateDiscountListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to update:"));
                Discount d = getDiscountFromInput();
                dm.Update(d, row);
                refreshList();
                clearFields();
                JOptionPane.showMessageDialog(frmDiscount.this, "✏️ Discount updated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmDiscount.this, "❌ Error updating discount.");
            }
        }
    }

    private class DeleteDiscountListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to delete:"));
                dm.Delete(row);
                refreshList();
                JOptionPane.showMessageDialog(frmDiscount.this, "❌ Discount deleted!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmDiscount.this, "❌ Error deleting discount.");
            }
        }
    }

    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            new frmMain(); // برگشت به frmMain
        }
    }

    public static void main(String[] args) {
        new frmDiscount();
    }
}
