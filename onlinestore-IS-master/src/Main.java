import common.Product;
import managers.ProductManager;

public class Main {
    public static void main(String[] args) {
        // ساخت منیجر محصول
        ProductManager pm = new ProductManager();

        // خواندن کل لیست محصولات
        Product[] products = pm.SelectAll();

        // چاپ لیست محصولات
        System.out.println("📋 Product List:");
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null) {
                System.out.println((i + 1) + ". " + products[i].toString());
            }
        }
    }
}
