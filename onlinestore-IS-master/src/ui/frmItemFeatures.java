
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
    private JComboBox<String> cmbSkinTypeTarget;
    private JButton btnSave;
    private int productId;

    private JTextField txtBrandTarget, txtDescriptionTarget, txtIsOrganicTarget;

    public frmItemFeatures(int productId, JTextField brand, JTextField desc, JTextField skinType, JTextField isOrganic) {
        this.productId = productId;
        this.txtBrandTarget = brand;
        this.txtDescriptionTarget = desc;
        this.txtIsOrganicTarget = isOrganic;

        setTitle("🔧 Edit Item Features");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        txtBrand = new JTextField(brand.getText());
        txtDescription = new JTextField(desc.getText());
        cmbSkinTypeTarget = new JComboBox<>(new String[]{"Dry", "Oily", "Sensitive", "Normal", "Combination"});
        cmbSkinTypeTarget.setSelectedItem(skinType.getText());
        txtIsOrganic = new JTextField(isOrganic.getText());

        JComponent[] fields = {txtBrand, txtDescription, cmbSkinTypeTarget, txtIsOrganic};
        for (JComponent f : fields) f.setFont(font);

        btnSave = new JButton("💾 Save");
        btnSave.setBackground(new Color(204, 255, 204));
        btnSave.setFont(font);

        add(new JLabel("🏷️ Brand:")); add(txtBrand);
        add(new JLabel("🗒️ Description:")); add(txtDescription);
        add(new JLabel("🧴 Skin Type:")); add(cmbSkinTypeTarget);
        add(new JLabel("🍃 Is Organic (true/false):")); add(txtIsOrganic);
        add(new JLabel("")); add(btnSave);

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ItemFeaturesManager ifm = new ItemFeaturesManager();
                ItemFeatures f = new ItemFeatures(
                        productId,
                        txtBrand.getText(),
                        txtDescription.getText(),
                        cmbSkinTypeTarget.getSelectedItem().toString(),
                        Boolean.parseBoolean(txtIsOrganic.getText())
                );
                ifm.SaveOrUpdate(f);

                txtBrandTarget.setText(f.getBrand());
                txtDescriptionTarget.setText(f.getDescription());
                txtIsOrganicTarget.setText(String.valueOf(f.isOrganic()));
                skinType.setText(f.getSkinType());

                JOptionPane.showMessageDialog(frmItemFeatures.this, "✅ Features saved!");
                dispose();
            }
        });

        setVisible(true);
    }
}

























//
//package ui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import common.ItemFeatures;
//import managers.ItemFeaturesManager;
//
//public class frmItemFeatures extends JFrame {
//    private JTextField txtBrand, txtDescription, txtIsOrganic;
//    private JComboBox<String> cmbSkinType;
//    private JButton btnSave;
//    private int productId;
//
//    private JTextField txtBrandTarget, txtDescriptionTarget, txtIsOrganicTarget;
//    private JComboBox<String> cmbSkinTypeTarget;
//
//    public frmItemFeatures(int productId, JTextField brand, JTextField desc, JTextField skinType, JTextField isOrganic) {
//        this.productId = productId;
//        this.txtBrandTarget = brand;
//        this.txtDescriptionTarget = desc;
//        this.txtSkinTypeTarget = new JComboBox<>(new String[]{"Dry", "Oily", "Sensitive", "Normal", "Combination"});
//        this.txtSkinTypeTarget.setSelectedItem(skinType.getText());
//        this.txtIsOrganicTarget = isOrganic;
//
//        setTitle("🧾 Add/Edit Item Features");
//        setSize(400, 300);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setLayout(new GridLayout(5, 2, 10, 10));
//        getContentPane().setBackground(new Color(255, 240, 245));
//
//        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);
//
//        txtBrand = new JTextField(brand.getText());
//        txtDescription = new JTextField(desc.getText());
//        cmbSkinType = new JComboBox<>(new String[]{"Dry", "Oily", "Sensitive", "Normal", "Combination"});
//        cmbSkinType.setSelectedItem(skinType.getText());
//        txtIsOrganic = new JTextField(isOrganic.getText());
//
//        JLabel[] labels = {
//                new JLabel("🏷️ Brand:"), new JLabel("🗒️ Description:"),
//                new JLabel("🧴 Skin Type:"), new JLabel("🍃 Is Organic:")
//        };
//        JComponent[] fields = {txtBrand, txtDescription, cmbSkinType, txtIsOrganic};
//
//        for (int i = 0; i < labels.length; i++) {
//            labels[i].setFont(font);
//            fields[i].setFont(font);
//            add(labels[i]);
//            add(fields[i]);
//        }
//
//        btnSave = new JButton("💾 Save");
//        btnSave.setBackground(new Color(204, 255, 204));
//        btnSave.setFont(font);
//
//        add(new JLabel()); // spacer
//        add(btnSave);
//
//        btnSave.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                saveFeatures();
//            }
//        });
//    }
//
//    private void saveFeatures() {
//        try {
//            ItemFeatures item = new ItemFeatures(
//                    productId,
//                    txtBrand.getText(),
//                    txtDescription.getText(),
//                    cmbSkinType.getSelectedItem().toString(),
//                    Boolean.parseBoolean(txtIsOrganic.getText())
//            );
//
//            ItemFeaturesManager manager = new ItemFeaturesManager();
//            manager.SaveOrUpdate(item);
//
//            // بروزرسانی فیلدهای frmProduct
//            txtBrandTarget.setText(item.getBrand());
//            txtDescriptionTarget.setText(item.getDescription());
//            txtSkinTypeTarget.setSelectedItem(item.getSkinType());
//            txtIsOrganicTarget.setText(String.valueOf(item.isOrganic()));
//
//            JOptionPane.showMessageDialog(this, "✅ Features saved.");
//            dispose();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "❌ Invalid input!");
//        }
//    }
//}
//






































