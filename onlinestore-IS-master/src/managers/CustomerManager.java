package managers;

import common.Customer;
import filemanager.txtFileManager;

public class CustomerManager {
    private txtFileManager fm;
    private String fileName = "Customer.txt";

    public CustomerManager() {
        fm = new txtFileManager(fileName);
    }

    // ➕ ثبت مشتری
    public void Insert(Customer c) {
        fm.AppendRow(c.toString());
    }

    // ✏️ ویرایش مشتری
    public void Update(Customer c, int rowNumber) {
        fm.UpdateRow(c.toString(), rowNumber);
    }
}
