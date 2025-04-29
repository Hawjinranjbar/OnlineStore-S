package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.CartManager;
import managers.ProductManager;
import managers.DiscountManager;
import common.Cart;
import common.Product;
import common.Discount;

public class frmCart extends JFrame {
    private JTextArea txtCartList;
    private JLabel lblTotalPrice;
    private JLabel lblDiscountInfo;
    private JTextField txtDiscountCode;
    private JButton btnApplyDiscount;
    private JButton btnFinalizeOrder;
    private JButton btnGoToLogin;
    private CartManager cartManager;
    private ProductManager productManager;
    private DiscountManager discountManager;
    private Discount appliedDiscount = null;

    public frmCart() {
        setTitle("🛒 Your Shopping Cart");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 15);

        cartManager = new CartManager();
        productManager = new ProductManager();
        discountManager = new DiscountManager();

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

        btnGoToLogin = new JButton("🔑 Go to Login");
        btnGoToLogin.setFont(font);
        btnGoToLogin.setBackground(new Color(255, 204, 229));
        btnGoToLogin.setVisible(false); // اول مخفیه

        JPanel discountPanel = new JPanel();
        discountPanel.setBackground(new Color(255, 240, 245));
        discountPanel.add(new JLabel("Enter Discount Code:"));
        discountPanel.add(txtDiscountCode);
        discountPanel.add(btnApplyDiscount);

        JPanel bottomPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.add(lblDiscountInfo);
        bottomPanel.add(discountPanel);
        bottomPanel.add(lblTotalPrice);
        bottomPanel.add(btnFinalizeOrder);
        bottomPanel.add(btnGoToLogin);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnApplyDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyDiscountManually();
            }
        });

        btnFinalizeOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                finalizeOrder();
            }
        });

        btnGoToLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new frmLoginCustomer();
                dispose(); // این فرم رو ببند
            }
        });

        loadCart();
        setVisible(true);
    }

    private void loadCart() {
        Cart[] carts = cartManager.SelectAll();
        Product[] products = productManager.SelectAll();

        double totalPrice = 0;
        StringBuilder sb = new StringBuilder();

        for (Cart c : carts) {
            if (c != null) {
                for (Product p : products) {
                    if (p != null && p.getId() == c.getProductId()) {
                        double itemPrice = p.getPrice() * c.getQuantity();
                        totalPrice += itemPrice;
                        sb.append("🌸 ").append(p.getName())
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
            lblDiscountInfo.setText("🎟️ Discount Applied: " + appliedDiscount.getDiscountCode() + " (" + appliedDiscount.getDiscountPercent() + "% OFF)");
        } else {
            lblDiscountInfo.setText("🎟️ Discount: None");
        }

        lblTotalPrice.setText("Total: " + formatPrice(totalPrice) + " Toman");
        txtCartList.setText(sb.toString());
    }

    private void applyDiscountManually() {
        String code = txtDiscountCode.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a discount code.");
            return;
        }

        Discount[] discounts = discountManager.SelectAll();
        boolean found = false;

        for (Discount d : discounts) {
            if (d != null && d.getDiscountCode().equalsIgnoreCase(code) && d.isActive()) {
                appliedDiscount = d;
                found = true;
                break;
            }
        }

        if (found) {
            JOptionPane.showMessageDialog(this, "✅ Discount applied successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid or inactive discount code.");
        }

        loadCart(); // رفرش کن
    }

    private void finalizeOrder() {
        if (frmLoginCustomer.loggedInCustomer == null) {
            JOptionPane.showMessageDialog(this, "❌ You must login first!");
            btnGoToLogin.setVisible(true); // دکمه لاگین رو نشون بده
            return;
        }

        JOptionPane.showMessageDialog(this, "✅ Order finalized successfully!\nThanks for shopping with us 🛍️");

        // اینجا میتونیم سبد خرید رو خالی کنیم یا سفارش رو ذخیره کنیم بعداً
    }

    private String formatPrice(double price) {
        return String.format("%,.0f", price);
    }

    public static void main(String[] args) {
        new frmCart();
    }
}