//package ui;
//
//import common.ItemFeatures;
//import managers.ItemFeaturesManager;
//
//import javax.swing.*;
//import java.awt.event.*;
//
//public class frmItemFeatures extends JFrame {
//    private JComboBox<String> cmbSkinType;
//    private JTextField txtBrand, txtDescription;
//    private JCheckBox chkOrganic;
//    private JButton btnSave;
//
//    private int productId;
//    private JTextField refBrand;
//    private JTextField refDescription;
//    private JTextField refSkinType;
//    private JTextField refIsOrganic;
//
//    private ItemFeaturesManager manager;
//
//    public frmItemFeatures(int productId, JTextField refBrand, JTextField refDescription, JTextField refSkinType, JTextField refIsOrganic) {
//        this.productId = productId;
//        this.refBrand = refBrand;
//        this.refDescription = refDescription;
//        this.refSkinType = refSkinType;
//        this.refIsOrganic = refIsOrganic;
//
//        setTitle("Item Features");
//        setSize(400, 300);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setLayout(null);
//
//        manager = new ItemFeaturesManager();
//
//        JLabel lblBrand = new JLabel("Brand:");
//        lblBrand.setBounds(30, 30, 100, 25);
//        add(lblBrand);
//
//        txtBrand = new JTextField();
//        txtBrand.setBounds(140, 30, 200, 25);
//        add(txtBrand);
//
//        JLabel lblDesc = new JLabel("Description:");
//        lblDesc.setBounds(30, 70, 100, 25);
//        add(lblDesc);
//
//        txtDescription = new JTextField();
//        txtDescription.setBounds(140, 70, 200, 25);
//        add(txtDescription);
//
//        JLabel lblSkin = new JLabel("Skin Type:");
//        lblSkin.setBounds(30, 110, 100, 25);
//        add(lblSkin);
//
//        cmbSkinType = new JComboBox<>(new String[]{"dry", "oily", "sensitive", "normal", "combination"});
//        cmbSkinType.setBounds(140, 110, 200, 25);
//        add(cmbSkinType);
//
//        chkOrganic = new JCheckBox("Is Organic?");
//        chkOrganic.setBounds(140, 150, 200, 25);
//        add(chkOrganic);
//
//        btnSave = new JButton("Save");
//        btnSave.setBounds(140, 190, 100, 30);
//        add(btnSave);
//
//        btnSave.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String brand = txtBrand.getText().trim();
//                String desc = txtDescription.getText().trim();
//                String skinType = cmbSkinType.getSelectedItem().toString();
//                boolean isOrg = chkOrganic.isSelected();
//
//                if (brand.equals("") || desc.equals("")) {
//                    JOptionPane.showMessageDialog(frmItemFeatures.this, "Please fill all fields.");
//                    return;
//                }
//
//                ItemFeatures f = new ItemFeatures(productId, brand, desc, skinType, isOrg);
//                manager.InsertOrUpdate(f);
//
//                refBrand.setText(brand);
//                refDescription.setText(desc);
//                refSkinType.setText(skinType);
//                refIsOrganic.setText(String.valueOf(isOrg));
//
//                JOptionPane.showMessageDialog(frmItemFeatures.this, "✅ Features saved.");
//                dispose();
//            }
//        });
//
//        loadExisting();
//    }
//
//    private void loadExisting() {
//        ItemFeatures existing = manager.SearchByProductId(productId);
//        if (existing != null) {
//            txtBrand.setText(existing.getBrand());
//            txtDescription.setText(existing.getDescription());
//            cmbSkinType.setSelectedItem(existing.getSkinType());
//            chkOrganic.setSelected(existing.isOrganic());
//        }
//    }
//}