package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import common.*;
import managers.*;

public class frmCart extends JFrame {
    // ناحیه‌ها و دکمه‌های فرم
    private JTextArea txtCartList;
    private JLabel lblTotalPrice;
    private JLabel lblDiscountInfo;
    private JTextField txtDiscountCode;
    private JButton btnApplyDiscount, btnFinalizeOrder, btnDeleteProduct, btnGoToLogin, btnBackToMenu;

    // لایه مدیریتی فایل‌ها
    private CartManager cartManager = new CartManager();
    private ProductManager productManager = new ProductManager();
    private DiscountManager discountManager = new DiscountManager();
    private Discount appliedDiscount = null;

    private JFrame parent; // فرم قبلی برای برگشت

    public frmCart(JFrame parent) {
        this.parent = parent;

        setTitle("🛒 Your Shopping Cart");
        setSize(800, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 15);

        // تنظیم ناحیه لیست سبد خرید
        txtCartList = new JTextArea();
        txtCartList.setEditable(false);
        txtCartList.setFont(font);
        JScrollPane scrollPane = new JScrollPane(txtCartList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("🛍️ Items in Cart"));

        // مجموع قیمت
        lblTotalPrice = new JLabel("Total: 0 Toman", SwingConstants.CENTER);
        lblTotalPrice.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        lblTotalPrice.setForeground(new Color(153, 0, 153));

        // لیبل کد تخفیف
        lblDiscountInfo = new JLabel("🎟️ Discount: None", SwingConstants.CENTER);
        lblDiscountInfo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        lblDiscountInfo.setForeground(new Color(0, 102, 102));

        // فیلد ورودی کد تخفیف
        txtDiscountCode = new JTextField(15);
        txtDiscountCode.setFont(font);

        // دکمه‌ها
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
        btnGoToLogin.setVisible(false); // فقط وقتی کاربر لاگین نیست نشون داده میشه

        btnBackToMenu = new JButton("💙 Back to Menu");
        btnBackToMenu.setFont(font);
        btnBackToMenu.setBackground(new Color(204, 229, 255));

        // پنل تخفیف
        JPanel discountPanel = new JPanel();
        discountPanel.setBackground(new Color(255, 240, 245));
        discountPanel.add(new JLabel("Enter Discount Code:"));
        discountPanel.add(txtDiscountCode);
        discountPanel.add(btnApplyDiscount);

        // پنل پایینی شامل دکمه‌ها و لیبل‌ها
        JPanel bottomPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.add(lblDiscountInfo);
        bottomPanel.add(discountPanel);
        bottomPanel.add(lblTotalPrice);
        bottomPanel.add(btnFinalizeOrder);
        bottomPanel.add(btnDeleteProduct);
        bottomPanel.add(btnGoToLogin);
        bottomPanel.add(btnBackToMenu);

        // افزودن کامپوننت‌ها به فرم
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // اکشن‌ها
        btnApplyDiscount.addActionListener(e -> applyDiscount());
        btnFinalizeOrder.addActionListener(e -> finalizeOrder());
        btnDeleteProduct.addActionListener(e -> deleteProduct());
        btnGoToLogin.addActionListener(e -> {
            new frmLoginCustomer("cart");
            dispose();
        });
        btnBackToMenu.addActionListener(e -> {
            dispose();
            if (parent != null) parent.setVisible(true);
        });

        loadCart();
        setVisible(true);
    }

    // اعمال تخفیف
    private void applyDiscount() {
        String code = txtDiscountCode.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a discount code.");
            return;
        }

        Discount[] discounts = discountManager.SelectAll();
        for (Discount d : discounts) {
            if (d != null && d.getDiscountCode().equalsIgnoreCase(code) && d.isActive()) {
                appliedDiscount = d;
                JOptionPane.showMessageDialog(this, "✅ Discount applied successfully!");
                loadCart(); // بارگذاری مجدد با تخفیف
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "❌ Invalid or inactive discount code.");
    }

