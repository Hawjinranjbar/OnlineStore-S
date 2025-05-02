package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import common.Inventory;
import managers.InventoryManager;

public class frmInventory extends JFrame {
    private JTextField txtProductId, txtProductName, txtQuantity, txtSearch;
    private JTextArea txtList;
    private JButton btnAdd, btnRemove, btnSearch, btnRefresh, btnGoToProducts, btnBack;
    private InventoryManager im;

    public frmInventory() {
        setTitle("📦 Inventory Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        im = new InventoryManager();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 240, 245));
        inputPanel.setBorder(BorderFactory.createTitledBorder("📥 Product Info"));

        txtProductId = new JTextField();
        txtProductName = new JTextField();
        txtQuantity = new JTextField();

        inputPanel.add(new JLabel("🆔 Product ID:")); inputPanel.add(txtProductId);
        inputPanel.add(new JLabel("🛙 Product Name:")); inputPanel.add(txtProductName);
        inputPanel.add(new JLabel("🔢 Quantity:")); inputPanel.add(txtQuantity);

        btnAdd = new JButton("➕ Add Stock");
        btnRemove = new JButton("➖ Remove Stock");

        inputPanel.add(btnAdd);
        inputPanel.add(btnRemove);

        txtList = new JTextArea();
        txtList.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        txtList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Inventory List"));

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(255, 240, 245));

        txtSearch = new JTextField(20);
        btnSearch = new JButton("🔍 Search");
        btnRefresh = new JButton("🔄 Refresh");
        btnGoToProducts = new JButton("🛙 Manage Products");
        btnBack = new JButton("⬅️ Back");

        bottomPanel.add(new JLabel("Search by Name:"));
        bottomPanel.add(txtSearch);
        bottomPanel.add(btnSearch);
        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnGoToProducts);
        bottomPanel.add(btnBack);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStock();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeStock();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshList();
            }
        });

        btnGoToProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // برای Safe Back
                new frmProduct(frmInventory.this); // به عنوان parent فرستاده میشه
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        refreshList();
        setVisible(true);
    }

    private void addStock() {
        try {
            int id = Integer.parseInt(txtProductId.getText().trim());
            String name = txtProductName.getText().trim();
            int qty = Integer.parseInt(txtQuantity.getText().trim());

            Inventory inv = new Inventory(id, name, qty, true);
            im.Insert(inv);
            JOptionPane.showMessageDialog(this, "✅ Stock added!");
            refreshList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Invalid input.");
        }
    }

    private void removeStock() {
        try {
            int id = Integer.parseInt(txtProductId.getText().trim());
            String name = txtProductName.getText().trim();
            int qty = Integer.parseInt(txtQuantity.getText().trim());

            Inventory inv = new Inventory(id, name, qty, false);
            im.Insert(inv);
            JOptionPane.showMessageDialog(this, "✅ Stock removed!");
            refreshList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Invalid input.");
        }
    }

    private void search() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        Inventory[] list = im.SelectAll();
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (int i = 0; i < list.length; i++) {
            Inventory inv = list[i];
            if (inv != null && inv.getProductName().toLowerCase().contains(keyword)) {
                sb.append(inv.toString()).append("\n");
                count++;
            }
        }

        if (count == 0) {
            sb.append("🥺 No matches found.");
        }

        txtList.setText(sb.toString());
    }

    private void refreshList() {
        Inventory[] list = im.SelectAll();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            Inventory inv = list[i];
            if (inv != null) {
                sb.append(inv.toString()).append("\n");
            }
        }

        if (sb.length() == 0) {
            txtList.setText("📦 Inventory is empty.");
        } else {
            txtList.setText(sb.toString());
        }
    }

    public static void main(String[] args) {
        new frmInventory();
    }
}
