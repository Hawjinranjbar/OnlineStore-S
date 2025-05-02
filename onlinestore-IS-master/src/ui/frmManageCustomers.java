package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.CustomerManager;
import common.Customer;

public class frmManageCustomers extends JFrame {
    // اجزای گرافیکی فرم
    private JTextArea txtCustomerList; // برای نمایش لیست مشتری‌ها
    private JButton btnRefresh, btnUpdate, btnDelete, btnBack; // دکمه‌ها
    private CustomerManager cm; // مدیریت مشتری‌ها
    private JFrame parent; // برای برگشت به فرم قبلی

    // کانستراکتور فرم مدیریت مشتری‌ها
    public frmManageCustomers(JFrame parent) {
        this.parent = parent;

        // تنظیمات کلی فرم
        setTitle("👑 Admin Panel - Manage Customers");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 228, 240));

        cm = new CustomerManager(); // لود کلاس مدیریت مشتری‌ها
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        // لیست مشتری‌ها (غیرقابل ویرایش)
        txtCustomerList = new JTextArea();
        txtCustomerList.setEditable(false);
        txtCustomerList.setFont(font);

        // اسکرول لیست مشتری‌ها
        JScrollPane scrollPane = new JScrollPane(txtCustomerList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Customer List"));

        // ساخت دکمه‌ها
        btnRefresh = new JButton("🔄 Refresh");
        btnUpdate = new JButton("✏️ Update Customer");
        btnDelete = new JButton("❌ Delete Customer");
        btnBack = new JButton("🔙 Back to Dashboard");

        // رنگ و فونت دکمه‌ها
        btnRefresh.setBackground(new Color(204, 229, 255));
        btnUpdate.setBackground(new Color(255, 255, 153));
        btnDelete.setBackground(new Color(255, 204, 204));
        btnBack.setBackground(new Color(204, 255, 229));

        btnRefresh.setFont(font);
        btnUpdate.setFont(font);
        btnDelete.setFont(font);
        btnBack.setFont(font);

        // پنل پایین فرم برای قرار دادن دکمه‌ها
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(255, 228, 240));
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);

        // اضافه کردن بخش‌ها به فرم
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // رویداد کلیک دکمه Refresh (بازخوانی لیست)
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshList();
            }
        });

        // رویداد کلیک برای ویرایش مشتری
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // دریافت شماره ردیف مشتری برای ویرایش
                    int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to update:"));

                    // گرفتن اطلاعات جدید از ادمین
                    String newName = JOptionPane.showInputDialog("New name:");
                    String newPhone = JOptionPane.showInputDialog("New phone:");
                    String newEmail = JOptionPane.showInputDialog("New email:");
                    String newPassword = JOptionPane.showInputDialog("New password:");

                    // ساخت مشتری جدید با اطلاعات قدیمی + اطلاعات جدید
                    Customer oldCustomer = cm.SelectAll()[row];
                    Customer updatedCustomer = new Customer(
                            oldCustomer.getId(), // حفظ آیدی قبلی
                            newName,
                            newPhone,
                            newEmail,
                            newPassword
                    );

                    // ذخیره تغییرات
                    cm.Update(updatedCustomer, row);
                    JOptionPane.showMessageDialog(frmManageCustomers.this, "✅ Customer Updated!");
                    refreshList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmManageCustomers.this, "❌ Error updating customer.");
                }
            }
        });

        // رویداد کلیک برای حذف مشتری
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to delete:"));
                    cm.Delete(row);
                    JOptionPane.showMessageDialog(frmManageCustomers.this, "✅ Customer Deleted!");
                    refreshList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmManageCustomers.this, "❌ Error deleting customer.");
                }
            }
        });

        // برگشت به فرم قبلی (مثلاً داشبورد ادمین)
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // بستن فرم فعلی
                if (parent != null) {
                    parent.setVisible(true); // نمایش فرم قبلی
                }
            }
        });

        refreshList(); // بارگذاری اولیه مشتری‌ها
        setVisible(true); // نمایش فرم
    }

    // متد برای نمایش لیست مشتری‌ها داخل TextArea
    private void refreshList() {
        Customer[] customers = cm.SelectAll(); // گرفتن همه مشتری‌ها
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < customers.length; i++) {
            Customer c = customers[i];
            if (c != null) {
                sb.append(i).append(". ") // شماره ردیف
                        .append("👤 ").append(c.getName()) // نام مشتری
                        .append(" | 📧 ").append(c.getEmail()) // ایمیل
                        .append(" | 📱 ").append(c.getPhone()) // شماره تلفن
                        .append("\n\n");
            }
        }

        txtCustomerList.setText(sb.toString()); // نمایش توی TextArea
    }

    // اجرای مستقل برای تست مستقیم فرم
    public static void main(String[] args) {
        new frmManageCustomers(null);
    }
}
