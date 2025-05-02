
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.WishlistManager;
import managers.ProductManager;
import managers.CurrentCustomer;
import common.Wishlist;
import common.Product;

public class frmWishlist extends JFrame {
    private JPanel panelWishlist;
    private JScrollPane scrollPane;
    private Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 16);
    private WishlistManager wm = new WishlistManager();
    private ProductManager pm = new ProductManager();

    public frmWishlist() {
        setTitle("💖 Your Wishlist");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        panelWishlist = new JPanel(new GridLayout(0, 2, 10, 10));
        panelWishlist.setBackground(new Color(255, 240, 245));

        scrollPane = new JScrollPane(panelWishlist);
        scrollPane.setBorder(BorderFactory.createTitledBorder("🧡 Saved Products"));

        add(scrollPane, BorderLayout.CENTER);
        refreshWishlist();
        setVisible(true);
    }

    public frmWishlist(frmAdminDashboard frmAdminDashboard) {
    }

    private void refreshWishlist() {
        panelWishlist.removeAll();
        Wishlist[] all = wm.SelectAll();
        Product[] allProducts = pm.SelectAll();
        int customerId = CurrentCustomer.getLoggedInCustomerId();

        for (int i = 0; i < all.length; i++) {
            Wishlist w = all[i];
            if (w != null && w.getCustomerId() == customerId) {
                for (int j = 0; j < allProducts.length; j++) {
                    Product p = allProducts[j];
                    if (p != null && p.getId() == w.getProductId()) {
                        panelWishlist.add(createWishlistCard(p));
                        break;
                    }
                }
            }
        }

        panelWishlist.revalidate();
        panelWishlist.repaint();
    }

    private JPanel createWishlistCard(Product p) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.PINK));
        card.setPreferredSize(new Dimension(250, 350));

        JLabel lblImage = new JLabel();
        try {
            ImageIcon icon = new ImageIcon("images/" + p.getImageFileName());
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImage.setText("🖼️");
            lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JLabel lblName = new JLabel(p.getName(), SwingConstants.CENTER);
        lblName.setFont(emojiFont);

        JLabel lblPrice = new JLabel(p.getPrice() + " Toman", SwingConstants.CENTER);
        lblPrice.setFont(emojiFont);

        JButton btnRemove = new JButton("❌ Remove");
        btnRemove.setFont(emojiFont);
        btnRemove.setBackground(new Color(255, 204, 204));
        btnRemove.addActionListener(new RemoveWishlistHandler(p.getId()));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(lblName);
        infoPanel.add(lblPrice);

        card.add(lblImage, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnRemove, BorderLayout.SOUTH);

        return card;
    }

    private class RemoveWishlistHandler implements ActionListener {
        private int productId;

        public RemoveWishlistHandler(int productId) {
            this.productId = productId;
        }

        public void actionPerformed(ActionEvent e) {
            wm.Delete(CurrentCustomer.getLoggedInCustomerId(), productId);
            JOptionPane.showMessageDialog(frmWishlist.this, "❌ Removed from wishlist!");
            refreshWishlist();
        }
    }

    public static void main(String[] args) {
        new frmWishlist();
    }
}




