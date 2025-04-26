import common.Review;
import managers.ReviewManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class frmReview extends JFrame {
    private JTextField txtProductId, txtCustomerName, txtRating, txtComment;
    private JTable tblReviews;
    private DefaultTableModel model;

    public frmReview() {
        setTitle("Review Management");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 228, 250));
        setLayout(null);

        JLabel lblProductId = new JLabel("Product ID:");
        lblProductId.setBounds(20, 20, 100, 25);
        lblProductId.setForeground(new Color(138, 43, 226));
        add(lblProductId);
        txtProductId = new JTextField();
        txtProductId.setBounds(130, 20, 150, 25);
        add(txtProductId);

        JLabel lblCustomerName = new JLabel("Customer Name:");
        lblCustomerName.setBounds(20, 60, 100, 25);
        lblCustomerName.setForeground(new Color(138, 43, 226));
        add(lblCustomerName);
        txtCustomerName = new JTextField();
        txtCustomerName.setBounds(130, 60, 150, 25);
        add(txtCustomerName);

        JLabel lblRating = new JLabel("Rating (1-5):");
        lblRating.setBounds(20, 100, 100, 25);
        lblRating.setForeground(new Color(138, 43, 226));
        add(lblRating);
        txtRating = new JTextField();
        txtRating.setBounds(130, 100, 150, 25);
        add(txtRating);

        JLabel lblComment = new JLabel("Comment:");
        lblComment.setBounds(20, 140, 100, 25);
        lblComment.setForeground(new Color(138, 43, 226));
        add(lblComment);
        txtComment = new JTextField();
        txtComment.setBounds(130, 140, 150, 25);
        add(txtComment);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(320, 20, 100, 25);
        add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(320, 60, 100, 25);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(320, 100, 100, 25);
        add(btnDelete);

        model = new DefaultTableModel(new Object[]{"Product ID", "Customer Name", "Rating", "Comment"}, 0);
        tblReviews = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblReviews);
        scrollPane.setBounds(20, 200, 640, 200);
        add(scrollPane);

        loadReviews();

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int productId = Integer.parseInt(txtProductId.getText().trim());
                    String customerName = txtCustomerName.getText().trim();
                    int rating = Integer.parseInt(txtRating.getText().trim());
                    String comment = txtComment.getText().trim();

                    Review review = new Review(productId, customerName, rating, comment);
                    ReviewManager.addReview(review);
                    clearFields();
                    loadReviews(); // reload from file after adding!
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tblReviews.getSelectedRow();
                if (row >= 0) {
                    try {
                        int productId = Integer.parseInt(txtProductId.getText().trim());
                        String customerName = txtCustomerName.getText().trim();
                        int rating = Integer.parseInt(txtRating.getText().trim());
                        String comment = txtComment.getText().trim();

                        Review review = new Review(productId, customerName, rating, comment);
                        ReviewManager.updateReview(review, row);
                        clearFields();
                        loadReviews(); // reload from file after update!
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tblReviews.getSelectedRow();
                if (row >= 0) {
                    ReviewManager.deleteReview(row);
                    clearFields();
                    loadReviews(); // reload after delete!
                }
            }
        });

        tblReviews.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int row = tblReviews.getSelectedRow();
                if (row >= 0) {
                    Review review = ReviewManager.getReview(row);
                    if (review != null) {
                        txtProductId.setText(String.valueOf(review.getProductId()));
                        txtCustomerName.setText(review.getCustomerName());
                        txtRating.setText(String.valueOf(review.getRating()));
                        txtComment.setText(review.getComment());
                    }
                }
            }
        });
    }

    private void loadReviews() {
        model.setRowCount(0); // Clear table first
        Review[] reviews = ReviewManager.getAllReviews();
        for (int i = 0; i < reviews.length; i++) {
            Review review = reviews[i];
            if (review != null) {
                model.addRow(new Object[]{review.getProductId(), review.getCustomerName(), review.getRating(), review.getComment()});
            }
        }
    }

    private void clearFields() {
        txtProductId.setText("");
        txtCustomerName.setText("");
        txtRating.setText("");
        txtComment.setText("");
        tblReviews.clearSelection();
    }

    public static void main(String[] args) {
        frmReview frame = new frmReview();
        frame.setVisible(true);
    }
}
