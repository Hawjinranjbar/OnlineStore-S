package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.ProductManager;
import common.Product;

public class frmProduct extends JFrame {
    private JTextField txtId, txtName, txtBrand, txtDescription, txtPrice, txtStock, txtCategory, txtSkinType, txtIsOrganic, txtImageUrl;
    private JButton btnInsert, btnUpdate, btnDelete;
    private JTextArea txtList;
    private ProductManager pm;

    public frmProduct() {
        setTitle("👑 Admin Panel - Manage Products");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        pm = new ProductManager();

        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        inputPanel.setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        txtId = new JTextField();
        txtName = new JTextField();
        txtBrand = new JTextField();
        txtDescription = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();
        txtCategory = new JTextField();
        txtSkinType = new JTextField();
        txtIsOrganic = new JTextField();
        txtImageUrl = new JTextField();

        JTextField[] fields = {txtId, txtName, txtBrand, txtDescription, txtPrice, txtStock, txtCategory, txtSkinType, txtIsOrganic, txtImageUrl};
        for (JTextField field : fields) {
            field.setFont(font);
        }

        inputPanel.add(new JLabel("Product ID:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(txtName);
        inputPanel.add(new JLabel("Brand:")); inputPanel.add(txtBrand);
        inputPanel.add(new JLabel("Description:")); inputPanel.add(txtDescription);
        inputPanel.add(new JLabel("Price:")); inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("Stock:")); inputPanel.add(txtStock);
        inputPanel.add(new JLabel("Category:")); inputPanel.add(txtCategory);
        inputPanel.add(new JLabel("Skin Type:")); inputPanel.add(txtSkinType);
        inputPanel.add(new JLabel("Is Organic (true/false):")); inputPanel.add(txtIsOrganic);
        inputPanel.add(new JLabel("Image URL:")); inputPanel.add(txtImageUrl);

        btnInsert = new JButton("➕ Insert");
        btnUpdate = new JButton("✏️ Update");
        btnDelete = new JButton("❌ Delete");

        btnInsert.setBackground(new Color(204, 255, 204));
        btnUpdate.setBackground(new Color(255, 255, 153));
        btnDelete.setBackground(new Color(255, 204, 204));

        btnInsert.setFont(font);
        btnUpdate.setFont(font);
        btnDelete.setFont(font);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        txtList = new JTextArea();
        txtList.setFont(font);
        txtList.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📚 Product List"));
        scrollPane.setPreferredSize(new Dimension(850, 250));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

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

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = JOptionPane.showInputDialog("Enter row number to update:");
                    if (input == null || input.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frmProduct.this, "❌ Row number is required!");
                        return;
                    }

                    int row = Integer.parseInt(input);

                    // چک کنیم فرم کامل پر شده باشه
                    if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtBrand.getText().isEmpty()
                            || txtDescription.getText().isEmpty() || txtPrice.getText().isEmpty()
                            || txtStock.getText().isEmpty() || txtCategory.getText().isEmpty()
                            || txtSkinType.getText().isEmpty() || txtIsOrganic.getText().isEmpty()
                            || txtImageUrl.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(frmProduct.this, "❌ Please fill all fields before updating!");
                        return;
                    }

                    Product p = getProductFromInput();
                    pm.Update(p, row);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "✅ Product updated successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Invalid row number! Please enter a number.");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Row number out of range!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Error updating product: " + ex.getMessage());
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to delete:"));
                    pm.Delete(row);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Product Deleted!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Error deleting product.");
                }
            }
        });

        refreshList();
        setVisible(true);
    }

    private Product getProductFromInput() {
        return new Product(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtBrand.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtStock.getText()),
                txtCategory.getText(),
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
                sb.append(i).append(". ")
                        .append(p.getName())
                        .append(" | 💰 ").append(p.getPrice())
                        .append(" | 🛒 Stock: ").append(p.getStock())
                        .append(" | 🔖 Category: ").append(p.getCategory())
                        .append("\n\n");
            }
        }
        txtList.setText(sb.toString());
    }

    public static void main(String[] args) {
        new frmProduct();
    }
}
