import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Framuser extends JFrame {
    public Framuser() {
        // Set the title and size of the window
        setTitle("پنل مدیریت");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(255, 182, 193)); // Light Pink

        // Create the panel with GridLayout for button arrangement
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows and 2 columns with some space
        panel.setBackground(new Color(255, 182, 193));

        // Create buttons for different forms
        JButton btnAdminLogin = createButton("ورود مدیر", "path/to/admin_icon.png");
        JButton btnProduct = createButton("مدیریت محصولات", "path/to/product_icon.png");
        JButton btnBack = createButton("بازگشت", "path/to/back_icon.png");

        // Add action listeners for each button
        btnAdminLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Framuser().setVisible(true);  // Open admin login form
                dispose();
            }
        });

        btnProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new frmProduct().setVisible(true);  // Open product management form
                dispose();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new frmMain().setVisible(true);  // Go back to the main form
                dispose();
            }
        });

        // Add buttons to the panel
        panel.add(btnAdminLogin);
        panel.add(btnProduct);
        panel.add(btnBack);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);
    }

    // Helper method to create buttons with icons and styling
    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath)); // Set the button icon
        button.setBackground(new Color(221, 160, 221)); // Light Purple
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); // Button border
        return button;
    }

    public static void main(String[] args) {
        // Launch the form
        new Framuser().setVisible(true);  // Make sure to call the correct class
    }
}