    // نهایی‌سازی خرید
    private void finalizeOrder() {
        if (frmLoginCustomer.loggedInCustomer == null) {
            JOptionPane.showMessageDialog(this, "❌ You must login first!");
            btnGoToLogin.setVisible(true);
            return;
        }

        Cart[] carts = cartManager.SelectAll();
        if (carts.length == 0) {
            JOptionPane.showMessageDialog(this, "🛒 Your cart is empty!");
            return;
        }

        int customerId = frmLoginCustomer.loggedInCustomer.getId();

        // پیدا کردن آدرس مشتری
        Address[] addresses = new AddressManager().SelectAll();
        Address customerAddress = null;
        for (Address a : addresses) {
            if (a != null && a.getId() == customerId) {
                customerAddress = a;
                break;
            }
        }

        if (customerAddress == null) {
            JOptionPane.showMessageDialog(this, "❌ No address found for this customer!");
            return;
        }

        // محاسبه مجموع خرید
        Product[] products = productManager.SelectAll();
        double total = 0;
        StringBuilder items = new StringBuilder();
        for (Cart c : carts) {
            for (Product p : products) {
                if (p != null && p.getId() == c.getProductId()) {
                    total += p.getPrice() * c.getQuantity();
                    items.append(c.getQuantity()).append("x").append(p.getName()).append(", ");
                }
            }
        }

        if (appliedDiscount != null) {
            double discount = (appliedDiscount.getDiscountPercent() / 100.0) * total;
            total -= discount;
        }

        // ساخت و ثبت سفارش
        Order order = new Order(
                (int) (System.currentTimeMillis() % 100000),
                customerId,
                customerAddress.getId(),
                total,
                appliedDiscount != null ? appliedDiscount.getDiscountCode() : "None",
                items.toString(),
                LocalDate.now().toString()
        );

        new OrderManager().Insert(order); // ثبت در فایل order.txt
        cartManager.ClearAll(); // خالی کردن سبد خرید

        JOptionPane.showMessageDialog(this, "✅ Order saved!\n🧾 ID: " + order.getOrderId());

        // ارسال به frmOrder با ارسال parent = this (برای تشخیص مسیر ورود)
        new frmOrder(this);
        setVisible(false);
    }

    // حذف محصول از سبد
    private void deleteProduct() {
        try {
            String input = JOptionPane.showInputDialog(this, "Enter row number to delete:");
            if (input != null && !input.isEmpty()) {
                int row = Integer.parseInt(input.trim());
                cartManager.Delete(row);
                JOptionPane.showMessageDialog(this, "✅ Product deleted from cart!");
                loadCart(); // بروزرسانی نمایش
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error deleting product.");
        }
    }

    // بارگذاری اطلاعات سبد خرید
    private void loadCart() {
        Cart[] carts = cartManager.SelectAll();
        Product[] products = productManager.SelectAll();
        double total = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < carts.length; i++) {
            Cart c = carts[i];
            if (c != null) {
                for (int j = 0; j < products.length; j++) {
                    Product p = products[j];
                    if (p != null && p.getId() == c.getProductId()) {
                        double price = p.getPrice() * c.getQuantity();
                        total += price;
                        sb.append(i).append(". 🌸 ").append(p.getName())
                                .append(" (x").append(c.getQuantity()).append(")")
                                .append(" - ").append(formatPrice(price)).append(" Toman\n");
                        break;
                    }
                }
            }
        }

        // اعمال تخفیف اگر وجود داشت
        if (appliedDiscount != null) {
            double discountAmount = (appliedDiscount.getDiscountPercent() / 100.0) * total;
            total -= discountAmount;
            lblDiscountInfo.setText("🎟️ Applied: " + appliedDiscount.getDiscountCode()
                    + " (" + appliedDiscount.getDiscountPercent() + "% OFF)");
        } else {
            lblDiscountInfo.setText("🎟️ Discount: None");
        }

        lblTotalPrice.setText("Total: " + formatPrice(total) + " Toman");
        txtCartList.setText(sb.toString());
    }

    // فرمت قیمت با کاما
    private String formatPrice(double price) {
        return String.format("%,.0f", price);
    }

    public static void main(String[] args) {
        new frmCart(null);
    }
}
