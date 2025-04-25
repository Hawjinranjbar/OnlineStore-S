import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import common.Product;
import managers.ProductManager;

public class frmProduct extends JFrame {
    private JTextField txtId, txtName, txtPrice, txtStock;
    private JTextArea txtList;
    private JButton btnInsert, btnUpdate, btnDelete;
    private ProductManager pm;

    public frmProduct() {
        setTitle("🛒 Product Management");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        pm = new ProductManager();

        // پنل ورودی
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        inputPanel.setBackground(Color.WHITE);

        txtId = new JTextField();
        txtName = new JTextField();
        txtPrice = new JTextField();
        txtStock = new JTextField();

        inputPanel.add(new JLabel("Product ID:"));
        inputPanel.add(txtId);
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(txtName);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("Stock:"));
        inputPanel.add(txtStock);

        // دکمه‌ها
        btnInsert = new JButton("➕ Insert Product");
        btnUpdate = new JButton("✏️ Update Product");
        btnDelete = new JButton("❌ Delete Product");

        btnInsert.setBackground(new Color(0xD4EDDA));
        btnUpdate.setBackground(new Color(0xFFF3CD));
        btnDelete.setBackground(new Color(0xF8D7DA));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        // لیست محصولات
        txtList = new JTextArea(15, 60);
        txtList.setEditable(false);
        txtList.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(txtList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Product List"));
        scrollPane.setPreferredSize(new Dimension(780, 300));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // اکشن‌ها
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Product p = getProductFromInput();
                    pm.Insert(p);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "✅ Product inserted.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Insertion error.");
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = Integer.parseInt(JOptionPane.showInputDialog("Row number to update:"));
                    Product p = getProductFromInput();
                    pm.Update(p, row);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "✅ Product updated.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Update error.");
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = Integer.parseInt(JOptionPane.showInputDialog("Row number to delete:"));
                    pm.Delete(row);
                    refreshList();
                    JOptionPane.showMessageDialog(frmProduct.this, "✅ Product deleted.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmProduct.this, "❌ Deletion error.");
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
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtStock.getText())
        );
    }

    private void refreshList() {
        Product[] products = pm.SelectAll();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            s.append(i).append(". ")
                    .append(p.getName())
                    .append(" | 💰 ").append(p.getPrice())
                    .append(" | 📦 ").append(p.getStock())
                    .append("\n\n");
        }
        txtList.setText(s.toString());
    }

    public static void main(String[] args) {
        new frmProduct();
    }
}
