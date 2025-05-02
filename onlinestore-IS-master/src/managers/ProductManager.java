package managers;

import common.Product;
import filemanager.txtFileManager;

public class ProductManager {
    private txtFileManager fm;

    public ProductManager() {
        fm = new txtFileManager("product.txt");
    }

    public void Insert(Product p) {
        fm.AppendRow(p.toString()); // ذخیره محصول جدید در فایل
    }

    public void Update(Product p, int row) {
        fm.UpdateRow(p.toString(), row); // جایگزینی محصول جدید با ردیف مشخص
    }

    public void Delete(int row) {
        fm.DeleteRow(row); // حذف ردیف موردنظر
    }

    public Product[] SelectAll() {
        String[] rows = fm.GetArray(); // گرفتن کل خط‌های فایل

        if (rows == null || rows.length == 0)
            return new Product[0]; // اگه فایل خالیه، هیچی برنگردون

        Product[] products = new Product[rows.length];

        for (int i = 0; i < rows.length; i++) {
            try {
                if (rows[i] != null && !rows[i].trim().isEmpty()) {
                    String[] parts = rows[i].split(";"); // جدا کردن فیلدها با ;

                    if (parts.length == 10) {
                        // ساختن محصول از روی فیلدها
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String brand = parts[2];
                        String desc = parts[3];
                        double price = Double.parseDouble(parts[4]);
                        int stock = Integer.parseInt(parts[5]);
                        String cat = parts[6];
                        String skin = parts[7];
                        boolean organic = Boolean.parseBoolean(parts[8]);
                        String img = parts[9];

                        products[i] = new Product(id, name, brand, desc, price, stock, cat, skin, organic, img);
                    }
                }
            } catch (Exception ex) {
                System.out.println("❌ خطا در ردیف " + i + ": " + ex.getMessage());
                products[i] = null;
            }
        }

        return products; // لیست محصولات کامل
    }

    public static void main(String[] args) {
        ProductManager pm = new ProductManager();
        Product[] products = pm.SelectAll();

        for (Product p : products) {
            if (p != null) System.out.println(p.getName()); // چاپ اسم‌ها
        }
    }
}
