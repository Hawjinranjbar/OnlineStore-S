package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.ProductManager;
import managers.CartManager;
import managers.WishlistManager;
import managers.CurrentCustomer;
import common.Product;
import common.Cart;
import common.Wishlist;
import java.text.NumberFormat;
import java.util.Locale;

public class frmShowProducts extends JFrame {
        // کامپوننت‌های جستجو و فیلتر
        private JTextField txtSearch;
        private JButton btnSearch;
        private JComboBox<String> cmbCategory;

        // ابزارهای مدیریت داده‌ها
        private ProductManager pm;
        private JLabel lblResultCount;
        private JPanel panelProducts;
        private JScrollPane scrollPane;

        private Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 16);

        // کانستراکتور فرم
        public frmShowProducts(String categoryName) {
            setTitle("💋 Beauty Shop - View Products");
            setSize(900, 700);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));
            getContentPane().setBackground(new Color(255, 228, 240));

            pm = new ProductManager(); // وصل شدن به داده‌های محصولات

            // ساخت پنل بالایی (جستجو، فیلتر و wishlist)
            JPanel topPanel = new JPanel(new BorderLayout(10, 10));
            topPanel.setBackground(new Color(255, 228, 240));

            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            searchPanel.setBackground(new Color(255, 228, 240));

            // دکمه برگشت
            JButton btnBack = new JButton("🔙 Back to Categories");
            btnBack.setFont(emojiFont);
            btnBack.setBackground(new Color(204, 255, 255));
            btnBack.addActionListener(new BackButtonListener());

            // دکمه علاقه‌مندی‌ها
            JButton btnWishlistPanel = new JButton("💖 Wishlist");
            btnWishlistPanel.setFont(emojiFont);
            btnWishlistPanel.setBackground(new Color(255, 204, 229));
            btnWishlistPanel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new frmWishlist(); // باز کردن فرم علاقه‌مندی‌ها
                }
            });

            // فیلد جستجو و دکمه جستجو
            txtSearch = new JTextField(20);
            txtSearch.setFont(emojiFont);

            btnSearch = new JButton("🔍 Search");
            btnSearch.setBackground(new Color(255, 182, 193));
            btnSearch.setFont(emojiFont);
            btnSearch.addActionListener(new SearchButtonListener());

            // فیلتر دسته‌بندی
            cmbCategory = new JComboBox<>(new String[]{"All", "Skincare", "Makeup", "Haircare", "Bodycare"});
            cmbCategory.setFont(emojiFont);
            cmbCategory.setSelectedItem(categoryName);
            cmbCategory.addActionListener(new CategoryChangeListener());

            // چیدن اجزای پنل جستجو
            searchPanel.add(btnBack);
            searchPanel.add(btnWishlistPanel);
            searchPanel.add(txtSearch);
            searchPanel.add(btnSearch);
            searchPanel.add(cmbCategory);

            // لیبل تعداد نتایج
            lblResultCount = new JLabel(" ");
            lblResultCount.setHorizontalAlignment(SwingConstants.CENTER);
            lblResultCount.setForeground(Color.DARK_GRAY);
            lblResultCount.setFont(emojiFont);

            topPanel.add(searchPanel, BorderLayout.CENTER);
            topPanel.add(lblResultCount, BorderLayout.SOUTH);

            // پنل نمایش کارت‌های محصولات
            panelProducts = new JPanel(new GridLayout(0, 2, 10, 10));
            panelProducts.setBackground(new Color(255, 228, 240));

            scrollPane = new JScrollPane(panelProducts);
            scrollPane.setBorder(BorderFactory.createTitledBorder("✨ Available Beauty Products"));

            // اضافه کردن به فرم
            add(topPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);

            // نمایش اولیه محصولات
            searchProducts();
            setVisible(true);
        }

        // متد جستجو و فیلتر محصولات
        private void searchProducts() {
            String keyword = txtSearch.getText().trim().toLowerCase();
            String selectedCategory = cmbCategory.getSelectedItem().toString();

            Product[] products = pm.SelectAll();
            panelProducts.removeAll(); // حذف کارت‌های قبلی
            int count = 0;

            // فیلتر و ساخت کارت برای هر محصول
            for (Product p : products) {
                if (p != null) {
                    boolean matchKeyword = keyword.isEmpty() || p.getName().toLowerCase().contains(keyword);
                    boolean matchCategory = selectedCategory.equals("All") || p.getCategory().equalsIgnoreCase(selectedCategory);

                    if (matchKeyword && matchCategory) {
                        panelProducts.add(createProductCard(p)); // ساخت کارت محصول
                        count++;
                    }
                }
            }

            // پیام اگه چیزی پیدا نشد
            if (count == 0) {
                JLabel lbl = new JLabel("🥺 No products found.");
                lbl.setFont(emojiFont);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                panelProducts.add(lbl);
            }

            lblResultCount.setText(count + " products found."); // آپدیت لیبل تعداد
            panelProducts.revalidate();
            panelProducts.repaint();
        }

        // ساختن کارت برای هر محصول
        private JPanel createProductCard(Product p) {
            JPanel card = new JPanel(new BorderLayout(5, 5));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            card.setPreferredSize(new Dimension(250, 400));

            // عکس محصول
            JLabel lblImage = new JLabel();
            try {
                ImageIcon icon = new ImageIcon("images/" + p.getImageFileName());
                Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(image));
            } catch (Exception e) {
                lblImage.setText("🖼️");
                lblImage.setFont(emojiFont);
                lblImage.setHorizontalAlignment(SwingConstants.CENTER);
            }
            lblImage.setHorizontalAlignment(SwingConstants.CENTER);

            // اطلاعات متنی محصول
            JLabel lblName = new JLabel("🌸 " + p.getName(), SwingConstants.CENTER);
            lblName.setFont(emojiFont);

            JLabel lblPrice = new JLabel(formatPrice(p.getPrice()) + " Toman", SwingConstants.CENTER);
            lblPrice.setFont(emojiFont);

            JLabel lblCategory = new JLabel("Category: " + p.getCategory(), SwingConstants.CENTER);
            lblCategory.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 14));

            JPanel infoPanel = new JPanel(new GridLayout(3, 1));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.add(lblName);
            infoPanel.add(lblPrice);
            infoPanel.add(lblCategory);

            // دکمه‌های کارت محصول
            JPanel btnPanel = new JPanel(new GridLayout(3, 1));
            btnPanel.setBackground(Color.WHITE);

            JButton btnAddToCart = new JButton("➕ Add to Cart");
            btnAddToCart.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            btnAddToCart.setBackground(new Color(255, 204, 229));
            btnAddToCart.addActionListener(new AddToCartListener(p));

            JButton btnReview = new JButton("📝 Review");
            btnReview.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            btnReview.setBackground(new Color(229, 229, 255));
            btnReview.addActionListener(new ReviewListener(p));

            JButton btnWishlist = new JButton("🤍 Wishlist");
            btnWishlist.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            WishlistManager wm = new WishlistManager();
            boolean alreadyInWishlist = wm.Exists(CurrentCustomer.getLoggedInCustomerId(), p.getId());
            btnWishlist.setBackground(alreadyInWishlist ? new Color(255, 102, 153) : new Color(220, 220, 220));
            btnWishlist.addActionListener(new WishlistListener(p, btnWishlist));

            btnPanel.add(btnAddToCart);
            btnPanel.add(btnReview);
            btnPanel.add(btnWishlist);

            // چیدن کارت نهایی
            card.add(lblImage, BorderLayout.NORTH);
            card.add(infoPanel, BorderLayout.CENTER);
            card.add(btnPanel, BorderLayout.SOUTH);

            return card;
        }

        // فرمت قیمت
        private String formatPrice(double price) {
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            return nf.format(price);
        }

        // دکمه سبد خرید
        private class AddToCartListener implements ActionListener {
            private Product product;
            public AddToCartListener(Product product) { this.product = product; }

            public void actionPerformed(ActionEvent e) {
                try {
                    CartManager cm = new CartManager();
                    Cart[] current = cm.SelectAll();
                    for (Cart c : current) {
                        if (c != null && c.getProductId() == product.getId()) {
                            JOptionPane.showMessageDialog(frmShowProducts.this, "⚠️ Product already in cart.");
                            return;
                        }
                    }
                    cm.Insert(new Cart(product.getId(), 1));
                    JOptionPane.showMessageDialog(frmShowProducts.this, "🛒 Product added to cart!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmShowProducts.this, "❌ Error adding product to cart.");
                }
            }
        }

        // دکمه باز کردن فرم نظرات
        private class ReviewListener implements ActionListener {
            private Product product;
            public ReviewListener(Product product) { this.product = product; }

            public void actionPerformed(ActionEvent e) {
                new frmReviewPanel(product.getId()).setVisible(true);
            }
        }

        // دکمه wishlist
        private class WishlistListener implements ActionListener {
            private Product product;
            private JButton button;

            public WishlistListener(Product product, JButton button) {
                this.product = product;
                this.button = button;
            }

            public void actionPerformed(ActionEvent e) {
                WishlistManager wm = new WishlistManager();
                int customerId = CurrentCustomer.getLoggedInCustomerId();
                if (!wm.Exists(customerId, product.getId())) {
                    wm.Insert(new Wishlist(customerId, product.getId()));
                    button.setBackground(new Color(255, 102, 153));
                    JOptionPane.showMessageDialog(frmShowProducts.this, "💖 Added to wishlist!");
                } else {
                    wm.Delete(customerId, product.getId());
                    button.setBackground(new Color(220, 220, 220));
                    JOptionPane.showMessageDialog(frmShowProducts.this, "❌ Removed from wishlist!");
                }
            }
        }

        // دکمه جستجو
        private class SearchButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        }

        // تغییر دسته‌بندی
        private class CategoryChangeListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        }

        // برگشت به فرم اصلی
        private class BackButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new frmMain();
            }
        }
    }



















