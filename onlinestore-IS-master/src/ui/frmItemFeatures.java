
// =======================
// File: ui/frmItemFeatures.java
// =======================

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import common.ItemFeatures;
import managers.ItemFeaturesManager;

public class frmItemFeatures extends JFrame {
    private JTextField txtBrand, txtDescription, txtIsOrganic;
    private JComboBox<String> cmbSkinType;
    private JButton btnSave;

    private int productId;

    public frmItemFeatures(int productId) {
        this.productId = productId;

        setTitle("🔧 Edit Item Features");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        txtBrand = new JTextField();
        txtDescription = new JTextField();
        cmbSkinType = new JComboBox<>(new String[]{"Dry", "Oily", "Sensitive", "Normal", "Combination"});
        txtIsOrganic = new JTextField();

        JComponent[] fields = {txtBrand, txtDescription, cmbSkinType, txtIsOrganic};
        for (JComponent f : fields) f.setFont(font);

        btnSave = new JButton("💾 Save");
        btnSave.setBackground(new Color(204, 255, 204));
        btnSave.setFont(font);

        add(new JLabel("🏷️ Brand:")); add(txtBrand);
        add(new JLabel("🗒️ Description:")); add(txtDescription);
        add(new JLabel("🧴 Skin Type:")); add(cmbSkinType);
        add(new JLabel("🍃 Is Organic (true/false):")); add(txtIsOrganic);
        add(new JLabel("")); add(btnSave);

        // پر کردن فیلدها در صورت وجود اطلاعات قبلی
        loadExistingData();

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ItemFeaturesManager ifm = new ItemFeaturesManager();
                    ItemFeatures f = new ItemFeatures(
                            productId,
                            txtBrand.getText(),
                            txtDescription.getText(),
                            cmbSkinType.getSelectedItem().toString(),
                            Boolean.parseBoolean(txtIsOrganic.getText())
                    );
                    ifm.SaveOrUpdate(f);
                    JOptionPane.showMessageDialog(frmItemFeatures.this, "✅ Features saved!");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmItemFeatures.this, "❌ Error: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

    private void loadExistingData() {
        try {
            ItemFeaturesManager ifm = new ItemFeaturesManager();
            ItemFeatures f = ifm.SearchByProductId(productId);
            if (f != null) {
                txtBrand.setText(f.getBrand());
                txtDescription.setText(f.getDescription());
                cmbSkinType.setSelectedItem(f.getSkinType());
                txtIsOrganic.setText(String.valueOf(f.isOrganic()));
            }
        } catch (Exception e) {
            // Ignored - means it's a new entry
        }
    }

    public static void main(String[] args) {
        new frmItemFeatures(1); // برای تست دستی
    }
}
