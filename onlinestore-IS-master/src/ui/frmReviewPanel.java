

// =============================
// File: ui/frmReviewPanel.java
// =============================

package ui;

import common.Review;
import managers.ReviewManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class frmReviewPanel extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtName;
    private JComboBox<String> cmbRating;
    private JTextArea txtComment;
    private JButton btnSubmit;

    private int productId;
    private ReviewManager rm;

    public frmReviewPanel(int productId) {
        this.productId = productId;
        this.rm = new ReviewManager();

        setTitle("📝 Reviews for Product " + productId);
        setSize(600, 520);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 240, 250));

        model = new DefaultTableModel();
        model.addColumn("👤 Customer");
        model.addColumn("⭐ Rating");
        model.addColumn("💬 Comment");
        model.addColumn("📅 Date");

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 540, 200);
        add(scroll);

        JLabel lblName = new JLabel("👤 Your Name:");
        lblName.setBounds(20, 240, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(130, 240, 200, 25);
        add(txtName);

        JLabel lblRating = new JLabel("⭐ Rating:");
        lblRating.setBounds(20, 280, 100, 25);
        add(lblRating);

        cmbRating = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        cmbRating.setBounds(130, 280, 50, 25);
        add(cmbRating);

        JLabel lblComment = new JLabel("💬 Comment:");
        lblComment.setBounds(20, 320, 100, 25);
        add(lblComment);

        txtComment = new JTextArea();
        JScrollPane commentScroll = new JScrollPane(txtComment);
        commentScroll.setBounds(130, 320, 300, 60);
        add(commentScroll);

        btnSubmit = new JButton("✨ Submit Review");
        btnSubmit.setBounds(130, 400, 160, 30);
        btnSubmit.setBackground(new Color(255, 204, 229));
        btnSubmit.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText().trim();
                String comment = txtComment.getText().trim();
                int rating = Integer.parseInt(cmbRating.getSelectedItem().toString());

                if (name.equals("") || comment.equals("")) {
                    JOptionPane.showMessageDialog(frmReviewPanel.this, "Please fill all fields.");
                    return;
                }

                Review review = Review.create(productId, name, rating, comment);
                rm.Insert(review);
                JOptionPane.showMessageDialog(frmReviewPanel.this, "✅ Review submitted!");
                loadReviews();
                txtName.setText("");
                txtComment.setText("");
            }
        });

        loadReviews();
    }

    private void loadReviews() {
        model.setRowCount(0);
        Review[] reviews = rm.GetByProductId(productId);
        for (Review r : reviews) {
            model.addRow(new Object[]{
                    r.getCustomerName(),
                    r.getRating(),
                    r.getComment(),
                    r.getDate()
            });
        }
    }

    public static void main(String[] args) {
        new frmReviewPanel(101).setVisible(true);
    }

    // 👇 داخل همین کلاس: نسخه‌ی مستقل فقط برای ثبت
    public static class frmReview extends JFrame {
        public frmReview(int productId) {
            setTitle("Write a Review");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setLayout(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JLabel lblName = new JLabel("Your Name:");
            lblName.setBounds(30, 30, 100, 25);
            add(lblName);

            JTextField txtName = new JTextField();
            txtName.setBounds(130, 30, 200, 25);
            add(txtName);

            JLabel lblRating = new JLabel("Rating:");
            lblRating.setBounds(30, 70, 100, 25);
            add(lblRating);

            JComboBox<String> cmbRating = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
            cmbRating.setBounds(130, 70, 50, 25);
            add(cmbRating);

            JLabel lblComment = new JLabel("Comment:");
            lblComment.setBounds(30, 110, 100, 25);
            add(lblComment);

            JTextArea txtComment = new JTextArea();
            JScrollPane scroll = new JScrollPane(txtComment);
            scroll.setBounds(130, 110, 200, 60);
            add(scroll);

            JButton btnSubmit = new JButton("Submit");
            btnSubmit.setBounds(130, 190, 100, 30);
            add(btnSubmit);

            btnSubmit.addActionListener(e -> {
                String name = txtName.getText().trim();
                String comment = txtComment.getText().trim();
                int rating = Integer.parseInt(cmbRating.getSelectedItem().toString());
                if (name.equals("") || comment.equals("")) {
                    JOptionPane.showMessageDialog(frmReview.this, "Fill all fields");
                    return;
                }
                new ReviewManager().Insert(Review.create(productId, name, rating, comment));
                JOptionPane.showMessageDialog(frmReview.this, "Review Submitted");
                dispose();
            });
        }
    }

    // 👇 داخل همین کلاس: نسخه‌ی مستقل فقط برای نمایش
    public static class frmShowReviews extends JFrame {
        public frmShowReviews(int productId) {
            setTitle("View Reviews");
            setSize(500, 400);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Customer");
            model.addColumn("Rating");
            model.addColumn("Comment");
            model.addColumn("Date");

            JTable table = new JTable(model);
            JScrollPane scroll = new JScrollPane(table);
            add(scroll, BorderLayout.CENTER);

            Review[] reviews = new ReviewManager().GetByProductId(productId);
            for (Review r : reviews) {
                model.addRow(new Object[]{
                        r.getCustomerName(), r.getRating(), r.getComment(), r.getDate()
                });
            }
        }
    }
}






































