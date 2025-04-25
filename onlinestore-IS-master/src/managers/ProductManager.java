package managers;

import filemanager.txtFileManager;
import common.Product;

public class ProductManager {
    txtFileManager fm;
    String fileName = "Product.txt";

    public ProductManager() {
        fm = new txtFileManager(fileName);
    }

    // افزودن محصول جدید
    public void Insert(Product p) {
        fm.AppendRow(p.toString());
    }

    // دریافت همه محصولات
    public Product[] SelectAll() {
        String[] rows = fm.GetArray();
        Product[] products = new Product[rows.length];
        for (int i = 0; i < rows.length; i++) {
            products[i] = Product.fromString(rows[i]);
        }
        return products;
    }

    // گرفتن محصول خاص با ایندکس (Primary Key فرضی)
    public Product SelectByPK(int rowNumber) {
        String row = fm.GetRow(rowNumber);
        if (row != null)
            return Product.fromString(row);
        return null;
    }

    // گرفتن تعداد کل محصولات
    public int SelectCount() {
        return fm.SelectCount();
    }

    // حذف یک محصول بر اساس شماره سطر
    public void Delete(int rowNumber) {
        fm.DeleteRow(rowNumber);
    }

    // ویرایش محصول
    public void Update(Product p, int rowNumber) {
        fm.UpdateRow(p.toString(), rowNumber);
    }

    // درج محصول در یک سطر خاص
    public void InsertAt(Product p, int rowNumber) {
        fm.InsertRow(p.toString(), rowNumber);
    }

    // پاک‌سازی کل فایل محصولات
    public void ClearAll() {
        fm.Clear();
    }
}
