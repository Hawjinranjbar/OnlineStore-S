package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import common.*;
import managers.*;

public class frmCart extends JFrame {
    private JTextArea txtCartList;
    private JLabel lblTotalPrice;
    private JLabel lblDiscountInfo;
    private JTextField txtDiscountCode;
    private JButton btnApplyDiscount, btnFinalizeOrder, btnDeleteProduct, btnGoToLogin, btnBackToMenu;

    private CartManager cartManager = new CartManager();
    private ProductManager productManager = new ProductManager();
    private DiscountManager discountManager = new DiscountManager();
    private Discount appliedDiscount = null;

    public frmCart() {
        setTitle("🛒 Your Shopping Cart");
        setSize(800, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 15);

        txtCartList = new JTextArea();
        txtCartList.setEditable(false);
        txtCartList.setFont(font);
        JScrollPane scrollPane = new JScrollPane(txtCartList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("🛍️ Items in Cart"));

        lblTotalPrice = new JLabel("Total: 0 Toman", SwingConstants.CENTER);
        lblTotalPrice.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        lblTotalPrice.setForeground(new Color(153, 0, 153));

        lblDiscountInfo = new JLabel("🎟️ Discount: None", SwingConstants.CENTER);
        lblDiscountInfo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        lblDiscountInfo.setForeground(new Color(0, 102, 102));

        txtDiscountCode = new JTextField(15);
        txtDiscountCode.setFont(font);

        btnApplyDiscount = new JButton("🎟️ Apply Discount");
        btnApplyDiscount.setFont(font);
        btnApplyDiscount.setBackground(new Color(204, 229, 255));

        btnFinalizeOrder = new JButton("✅ Finalize Order");
        btnFinalizeOrder.setFont(font);
        btnFinalizeOrder.setBackground(new Color(204, 255, 204));

        btnDeleteProduct = new JButton("🗑️ Delete Product");
        btnDeleteProduct.setFont(font);
        btnDeleteProduct.setBackground(new Color(255, 204, 204));

        btnGoToLogin = new JButton("🔑 Go to Login");
        btnGoToLogin.setFont(font);
        btnGoToLogin.setBackground(new Color(255, 204, 229));
        btnGoToLogin.setVisible(false);

        btnBackToMenu = new JButton("🔙 Back to Menu");
        btnBackToMenu.setFont(font);
        btnBackToMenu.setBackground(new Color(204, 229, 255));

        JPanel discountPanel = new JPanel();
        discountPanel.setBackground(new Color(255, 240, 245));
        discountPanel.add(new JLabel("Enter Discount Code:"));
        discountPanel.add(txtDiscountCode);
        discountPanel.add(btnApplyDiscount);

        JPanel bottomPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.add(lblDiscountInfo);
        bottomPanel.add(discountPanel);
        bottomPanel.add(lblTotalPrice);
        bottomPanel.add(btnFinalizeOrder);
        bottomPanel.add(btnDeleteProduct);
        bottomPanel.add(btnGoToLogin);
        bottomPanel.add(btnBackToMenu);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnApplyDiscount.addActionListener(new ApplyDiscountListener());
        btnFinalizeOrder.addActionListener(new FinalizeOrderListener());
        btnDeleteProduct.addActionListener(new DeleteProductListener());
        btnGoToLogin.addActionListener(new GoToLoginListener());
        btnBackToMenu.addActionListener(new BackButtonListener());

        loadCart();
        setVisible(true);
    }

    private void loadCart() {
        Cart[] carts = cartManager.SelectAll();
        Product[] products = productManager.SelectAll();
        double totalPrice = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < carts.length; i++) {
            Cart c = carts[i];
            if (c != null) {
                for (Product p : products) {
                    if (p != null && p.getId() == c.getProductId()) {
                        double itemPrice = p.getPrice() * c.getQuantity();
                        totalPrice += itemPrice;
                        sb.append(i).append(". 🌸 ").append(p.getName())
                                .append(" (x").append(c.getQuantity()).append(")")
                                .append(" - ").append(formatPrice(itemPrice)).append(" Toman\n");
                        break;
                    }
                }
            }
        }

