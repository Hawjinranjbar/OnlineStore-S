package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.AddressManager;
import common.Address;

public class frmAddAddress extends JFrame {
    private JTextField txtId, txtCity, txtStreet, txtPostalCode, txtDetails;
    private JButton btnSave, btnReturnToCart, btnBackToMenu;
    private AddressManager am;
    private JFrame parent;

    public frmAddAddress(JFrame parent) {
        this.parent = parent;
        setTitle("🏡 Add New Address");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 240, 245));

        am = new AddressManager();
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 14);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 240, 245));
        inputPanel.setBorder(BorderFactory.createTitledBorder("🏡 Enter Address Info"));

        txtId = new JTextField();
        txtCity = new JTextField();
        txtStreet = new JTextField();
        txtPostalCode = new JTextField();
        txtDetails = new JTextField();

        JTextField[] fields = {txtId, txtCity, txtStreet, txtPostalCode, txtDetails};
        for (int i = 0; i < fields.length; i++) {
            fields[i].setFont(font);
        }

        inputPanel.add(new JLabel("🆔 ID:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("🏙️ City:")); inputPanel.add(txtCity);
        inputPanel.add(new JLabel("🛣️ Street:")); inputPanel.add(txtStreet);
        inputPanel.add(new JLabel("📬 Postal Code:")); inputPanel.add(txtPostalCode);
        inputPanel.add(new JLabel("📝 Details:")); inputPanel.add(txtDetails);

        btnSave = new JButton("💾 Save Address");
        btnSave.setBackground(new Color(204, 255, 204));
        btnSave.setFont(font);
        btnSave.addActionListener(new SaveAddressListener());

        btnReturnToCart = new JButton("↩️ Return to Cart");
        btnReturnToCart.setBackground(new Color(255, 229, 204));
        btnReturnToCart.setFont(font);
        btnReturnToCart.setVisible(false);
        btnReturnToCart.addActionListener(new ReturnToCartListener());

        btnBackToMenu = new JButton("🔙 Back");
        btnBackToMenu.setBackground(new Color(204, 229, 255));
        btnBackToMenu.setFont(font);
        btnBackToMenu.addActionListener(new BackButtonListener());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnReturnToCart);
        buttonPanel.add(btnBackToMenu);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class SaveAddressListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Address address = new Address(
                        Integer.parseInt(txtId.getText()),
                        txtCity.getText(),
                        txtStreet.getText(),
                        txtPostalCode.getText(),
                        txtDetails.getText()
                );
                am.Insert(address);
                JOptionPane.showMessageDialog(frmAddAddress.this, "✅ Address saved successfully!");
                clearFields();
                btnReturnToCart.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frmAddAddress.this, "❌ Error saving address: " + ex.getMessage());
            }
        }
    }

    private class ReturnToCartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new frmCart(frmAddAddress.this); // back-safe
            setVisible(false);
        }
    }

    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            if (parent != null) {
                parent.setVisible(true);
            }
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtCity.setText("");
        txtStreet.setText("");
        txtPostalCode.setText("");
        txtDetails.setText("");
    }

    public static void main(String[] args) {
        new frmAddAddress(null); // برای تست
    }
}
