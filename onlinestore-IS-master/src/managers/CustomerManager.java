package managers;

import common.Customer;
import filemanager.txtFileManager;

public class CustomerManager {
    private txtFileManager fm;

    public CustomerManager() {
        fm = new txtFileManager("customer.txt");
    }

    public void Insert(Customer c) {
        fm.AppendRow(c.toString());
    }

    public void Update(Customer c, int row) {
        fm.UpdateRow(c.toString(), row);
    }

    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    public Customer[] SelectAll() {
        String[] rows = fm.GetArray();
        Customer[] customers = new Customer[rows.length];
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";");
                if (parts.length == 5) {
                    customers[i] = new Customer(
                            Integer.parseInt(parts[0]),
                            parts[1], // name
                            parts[2], // phone
                            parts[3], // email
                            parts[4]  // password
                    );
                }
            }
        }
        return customers;
    }

    public Customer findByPhone(String phone) {
        Customer[] customers = SelectAll();
        for (Customer c : customers) {
            if (c != null && c.getPhone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

    public Customer findByEmailAndPassword(String email, String password) {
        Customer[] customers = SelectAll();
        for (Customer c : customers) {
            if (c != null && c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }
}