        if (appliedDiscount != null) {
            double discountAmount = (appliedDiscount.getDiscountPercent() / 100.0) * totalPrice;
            totalPrice -= discountAmount;
            lblDiscountInfo.setText("🎟️ Discount Applied: " + appliedDiscount.getDiscountCode()
                    + " (" + appliedDiscount.getDiscountPercent() + "% OFF)");
        } else {
            lblDiscountInfo.setText("🎟️ Discount: None");
        }

        lblTotalPrice.setText("Total: " + formatPrice(totalPrice) + " Toman");
        txtCartList.setText(sb.toString());
    }

    private class ApplyDiscountListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String code = txtDiscountCode.getText().trim();
            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(frmCart.this, "❌ Please enter a discount code.");
                return;
            }

            Discount[] discounts = discountManager.SelectAll();
            for (Discount d : discounts) {
                if (d != null && d.getDiscountCode().equalsIgnoreCase(code) && d.isActive()) {
                    appliedDiscount = d;
                    JOptionPane.showMessageDialog(frmCart.this, "✅ Discount applied successfully!");
                    loadCart();
                    return;
                }
            }

            JOptionPane.showMessageDialog(frmCart.this, "❌ Invalid or inactive discount code.");
        }
    }

    private class FinalizeOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (frmLoginCustomer.loggedInCustomer == null) {
                JOptionPane.showMessageDialog(frmCart.this, "❌ You must login first!");
                btnGoToLogin.setVisible(true);
                return;
            }

            Cart[] carts = cartManager.SelectAll();
            if (carts.length == 0) {
                JOptionPane.showMessageDialog(frmCart.this, "🛒 Your cart is empty!");
                return;
            }

            int customerId = frmLoginCustomer.loggedInCustomer.getId();
            AddressManager am = new AddressManager();
            Address[] addresses = am.SelectAll();
            Address customerAddress = null;
            for (Address a : addresses) {
                if (a != null && a.getId() == customerId) {
                    customerAddress = a;
                    break;
                }
            }

            if (customerAddress == null) {
                JOptionPane.showMessageDialog(frmCart.this, "❌ No address found for this customer!");
                return;
            }

            double totalAmount = 0;
            StringBuilder itemsText = new StringBuilder();
            Product[] products = productManager.SelectAll();
            for (Cart c : carts) {
                for (Product p : products) {
                    if (p != null && p.getId() == c.getProductId()) {
                        totalAmount += p.getPrice() * c.getQuantity();
                        itemsText.append(c.getQuantity()).append("x").append(p.getName()).append(", ");
                    }
                }
            }

            if (appliedDiscount != null) {
                double discountAmount = (appliedDiscount.getDiscountPercent() / 100.0) * totalAmount;
                totalAmount -= discountAmount;
            }

            int orderId = (int) (System.currentTimeMillis() % 100000);
            String date = LocalDate.now().toString();
            String discountCode = (appliedDiscount != null) ? appliedDiscount.getDiscountCode() : "None";

            Order order = new Order(orderId, customerId, customerAddress.getId(), totalAmount, discountCode, itemsText.toString(), date);
            new OrderManager().Insert(order);
            cartManager.ClearAll();

            JOptionPane.showMessageDialog(frmCart.this, "✅ Order finalized and saved!\n🧾 Order ID: " + orderId);
            new frmOrder();
            dispose();
        }
    }

    private class DeleteProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String input = JOptionPane.showInputDialog(frmCart.this, "Enter row number to delete:");
                if (input != null && !input.isEmpty()) {
                    int row = Integer.parseInt(input.trim());
                    cartManager.Delete(row);
                    JOptionPane.showMessageDialog(frmCart.this, "✅ Product deleted from cart!");
                    loadCart();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmCart.this, "❌ Error deleting product.");
            }
        }
    }

    private class GoToLoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new frmLoginCustomer("cart");
            dispose();
        }
    }

    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            new frmMain();
        }
    }

    private String formatPrice(double price) {
        return String.format("%,.0f", price);
    }

    public static void main(String[] args) {
        new frmCart();
    }
}
