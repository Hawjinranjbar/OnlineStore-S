package managers;

import common.Cart;
import filemanager.txtFileManager;

public class CartManager {
    private txtFileManager fm;

    // سازنده: اتصال به فایل cart.txt
    public CartManager() {
        fm = new txtFileManager("cart.txt");
    }

    // اضافه کردن آیتم جدید به سبد خرید
    public void Insert(Cart c) {
        fm.AppendRow(c.toString());
    }

    // به‌روزرسانی اطلاعات یه آیتم خاص (مثلاً تغییر تعداد)
    public void Update(Cart c, int row) {
        fm.UpdateRow(c.toString(), row);
    }

    // حذف یه آیتم از سبد خرید (بر اساس شماره ردیف)
    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    // حذف کل سبد خرید
    public void ClearAll() {
        fm.Clear();
    }

    // خوندن کل آیتم‌های سبد خرید از فایل
    public Cart[] SelectAll() {
        String[] rows = fm.GetArray();
        Cart[] carts = new Cart[rows.length];

        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";"); // داده‌ها از رشته جدا می‌شن
                if (parts.length == 2) {
                    carts[i] = new Cart(
                            Integer.parseInt(parts[0]), // productId
                            Integer.parseInt(parts[1])  // quantity
                    );
                }
            }
        }

        return carts; // برگردوندن لیست سبد خرید
    }
}
