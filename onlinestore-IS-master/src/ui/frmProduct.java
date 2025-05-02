package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.ProductManager;
import managers.ItemFeaturesManager;
import common.Product;

public class frmProduct extends JFrame {
    private JTextField txtId = new JTextField();
    private JTextField txtName = new JTextField();
    private JTextField txtPrice = new JTextField();
    private JTextField txtStock = new JTextField();
    private JTextField txtImageUrl = new JTextField();
    private JComboBox<String> cmbCategory = new JComboBox<>(new String[]{"Makeup 💄", "Skincare 🌿", "Haircare 💇‍♀️", "Bodycare 🧴"});

    private JButton btnInsert = new JButton("➕ Insert");
    private JButton btnUpdate = new JButton("✏️ Update");
    private JButton btnDelete = new JButton("❌ Delete");
    private JButton btnLoadFeatures = new JButton("🔍 Load Features");
    private JButton btnEditFeatures = new JButton("🔧 Edit Features");
    private JButton btnBack = new JButton("🔙 Back");

    private JTextArea txtList = new JTextArea();
    private ProductManager pm = new ProductManager();
    private ItemFeaturesManager ifm = new ItemFeaturesManager();
    private JFrame callerFrame;
        public frmProduct(JFrame callerFrame) {
            this.callerFrame = callerFrame;

            // تنظیم ظاهر فرم
            setTitle("👑 Admin Panel - Manage Products");
            setSize(900, 700);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout(10, 10));
            getContentPane().setBackground(new Color(255, 240, 245));

            // ساخت پنل ورودی اطلاعات محصول
            JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            inputPanel.setBorder(BorderFactory.createTitledBorder("🛠️ Product Details"));
            inputPanel.setBackground(new Color(255, 240, 245));

            // ست کردن فونت برای فیلدها
            Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);
            JComponent[] fields = {txtId, txtName, txtPrice, txtStock, cmbCategory, txtImageUrl};
            for (JComponent comp : fields) comp.setFont(font);

            // اضافه کردن لیبل‌ها و فیلدها به پنل
            inputPanel.add(new JLabel("🆔 Product ID:")); inputPanel.add(txtId);
            inputPanel.add(new JLabel("📝 Name:")); inputPanel.add(txtName);
            inputPanel.add(new JLabel("💰 Price:")); inputPanel.add(txtPrice);
            inputPanel.add(new JLabel("📦 Stock:")); inputPanel.add(txtStock);
            inputPanel.add(new JLabel("📂 Category:")); inputPanel.add(cmbCategory);
            inputPanel.add(new JLabel("🖼️ Image URL:")); inputPanel.add(txtImageUrl);

            // دکمه‌ها و رنگشون
            JButton[] buttons = {btnInsert, btnUpdate, btnDelete, btnLoadFeatures, btnEditFeatures, btnBack};
            for (JButton b : buttons) b.setFont(font);
            btnInsert.setBackground(new Color(204, 255, 204));
            btnUpdate.setBackground(new Color(255, 255, 153));
            btnDelete.setBackground(new Color(255, 204, 204));
            btnLoadFeatures.setBackground(new Color(204, 229, 255));
            btnEditFeatures.setBackground(new Color(204, 229, 255));
            btnBack.setBackground(new Color(255, 228, 225));

            // ساخت پنل دکمه‌ها
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            buttonPanel.setBackground(new Color(255, 240, 245));
            for (JButton b : buttons) buttonPanel.add(b);

            // تنظیمات TextArea برای نمایش لیست محصولات
            txtList.setFont(font);
            txtList.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(txtList);
            scrollPane.setBorder(BorderFactory.createTitledBorder("📚 Product List"));
            scrollPane.setPreferredSize(new Dimension(850, 250));

            // اضافه کردن همه پنل‌ها به فرم
            add(inputPanel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.SOUTH);

            // دکمه Insert - اضافه کردن محصول
            btnInsert.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        Product p = getProductFromInput(); // گرفتن اطلاعات از فرم
                        pm.Insert(p); // ذخیره در فایل
                        refreshList(); // نمایش جدید
                        JOptionPane.showMessageDialog(frmProduct.this, "✅ Product Inserted!");
                        new frmShowProducts(p.getCategory()); // نمایش در فرم محصولات
                        dispose(); // بستن فرم فعلی
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmProduct.this, "❌ Error inserting product.");
                    }
                }
            });

            // دکمه Update - ویرایش محصول
            btnUpdate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to update:"));
                        Product p = getProductFromInput();
                        pm.Update(p, row);
                        refreshList();
                        JOptionPane.showMessageDialog(frmProduct.this, "✅ Product updated successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmProduct.this, "❌ Error updating product.");
                    }
                }
            });

            // دکمه Delete - حذف محصول
            btnDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row number to delete:"));
                        pm.Delete(row);
                        refreshList();
                        JOptionPane.showMessageDialog(frmProduct.this, "❌ Product Deleted!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmProduct.this, "❌ Error deleting product.");
                    }
                }
            });

            // دکمه Load Features - فقط پیام نمایشی
            btnLoadFeatures.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frmProduct.this, "🔍 Load Features is still available, but isOrganic and skinType fields have been removed from this form.");
                }
            });

            // دکمه Edit Features - باز کردن فرم ویژگی‌ها
            btnEditFeatures.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(txtId.getText());
                        new frmItemFeatures(id);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmProduct.this, "❌ Enter valid product ID first.");
                    }
                }
            });

            // دکمه Back - برگشت به فرم قبل
            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (callerFrame != null) {
                        callerFrame.setVisible(true);
                    }
                    dispose();
                }
            });

            refreshList(); // پر کردن لیست محصول‌ها در شروع فرم
            setVisible(true);
        }

        // متد ساختن شی Product از فیلدهای فرم
        private Product getProductFromInput() {
            String categoryRaw = cmbCategory.getSelectedItem().toString();
            String categoryClean = categoryRaw.split(" ")[0];

            return new Product(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    "",      // برند حذف شده
                    "",      // توضیحات حذف شده
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText()),
                    categoryClean,
                    "",      // نوع پوست حذف شده
                    false,   // ارگانیک حذف شده
                    txtImageUrl.getText()
            );
        }

        // متد نمایش محصولات توی text area
        private void refreshList() {
            Product[] products = pm.SelectAll();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < products.length; i++) {
                Product p = products[i];
                if (p != null) {
                    sb.append(i).append(". ")
                            .append(p.getName())
                            .append(" | 💰 ").append(p.getPrice())
                            .append(" | 🛒 Stock: ").append(p.getStock())
                            .append(" | 🔖 Category: ").append(p.getCategory())
                            .append("\n\n");
                }
            }

            txtList.setText(sb.toString());
        }

        // اجرای فرم به صورت مستقل
        public static void main(String[] args) {
            new frmProduct(null);
        }
    }


