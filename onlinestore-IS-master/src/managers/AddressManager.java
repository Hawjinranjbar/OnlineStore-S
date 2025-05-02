package managers;

import common.Address;
import filemanager.txtFileManager;

public class AddressManager {
    private txtFileManager fm;

    // کانستراکتور: فایل منیجر رو به فایل address.txt وصل می‌کنه
    public AddressManager() {
        fm = new txtFileManager("address.txt");
    }

    // اضافه کردن آدرس جدید به فایل
    public void Insert(Address a) {
        fm.AppendRow(a.toString());
    }

    // ویرایش یک آدرس خاص (بر اساس شماره ردیف)
    public void Update(Address a, int row) {
        fm.UpdateRow(a.toString(), row);
    }

    // حذف آدرس با شماره ردیف مشخص
    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    // خوندن تمام آدرس‌ها از فایل و تبدیل به آرایه Address
    public Address[] SelectAll() {
        String[] rows = fm.GetArray(); // خوندن همه‌ی خطوط فایل
        Address[] addresses = new Address[rows.length];

        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";"); // جدا کردن فیلدها
                if (parts.length == 5) {
                    addresses[i] = new Address(
                            Integer.parseInt(parts[0]), // id
                            parts[1],                  // city
                            parts[2],                  // street
                            parts[3],                  // postalCode
                            parts[4]                   // details
                    );
                }
            }
        }
        return addresses; // برگردوندن همه آدرس‌ها
    }
}
