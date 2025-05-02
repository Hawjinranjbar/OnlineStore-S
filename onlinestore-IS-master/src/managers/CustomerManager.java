package managers;

import common.Customer;
import filemanager.txtFileManager;

public class CustomerManager {
    private txtFileManager fm;

    // اتصال به فایل customer.txt موقع ساخت کلاس
    public CustomerManager() {
        fm = new txtFileManager("customer.txt");
    }

    // اضافه کردن مشتری جدید
    public void Insert(Customer c) {
        fm.AppendRow(c.toString());
    }

    // ویرایش اطلاعات مشتری در یک ردیف خاص
    public void Update(Customer c, int row) {
        fm.UpdateRow(c.toString(), row);
    }

    // حذف مشتری با شماره ردیف
    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    // خوندن همه مشتری‌ها از فایل و ساخت آرایه از Customer
    public Customer[] SelectAll() {
        String[] rows = fm.GetArray();
        Customer[] customers = new Customer[rows.length];

        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";"); // جدا کردن اطلاعات با ;
                if (parts.length == 5) {
                    customers[i] = new Customer(
                            Integer.parseInt(parts[0]), // id
                            parts[1],                   // name
                            parts[2],                   // phone
                            parts[3],                   // email
                            parts[4]                    // password
                    );
                }
            }
        }

        return customers;
    }

    // جستجو با شماره تلفن (برای چک کردن ثبت‌نام یا یکتا بودن)
    public Customer findByPhone(String phone) {
        Customer[] customers = SelectAll();
        for (Customer c : customers) {
            if (c != null && c.getPhone().equals(phone)) {
                return c; // مشتری پیدا شد
            }
        }
        return null; // پیدا نشد
    }

    // جستجو با ایمیل و رمز عبور (برای لاگین)
    public Customer findByEmailAndPassword(String email, String password) {
        Customer[] customers = SelectAll();
        for (Customer c : customers) {
            if (c != null && c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null; // اگه چیزی پیدا نشد
    }
}

