



package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.ProductManager;
import managers.ItemFeaturesManager;
import common.Product;
import common.ItemFeatures;

public class frmProduct extends JFrame {
    private JTextField txtId = new JTextField();
    private JTextField txtName = new JTextField();
    private JTextField txtBrand = new JTextField();
    private JTextField txtDescription = new JTextField();
    private JTextField txtPrice = new JTextField();
    private JTextField txtStock = new JTextField();
    private JTextField txtSkinType = new JTextField();
    private JTextField txtIsOrganic = new JTextField();
    private JTextField txtImageUrl = new JTextField();
    private JComboBox<String> cmbCategory = new JComboBox<>(new String[]{"Makeup 💄", "Skincare 🌿", "Haircare 💇‍♀️", "Bodycare 🧴"});

    private JButton btnInsert = new JButton("➕ Insert");
    private JButton btnUpdate = new JButton("✏️ Update");
    private JButton btnDelete = new JButton("❌ Delete");
    private JButton btnLoadFeatures = new JButton("🔍 Load Features");
    private JButton btnEditFeatures = new JButton("🔧 Edit Item Features");

    private JTextArea txtList = new JTextArea();
    private ProductManager pm = new ProductManager();
    private ItemFeaturesManager ifm = new ItemFeaturesManager();

    public frmProduct() {
        setTitle("👑 Admin Panel - Manage Products");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("🛠️ Product Details"));
        inputPanel.setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);
        JComponent[] fields = {txtId, txtName, txtBrand, txtDescription, txtPrice, txtStock, cmbCategory, txtSkinType, txtIsOrganic, txtImageUrl};
        for (JComponent comp : fields) comp.setFont(font);

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

        JButton[] buttons = {btnInsert, btnUpdate, btnDelete, btnLoadFeatures, btnEditFeatures};
        for (JButton b : buttons) {
            b.setFont(font);
        }

        btnInsert.setBackground(new Color(204, 255, 204));
        btnUpdate.setBackground(new Color(255, 255, 153));
        btnDelete.setBackground(new Color(255, 204, 204));
        btnLoadFeatures.setBackground(new Color(204, 229, 255));
        btnEditFeatures.setBackground(new Color(204, 229, 255));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        for (JButton b : buttons) buttonPanel.add(b);

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
                    new frmShowProducts(p.getCategory());
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Error inserting product.");
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to update:"));
                    Product p = getProductFromInput();
                    pm.Update(p, row);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "✅ Product updated successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Error updating product.");
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

        btnLoadFeatures.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    ItemFeatures feature = ifm.SearchByProductId(id);
                    if (feature != null) {
                        txtBrand.setText(feature.getBrand());
                        txtDescription.setText(feature.getDescription());
                        txtSkinType.setText(feature.getSkinType());
                        txtIsOrganic.setText(String.valueOf(feature.isOrganic()));
                        JOptionPane.showMessageDialog(frmProduct.this, "✅ Features loaded.");
                    } else {
                        JOptionPane.showMessageDialog(frmProduct.this, "❌ No features found for this product.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Invalid ID.");
                }
            }
        });

        btnEditFeatures.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    new frmItemFeatures(id, txtBrand, txtDescription, txtSkinType, txtIsOrganic).setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Enter valid product ID first.");
                }
            }
        });

        refreshList();
        setVisible(true);
    }

    private Product getProductFromInput() {
        String categoryRaw = cmbCategory.getSelectedItem().toString();
        String categoryClean = categoryRaw.split(" ")[0];
        return new Product(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtBrand.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtStock.getText()),
                categoryClean,
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



