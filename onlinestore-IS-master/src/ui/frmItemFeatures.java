
package ui;

import common.ItemFeatures;
import managers.ItemFeaturesManager;

import javax.swing.*;
import java.awt.event.*;

public class frmItemFeatures extends JFrame {
    private JComboBox<String> cmbSkinType;
    private JTextField txtBrand, txtDescription;
    private JCheckBox chkOrganic;
    private JButton btnSave;

    private int productId;
    private JTextField refBrand;
    private JTextField refDescription;
    private JTextField refSkinType;
    private JTextField refIsOrganic;

    private ItemFeaturesManager manager;

    public frmItemFeatures(int productId, JTextField refBrand, JTextField refDescription, JTextField refSkinType, JTextField refIsOrganic) {
        this.productId = productId;
        this.refBrand = refBrand;
        this.refDescription = refDescription;
        this.refSkinType = refSkinType;
        this.refIsOrganic = refIsOrganic;

        setTitle("Item Features");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        manager = new ItemFeaturesManager();

        JLabel lblBrand = new JLabel("Brand:");
        lblBrand.setBounds(30, 30, 100, 25);
        add(lblBrand);

        txtBrand = new JTextField();
        txtBrand.setBounds(140, 30, 200, 25);
        add(txtBrand);

        JLabel lblDesc = new JLabel("Description:");
        lblDesc.setBounds(30, 70, 100, 25);
        add(lblDesc);

        txtDescription = new JTextField();
        txtDescription.setBounds(140, 70, 200, 25);
        add(txtDescription);

        JLabel lblSkin = new JLabel("Skin Type:");
        lblSkin.setBounds(30, 110, 100, 25);
        add(lblSkin);

        cmbSkinType = new JComboBox<>(new String[]{"dry", "oily", "sensitive", "normal", "combination"});
        cmbSkinType.setBounds(140, 110, 200, 25);
        add(cmbSkinType);

        chkOrganic = new JCheckBox("Is Organic?");
        chkOrganic.setBounds(140, 150, 200, 25);
        add(chkOrganic);

        btnSave = new JButton("Save");
        btnSave.setBounds(140, 190, 100, 30);
        add(btnSave);

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String brand = txtBrand.getText().trim();
                String desc = txtDescription.getText().trim();
                String skinType = cmbSkinType.getSelectedItem().toString();
                boolean isOrg = chkOrganic.isSelected();

                if (brand.equals("") || desc.equals("")) {
                    JOptionPane.showMessageDialog(frmItemFeatures.this, "Please fill all fields.");
                    return;
                }

                ItemFeatures f = new ItemFeatures(productId, brand, desc, skinType, isOrg);
                manager.InsertOrUpdate(f);

                refBrand.setText(brand);
                refDescription.setText(desc);
                refSkinType.setText(skinType);
                refIsOrganic.setText(String.valueOf(isOrg));

                JOptionPane.showMessageDialog(frmItemFeatures.this, "✅ Features saved.");
                dispose();
            }
        });

        loadExisting();
    }

    private void loadExisting() {
        ItemFeatures existing = manager.SearchByProductId(productId);
        if (existing != null) {
            txtBrand.setText(existing.getBrand());
            txtDescription.setText(existing.getDescription());
            cmbSkinType.setSelectedItem(existing.getSkinType());
            chkOrganic.setSelected(existing.isOrganic());
        }
    }
}