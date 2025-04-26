import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class frmMain extends JFrame {
    public frmMain() {
        setTitle("پنل اصلی");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color
        getContentPane().setBackground(new Color(255, 182, 193)); // Light Pink

        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns
        panel.setBackground(new Color(255, 182, 193));

        // Create buttons
        JButton btnManager = createButton("مدیر", "path/to/manager_icon.png");
        JButton btnCustomer = createButton("مشتری", "path/to/customer_icon.png");

        // Button actions
        btnManager.addActionListener(e -> {
            new FrmUser().setVisible(true);  // Admin Login form
            dispose();
        });

        btnCustomer.addActionListener(e -> {
            new frmCustomerRegistration().setVisible(true);  // Customer Registration form
            dispose();
        });

        panel.add(btnManager);
        panel.add(btnCustomer);

        // Add panel to the center of the frame
        add(panel, BorderLayout.CENTER);

        // Exit button at the bottom
        JButton btnBack = new JButton("خروج");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.setBackground(new Color(221, 160, 221)); // Purple
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> System.exit(0)); // Exit the program

        // Add the Exit button to the bottom of the frame
        add(btnBack, BorderLayout.SOUTH);
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
        new frmMain().setVisible(true);
    }
}
