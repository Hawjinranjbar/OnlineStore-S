package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.ProductManager;
import common.Product;

public class frmProduct extends JFrame {
    private JTextField txtId, txtName, txtBrand, txtDescription, txtPrice, txtStock, txtSkinType, txtIsOrganic, txtImageUrl;
    private JComboBox<String> cmbCategory;
    private JButton btnInsert, btnUpdate, btnDelete, btnBack;
    private JTextArea txtList;
    private ProductManager pm;
    private JFrame parent;

    public frmProduct(JFrame parent) {
        this.parent = parent;

        setTitle("👑 Admin Panel - Manage Products");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        pm = new ProductManager();

        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("🛠️ Product Details"));
        inputPanel.setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        txtId = new JTextField();
        txtName = new JTextField();
        txtBrand = new JTextField();
        txtDescription = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();
        cmbCategory = new JComboBox<>(new String[]{"Makeup 💄", "Skincare 🌿", "Haircare 💇‍♀️", "Bodycare 🧴"});
        txtSkinType = new JTextField();
        txtIsOrganic = new JTextField();
        txtImageUrl = new JTextField();

        JComponent[] fields = {txtId, txtName, txtBrand, txtDescription, txtPrice, txtStock, cmbCategory, txtSkinType, txtIsOrganic, txtImageUrl};
        for (int i = 0; i < fields.length; i++) fields[i].setFont(font);

        inputPanel.add(new JLabel("🆔 Product ID:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("📝 Name:")); inputPanel.add(txtName);
        inputPanel.add(new JLabel("🏷️ Brand:")); inputPanel.add(txtBrand);
        inputPanel.add(new JLabel("🗒️ Description:")); inputPanel.add(txtDescription);
        inputPanel.add(new JLabel("💰 Price:")); inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("📦 Stock:")); inputPanel.add(txtStock);
        inputPanel.add(new JLabel("📂 Category:")); inputPanel.add(cmbCategory);
        inputPanel.add(new JLabel("🧴 Skin Type:")); inputPanel.add(txtSkinType);
        inputPanel.add(new JLabel("🍃 Is Organic (true/false):")); inputPanel.add(txtIsOrganic);
        inputPanel.add(new JLabel("🖼️ Image URL:")); inputPanel.add(txtImageUrl);

        btnInsert = new JButton("➕ Insert");
        btnUpdate = new JButton("✏️ Update");
        btnDelete = new JButton("❌ Delete");
        btnBack = new JButton("🔙 Back to Inventory");

        // Button styles
        btnInsert.setBackground(new Color(204, 255, 204));
        btnUpdate.setBackground(new Color(255, 255, 153));
        btnDelete.setBackground(new Color(255, 204, 204));
        btnBack.setBackground(new Color(204, 229, 255));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);

        txtList = new JTextArea();
        txtList.setFont(font);
        txtList.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📚 Product List"));
        scrollPane.setPreferredSize(new Dimension(850, 250));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Action listener for Insert button
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Product p = getProductFromInput();
                    pm.Insert(p);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "✅ Product Inserted!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Error inserting product.");
                }
            }
        });

        // Action listener for Update button
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to update:"));
                    Product p = getProductFromInput();
                    pm.Update(p, row);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "✅ Product updated!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Error updating product.");
                }
            }
        });

        // Action listener for Delete button
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to delete:"));
                    pm.Delete(row);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "✅ Product deleted!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Error deleting product.");
                }
            }
        });

        // Action listener for Back button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                if (parent != null) parent.setVisible(true); // Show the previous form (Inventory form)
            }
        });

        refreshList(); // Initialize the product list
        setVisible(true);
    }

    private Product getProductFromInput() {
        String category = cmbCategory.getSelectedItem().toString().split(" ")[0];
        return new Product(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtBrand.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtStock.getText()),
                category,
                txtSkinType.getText(),
                Boolean.parseBoolean(txtIsOrganic.getText()),
                txtImageUrl.getText()
        );
    }

    private void refreshList() {
        Product[] products = pm.SelectAll();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            if (p != null) {
                sb.append(i).append(". ").append(p.getName())
                        .append(" | 💰 ").append(p.getPrice())
                        .append(" | 📦 ").append(p.getStock())
                        .append(" | 🗂 ").append(p.getCategory()).append("\n\n");
            }
        }
        txtList.setText(sb.toString());
    }

    public static void main(String[] args) {
        new frmProduct(null);
    }
}
