package managers;

import common.Order;
import filemanager.txtFileManager;

public class OrderManager {
    private txtFileManager fm;

    // 🔧 اتصال این کلاس به فایل order.txt
    public OrderManager() {
        fm = new txtFileManager("order.txt");
    }

    // ➕ ثبت یک سفارش جدید در فایل
    public void Insert(Order o) {
        fm.AppendRow(o.toString());  // تبدیل سفارش به متن و نوشتن در فایل
    }

    // 📥 خوندن تمام سفارش‌ها از فایل و تبدیل به آرایه از Order
    public Order[] SelectAll() {
        String[] rows = fm.GetArray();           // گرفتن تمام سطرها از فایل
        Order[] orders = new Order[rows.length]; // آرایه برای نگه‌داری سفارش‌ها

        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";"); // جدا کردن فیلدها
                if (parts.length == 7) {             // اطمینان از کامل بودن داده
                    orders[i] = new Order(
                            Integer.parseInt(parts[0]), // orderId
                            Integer.parseInt(parts[1]), // customerId
                            Integer.parseInt(parts[2]), // addressId
                            Double.parseDouble(parts[3]), // totalAmount
                            parts[4],  // discountCode
                            parts[5],  // cartItems
                            parts[6]   // orderDate
                    );
                }
            }
        }

        return orders; // خروجی: لیست سفارش‌های قابل استفاده در UI
    }
}

