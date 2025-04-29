package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.CustomerManager;
import common.Customer;

public class frmManageCustomers extends JFrame {
    private JTextArea txtCustomerList;
    private JButton btnRefresh, btnUpdate, btnDelete;
    private CustomerManager cm;

    public frmManageCustomers() {
        setTitle("👑 Admin Panel - Manage Customers");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 228, 240));

        cm = new CustomerManager();
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        txtCustomerList = new JTextArea();
        txtCustomerList.setEditable(false);
        txtCustomerList.setFont(font);

        JScrollPane scrollPane = new JScrollPane(txtCustomerList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Customer List"));

        btnRefresh = new JButton("🔄 Refresh");
        btnUpdate = new JButton("✏️ Update Customer");
        btnDelete = new JButton("❌ Delete Customer");

        btnRefresh.setBackground(new Color(204, 229, 255));
        btnUpdate.setBackground(new Color(255, 255, 153));
        btnDelete.setBackground(new Color(255, 204, 204));

        btnRefresh.setFont(font);
        btnUpdate.setFont(font);
        btnDelete.setFont(font);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 228, 240));
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> refreshList());

        btnUpdate.addActionListener(e -> {
            try {
                int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to update:"));
                String newName = JOptionPane.showInputDialog("New name:");
                String newPhone = JOptionPane.showInputDialog("New phone:");
                String newEmail = JOptionPane.showInputDialog("New email:");
                String newPassword = JOptionPane.showInputDialog("New password:");

                Customer oldCustomer = cm.SelectAll()[row];
                Customer updatedCustomer = new Customer(
                        oldCustomer.getId(),
                        newName,
                        newPhone,
                        newEmail,
                        newPassword
                );

                cm.Update(updatedCustomer, row);
                JOptionPane.showMessageDialog(this, "✅ Customer Updated!");
                refreshList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error updating customer.");
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to delete:"));
                cm.Delete(row);
                JOptionPane.showMessageDialog(this, "✅ Customer Deleted!");
                refreshList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error deleting customer.");
            }
        });

        refreshList();
        setVisible(true);
    }

    private void refreshList() {
        Customer[] customers = cm.SelectAll();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customers.length; i++) {
            Customer c = customers[i];
            if (c != null) {
                sb.append(i).append(". ")
                        .append("👤 ").append(c.getName())
                        .append(" | 📧 ").append(c.getEmail())
                        .append(" | 📱 ").append(c.getPhone())
                        .append("\n\n");
            }
        }
        txtCustomerList.setText(sb.toString());
    }

    public static void main(String[] args) {
        new frmManageCustomers();
    }
}
