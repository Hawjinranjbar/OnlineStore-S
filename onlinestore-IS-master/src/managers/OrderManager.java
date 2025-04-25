package managers;

import common.Order;
import filemanager.txtFileManager;

public class OrderManager {
    private txtFileManager fm;
    private String fileName = "Order.txt";

    public OrderManager() {
        fm = new txtFileManager(fileName);
    }

    // ➕ ثبت سفارش
    public void Insert(Order o) {
        fm.AppendRow(o.toString());
    }

    // 📋 نمایش همه سفارش‌ها (برای مدیر)
    public Order[] SelectAll() {
        String[] rows = fm.GetArray();
        Order[] orders = new Order[rows.length];
        for (int i = 0; i < rows.length; i++) {
            orders[i] = Order.fromString(rows[i]);
        }
        return orders;
    }

    // 📌 گرفتن تعداد سفارش‌ها
    public int SelectCount() {
        return fm.SelectCount();
    }
}
