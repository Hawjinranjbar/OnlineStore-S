

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class frmMain extends JFrame {
    public frmMain() {
        setTitle("🏠 Online Shop - Main Menu");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 💗 منوی بالا بدون آیکن
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        menuPanel.setBackground(new Color(255, 228, 240));

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        JButton btnCart = new JButton("Cart");
        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Signup");
        JButton btnAdminLogin = new JButton("Admin Login");

        JButton[] buttons = {btnCart, btnLogin, btnRegister, btnAdminLogin};
        for (JButton btn : buttons) {
            btn.setFont(font);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(255, 228, 240));
            btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            menuPanel.add(btn);
        }

        btnCart.addActionListener(e -> new frmCart());
        btnLogin.addActionListener(e -> new frmLoginCustomer());
        btnRegister.addActionListener(e -> new frmRegisterCustomer());
        btnAdminLogin.addActionListener(e -> new frmLoginAdmin());

        add(menuPanel, BorderLayout.NORTH);

        // 💗 دسته‌بندی‌ها
        JPanel categoryPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        categoryPanel.setBackground(new Color(255, 228, 240));
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] categories = {"ALL", "Makeup", "Skincare", "Haircare", "Bodycare", "Discounts"};
        String[] imageFiles = {"all.jpg", "makeup.jpg", "skincare.jpg", "haircare.jpg", "bodycare.jpg", "discounts.jpg"};

        for (int i = 0; i < categories.length; i++) {
            int finalI = i;
            String categoryName = categories[i];

            JPanel box = new JPanel() {
                BufferedImage bg = null;
                {
                    try {
                        bg = ImageIO.read(new File("images/" + imageFiles[finalI]));
                    } catch (Exception e) {
                        System.out.println("❌ Couldn't load " + imageFiles[finalI]);
                    }
                }

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (bg != null) {
                        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                    } else {
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }

                    // متن وسط با حاشیه مشکی
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(categoryName);
                    int x = (getWidth() - textWidth) / 2;
                    int y = getHeight() / 2;

                    g2.setColor(Color.BLACK);
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (dx != 0 || dy != 0) {
                                g2.drawString(categoryName, x + dx, y + dy);
                            }
                        }
                    }

                    g2.setColor(Color.WHITE);
                    g2.drawString(categoryName, x, y);
                }
            };

            box.setPreferredSize(new Dimension(200, 180));
            box.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            box.setLayout(null);

            box.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (categoryName.equalsIgnoreCase("Discounts")) {
                        new frmDiscountViewer();
                    } else {
                        new frmShowProducts(categoryName);
                    }
                }
            });

            categoryPanel.add(box);
        }

        add(categoryPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new frmMain();
    }
}
