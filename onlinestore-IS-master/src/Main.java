import common.Product;
import managers.ProductManager;


public class Main {
    public static void main(String[] args) {
        // ساخت محصول نمونه
        Product p = new Product(1, "Mouse Logitech", 250000, 20);

        // ساخت منیجر و ثبت محصول
        ProductManager pm = new ProductManager();
        pm.Insert(p);

        // خواندن کل لیست محصول‌ها
        Product[] products = pm.SelectAll();

        System.out.println("📦 لیست محصولات:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i].toString());
        }
    }
}