//package ui;
//
//import common.Review;
//import managers.ReviewManager;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.*;
//
//public class frmReviewPanel extends JFrame {
//    private JTable table;
//    private DefaultTableModel model;
//    private JTextField txtName;
//    private JComboBox<String> cmbRating;
//    private JTextArea txtComment;
//    private JButton btnSubmit;
//
//    private int productId;
//    private ReviewManager rm;
//
//    public frmReviewPanel(int productId) {
//        this.productId = productId;
//        this.rm = new ReviewManager();
//
//        setTitle("Reviews for Product ID: " + productId);
//        setSize(600, 500);
//        setLocationRelativeTo(null);
//        setLayout(null);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//        model = new DefaultTableModel();
//        model.addColumn("Customer");
//        model.addColumn("Rating");
//        model.addColumn("Comment");
//        model.addColumn("Date");
//
//        table = new JTable(model);
//        JScrollPane scroll = new JScrollPane(table);
//        scroll.setBounds(20, 20, 540, 200);
//        add(scroll);
//
//        JLabel lblName = new JLabel("Your Name:");
//        lblName.setBounds(20, 240, 100, 25);
//        add(lblName);
//
//        txtName = new JTextField();
//        txtName.setBounds(120, 240, 200, 25);
//        add(txtName);
//
//        JLabel lblRating = new JLabel("Rating:");
//        lblRating.setBounds(20, 280, 100, 25);
//        add(lblRating);
//
//        cmbRating = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
//        cmbRating.setBounds(120, 280, 50, 25);
//        add(cmbRating);
//
//        JLabel lblComment = new JLabel("Comment:");
//        lblComment.setBounds(20, 320, 100, 25);
//        add(lblComment);
//
//        txtComment = new JTextArea();
//        JScrollPane commentScroll = new JScrollPane(txtComment);
//        commentScroll.setBounds(120, 320, 300, 60);
//        add(commentScroll);
//
//        btnSubmit = new JButton("Submit Review");
//        btnSubmit.setBounds(120, 400, 150, 30);
//        add(btnSubmit);
//
//        btnSubmit.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String name = txtName.getText().trim();
//                String comment = txtComment.getText().trim();
//                int rating = Integer.parseInt(cmbRating.getSelectedItem().toString());
//
//                if (name.equals("") || comment.equals("")) {
//                    JOptionPane.showMessageDialog(frmReviewPanel.this, "Please fill all fields.");
//                    return;
//                }
//
//                Review review = Review.create(productId, name, rating, comment);
//                rm.Insert(review);
//                JOptionPane.showMessageDialog(frmReviewPanel.this, "✅ Review submitted!");
//                loadReviews();
//                txtName.setText("");
//                txtComment.setText("");
//            }
//        });
//
//        loadReviews();
//    }
//
//    private void loadReviews() {
//        model.setRowCount(0);
//        Review[] reviews = rm.GetByProductId(productId);
//        for (Review r : reviews) {
//            model.addRow(new Object[]{
//                    r.getCustomerName(),
//                    r.getRating(),
//                    r.getComment(),
//                    r.getDate()
//            });
//        }
//    }
//
//    public static void main(String[] args) {
//        new frmReviewPanel(101).setVisible(true);
//    }
//}
