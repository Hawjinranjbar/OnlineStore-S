package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import common.Discount;
import managers.DiscountManager;

public class frmDiscountViewer extends JFrame {
    private JTextArea txtList;         // ناحیه‌ای برای نمایش تخفیف‌ها
    private DiscountManager dm;        // برای گرفتن داده‌ها از فایل
    private JFrame parent;             // فرم قبلی (برای برگشت)

    public frmDiscountViewer(JFrame parent) {
        this.parent = parent;

        // تنظیمات کلی فرم
        setTitle("🎟️ Available Discounts");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        dm = new DiscountManager(); // مدیریت فایل تخفیف

        // عنوان بالای صفحه
        JLabel lblTitle = new JLabel("🎁 Active & Inactive Discounts", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(120, 0, 120));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // باکس نمایش لیست تخفیف‌ها
        txtList = new JTextArea();
        txtList.setEditable(false);
        txtList.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        txtList.setBackground(new Color(255, 250, 252));
        txtList.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(txtList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Discount List"));

        // دکمه رفرش (بارگذاری مجدد لیست)
        JButton btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnRefresh.setBackground(new Color(204, 255, 229));
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshList();
            }
        });

        // دکمه برگشت
        JButton btnBack = new JButton("🔙 Back");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setBackground(new Color(204, 229, 255));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // بستن فرم
                if (parent != null) {
                    parent.setVisible(true); // برگشت به فرم قبلی
                }
            }
        });

        // چیدن دکمه‌ها پایین فرم
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnBack);

        // چیدن کل اجزا تو فرم
        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshList(); // بارگذاری اولیه تخفیف‌ها
        setVisible(true); // نمایش فرم
    }

    // متد برای خوندن لیست تخفیف‌ها از فایل و نمایش در textArea
    private void refreshList() {
        Discount[] discounts = dm.SelectAll(); // خوندن از فایل
        StringBuilder sb = new StringBuilder();

        if (discounts.length == 0) {
            sb.append("🥺 No discounts available yet!");
        } else {
            for (int i = 0; i < discounts.length; i++) {
                Discount d = discounts[i];
                if (d != null) {
                    sb.append(i + 1).append(". ")
                            .append("🏷️ Code: ").append(d.getDiscountCode())
                            .append(" | 🔢 ").append(d.getDiscountPercent()).append("%")
                            .append(" | ").append(d.isActive() ? "✅ Active" : "🚫 Inactive")
                            .append("\n----------------------------------\n");
                }
            }
        }

        txtList.setText(sb.toString());
    }

    // اجرای مستقل برای تست
    public static void main(String[] args) {
        new frmDiscountViewer(null);
    }
}
