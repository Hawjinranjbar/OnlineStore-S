package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.AddressManager;
import common.Address;

public class frmAddAddress extends JFrame {
    // فیلدهای فرم
    private JTextField txtId, txtCity, txtStreet, txtPostalCode, txtDetails;
    private JButton btnSave, btnReturnToCart, btnBackToMenu;
    private AddressManager am;
    private JFrame parent;

    // سازنده فرم
    public frmAddAddress(JFrame parent) {
        this.parent = parent;

        // تنظیمات کلی فرم
        setTitle("🏡 Add New Address");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        am = new AddressManager(); // ارتباط با فایل address.txt
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        // پنل ورودی اطلاعات آدرس
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 240, 245));
        inputPanel.setBorder(BorderFactory.createTitledBorder("🏡 Enter Address Info"));

        // ساخت فیلدها
        txtId = new JTextField();
        txtCity = new JTextField();
        txtStreet = new JTextField();
        txtPostalCode = new JTextField();
        txtDetails = new JTextField();

        JTextField[] fields = {txtId, txtCity, txtStreet, txtPostalCode, txtDetails};
        for (int i = 0; i < fields.length; i++) {
            fields[i].setFont(font);
        }

        // اضافه کردن لیبل و فیلد به پنل
        inputPanel.add(new JLabel("🆔 ID:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("🏙️ City:")); inputPanel.add(txtCity);
        inputPanel.add(new JLabel("🛣️ Street:")); inputPanel.add(txtStreet);
        inputPanel.add(new JLabel("📬 Postal Code:")); inputPanel.add(txtPostalCode);
        inputPanel.add(new JLabel("📝 Details:")); inputPanel.add(txtDetails);

        // دکمه ثبت آدرس
        btnSave = new JButton("💾 Save Address");
        btnSave.setBackground(new Color(204, 255, 204));
        btnSave.setFont(font);
        btnSave.addActionListener(new SaveAddressListener());

        // دکمه برگشت به سبد خرید (مخفی تا ذخیره نکنیم)
        btnReturnToCart = new JButton("↩️ Return to Cart");
        btnReturnToCart.setBackground(new Color(255, 229, 204));
        btnReturnToCart.setFont(font);
        btnReturnToCart.setVisible(false);
        btnReturnToCart.addActionListener(new ReturnToCartListener());

        // دکمه برگشت به فرم قبلی
        btnBackToMenu = new JButton("🔙 Back");
        btnBackToMenu.setBackground(new Color(204, 229, 255));
        btnBackToMenu.setFont(font);
        btnBackToMenu.addActionListener(new BackButtonListener());

        // چیدن دکمه‌ها پایین فرم
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnReturnToCart);
        buttonPanel.add(btnBackToMenu);

        // اضافه کردن پنل‌ها به فرم
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true); // نمایش فرم
    }

    // عملکرد دکمه Save
    private class SaveAddressListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                // ساخت آدرس از روی فیلدها
                Address address = new Address(
                        Integer.parseInt(txtId.getText()),
                        txtCity.getText(),
                        txtStreet.getText(),
                        txtPostalCode.getText(),
                        txtDetails.getText()
                );

                am.Insert(address); // ذخیره در فایل
                JOptionPane.showMessageDialog(frmAddAddress.this, "✅ Address saved successfully!");
                clearFields(); // پاک کردن فیلدها
                btnReturnToCart.setVisible(true); // فعال کردن برگشت به سبد خرید
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmAddAddress.this, "❌ Error saving address: " + ex.getMessage());
            }
        }
    }

    // عملکرد دکمه برگشت به سبد خرید
    private class ReturnToCartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new frmCart(frmAddAddress.this); // باز کردن سبد خرید و مخفی کردن این فرم
            setVisible(false);
        }
    }

    // عملکرد دکمه برگشت
    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose(); // بستن فرم
            if (parent != null) {
                parent.setVisible(true); // نمایش فرم قبلی (اگه داده شده)
            }
        }
    }

    // پاک‌سازی همه فیلدهای فرم
    private void clearFields() {
        txtId.setText("");
        txtCity.setText("");
        txtStreet.setText("");
        txtPostalCode.setText("");
        txtDetails.setText("");
    }

    // اجرای مستقل برای تست فرم
    public static void main(String[] args) {
        new frmAddAddress(null);
    }
}
