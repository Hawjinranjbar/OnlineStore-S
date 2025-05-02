package managers;

import common.Discount;
import filemanager.txtFileManager;

public class DiscountManager {
    private txtFileManager fm;

    // موقع ساخت، فایل discount.txt رو بهش وصل می‌کنیم
    public DiscountManager() {
        fm = new txtFileManager("discount.txt");
    }

    // اضافه کردن تخفیف جدید به فایل
    public void Insert(Discount d) {
        fm.AppendRow(d.toString());
    }

    // بروزرسانی تخفیف موجود (با شماره ردیف)
    public void Update(Discount d, int row) {
        fm.UpdateRow(d.toString(), row);
    }

    // حذف تخفیف از فایل
    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    // گرفتن همه تخفیف‌ها از فایل و تبدیلشون به آبجکت
    public Discount[] SelectAll() {
        String[] rows = fm.GetArray(); // خوندن کل فایل
        Discount[] discounts = new Discount[rows.length]; // ساخت آرایه تخفیف

        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";"); // جدا کردن فیلدها با ;
                if (parts.length == 3) {
                    discounts[i] = new Discount(
                            parts[0], // کد تخفیف
                            Integer.parseInt(parts[1]), // درصد تخفیف
                            Boolean.parseBoolean(parts[2]) // فعال یا نه
                    );
                }
            }
        }
        return discounts;
    }
}

