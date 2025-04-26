//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class frmCustomerRegistration extends JFrame {
//    public frmCustomerRegistration() {
//        setTitle("پنل ثبت‌نام مشتری");
//        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // پنل برای نمایش آیکون‌ها
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(2, 2));  // ۲ ردیف و ۲ ستون
//
//        // ساخت دکمه‌ها برای ورود به هر فرم
//        JButton btnCustomer = new JButton("ثبت‌نام مشتری");
//        JButton btnReview = new JButton("بازخورد");
//        JButton btnOrder = new JButton("سفارشات");
//        JButton btnBack = new JButton("بازگشت");
//
//        // تنظیم آیکون‌ها (مسیرها رو باید تغییر بدید به آیکون‌های واقعی)
//        btnCustomer.setIcon(new ImageIcon("path/to/customer_icon.png"));
//        btnReview.setIcon(new ImageIcon("path/to/review_icon.png"));
//        btnOrder.setIcon(new ImageIcon("path/to/order_icon.png"));
//        btnBack.setIcon(new ImageIcon("path/to/back_icon.png"));
//
//        // افزودن اکشن برای دکمه‌ها
//        btnCustomer.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new frmCustomer().setVisible(true);  // فرم ثبت‌نام مشتری
//                dispose();
//            }
//        });
//
//        btnReview.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new frmReview().setVisible(true);  // فرم بازخورد مشتری
//                dispose();
//            }
//        });
//
//        btnOrder.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new frmOrderCustomer().setVisible(true);  // فرم سفارشات مشتری
//                dispose();
//            }
//        });
//
//        btnBack.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new frmMain().setVisible(true);  // بازگشت به فرم اصلی
//                dispose();
//            }
//        });
//
//        // افزودن دکمه‌ها به پنل
//        panel.add(btnCustomer);
//        panel.add(btnReview);
//        panel.add(btnOrder);
//        panel.add(btnBack);
//
//        // افزودن پنل به فریم
//        add(panel, BorderLayout.CENTER);
//    }
//
//    public static void main(String[] args) {
//        new frmCustomerRegistration().setVisible(true);
//    }
//}
























import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class frmCustomerRegistration extends JFrame {
    public frmCustomerRegistration() {
        setTitle("پنل ثبت‌نام مشتری");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(255, 182, 193)); // Light Pink

        // Create panel with GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));  // ۲ ردیف و ۲ ستون
        panel.setBackground(new Color(255, 182, 193));

        // Create Buttons for different actions
        JButton btnCustomer = createButton("ثبت‌نام مشتری", "path/to/customer_icon.png");
        JButton btnReview = createButton("بازخورد", "path/to/review_icon.png");
        JButton btnOrder = createButton("سفارشات", "path/to/order_icon.png");
        JButton btnBack = createButton("بازگشت", "path/to/back_icon.png");

        // Adding ActionListeners to the buttons
        btnCustomer.addActionListener(e -> {
            new frmCustomer().setVisible(true);
            dispose();
        });

        btnReview.addActionListener(e -> {
            new frmReview().setVisible(true);
            dispose();
        });

        btnOrder.addActionListener(e -> {
            new frmOrderCustomer().setVisible(true);
            dispose();
        });

        btnBack.addActionListener(e -> {
            new frmMain().setVisible(true);
            dispose();
        });

        // Adding buttons to the panel
        panel.add(btnCustomer);
        panel.add(btnReview);
        panel.add(btnOrder);
        panel.add(btnBack);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);
    }

    // Helper method to create buttons with icons
    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath)); // Set the button icon
        button.setBackground(new Color(221, 160, 221)); // Purple
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        return button;
    }

    public static void main(String[] args) {
        new frmCustomerRegistration().setVisible(true);
    }
}
