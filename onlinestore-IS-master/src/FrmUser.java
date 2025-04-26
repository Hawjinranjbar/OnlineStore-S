import common.User;
import managers.UserManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmUser {
}

import common.User;
import managers.UserManager;

import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FrmUser extends JFrame {
    private JTextField txtId, txtUsername, txtPassword;
    private JTable tblUsers;
    private DefaultTableModel model;

    public FrmUser() {
        setTitle("User Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 80, 25);
        add(lblId);
        txtId = new JTextField();
        txtId.setBounds(100, 20, 150, 25);
        add(txtId);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(20, 60, 80, 25);
        add(lblUsername);
        txtUsername = new JTextField();
        txtUsername.setBounds(100, 60, 150, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(20, 100, 80, 25);
        add(lblPassword);
        txtPassword = new JTextField();
        txtPassword.setBounds(100, 100, 150, 25);
        add(txtPassword);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(270, 20, 100, 25);
        add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(270, 60, 100, 25);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(270, 100, 100, 25);
        add(btnDelete);

        model = new DefaultTableModel(new Object[]{"ID", "Username", "Password", "Role"}, 0);
        tblUsers = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblUsers);
        scrollPane.setBounds(20, 150, 540, 200);
        add(scrollPane);

        loadUsers();

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText().trim());
                    String username = txtUsername.getText().trim();
                    String password = txtPassword.getText().trim();
                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Username and Password cannot be empty!");
                        return;
                    }
                    User user = new User(id, username, password, "admin");
                    UserManager.addUser(user);
                    loadUsers();
                    clearFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tblUsers.getSelectedRow();
                if (row >= 0) {
                    try {
                        int id = Integer.parseInt(txtId.getText().trim());
                        String username = txtUsername.getText().trim();
                        String password = txtPassword.getText().trim();
                        if (username.isEmpty() || password.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Username and Password cannot be empty!");
                            return;
                        }
                        User user = new User(id, username, password, "admin");
                        UserManager.updateUser(user, row);
                        loadUsers();
                        clearFields();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tblUsers.getSelectedRow();
                if (row >= 0) {
                    UserManager.deleteUser(row);
                    loadUsers();
                    clearFields();
                }
            }
        });

        tblUsers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int row = tblUsers.getSelectedRow();
                if (row >= 0) {
                    User user = UserManager.getUser(row);
                    if (user != null) {
                        txtId.setText(String.valueOf(user.getId()));
                        txtUsername.setText(user.getUsername());
                        txtPassword.setText(user.getPassword());
                    }
                }
            }
        });
    }

    private void loadUsers() {
        model.setRowCount(0);
        User[] users = UserManager.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            User user = users[i];
            if (user != null) {
                model.addRow(new Object[]{user.getId(), user.getUsername(), user.getPassword(), user.getRole()});
            }
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        tblUsers.clearSelection();
    }

    public static void main(String[] args) {
        FrmUser frame = new FrmUser();
        frame.setVisible(true);
    }
}
