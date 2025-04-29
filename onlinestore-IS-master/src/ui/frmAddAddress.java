package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import managers.AddressManager;
import common.Address;

public class frmAddAddress extends JFrame {
    private JTextField txtId, txtCity, txtStreet, txtPostalCode, txtDetails;
    private JButton btnSave;
    private AddressManager am;

    public frmAddAddress() {
        setTitle("🏡 Add New Address");
        setSize(500, 500);
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
        for (JTextField field : fields) field.setFont(font);

        inputPanel.add(new JLabel("🆔 ID:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("🏙️ City:")); inputPanel.add(txtCity);
        inputPanel.add(new JLabel("🛣️ Street:")); inputPanel.add(txtStreet);
        inputPanel.add(new JLabel("📬 Postal Code:")); inputPanel.add(txtPostalCode);
        inputPanel.add(new JLabel("📝 Details:")); inputPanel.add(txtDetails);

        btnSave = new JButton("💾 Save Address");
        btnSave.setBackground(new Color(204, 255, 204));
        btnSave.setFont(font);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(btnSave);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAddress();
            }
        });

        setVisible(true);
    }

    private void saveAddress() {
        try {
            Address address = new Address(
                    Integer.parseInt(txtId.getText()),
                    txtCity.getText(),
                    txtStreet.getText(),
                    txtPostalCode.getText(),
                    txtDetails.getText()
            );
            am.Insert(address);
            JOptionPane.showMessageDialog(this, "✅ Address saved successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error saving address: " + ex.getMessage());
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
        new frmAddAddress();
    }
}
