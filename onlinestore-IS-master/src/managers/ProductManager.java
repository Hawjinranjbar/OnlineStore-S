

package managers;

import common.Product;
import filemanager.txtFileManager;

public class ProductManager {
    private txtFileManager fm;
    public static void main(String[] args) {
        ProductManager pm = new ProductManager();
        Product[] products = pm.SelectAll();
        for (Product p : products) {
            if (p != null) System.out.println(p.getName());
        }
    }

    public ProductManager() {
        fm = new txtFileManager("product.txt");
    }

    public void Insert(Product p) {
        fm.AppendRow(p.toString());
    }

    public void Update(Product p, int row) {
        fm.UpdateRow(p.toString(), row);
    }

    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    public Product[] SelectAll() {
        String[] rows = fm.GetArray();
        if (rows == null || rows.length == 0) {
            return new Product[0]; // اگه فایلی نباشه یا خالی باشه
        }

        Product[] products = new Product[rows.length];
        for (int i = 0; i < rows.length; i++) {
            try {
                if (rows[i] != null && !rows[i].trim().isEmpty()) {
                    String[] parts = rows[i].split(";");
                    if (parts.length == 10) {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String brand = parts[2];
                        String description = parts[3];
                        double price = Double.parseDouble(parts[4]);
                        int stock = Integer.parseInt(parts[5]);
                        String category = parts[6];
                        String skinType = parts[7];
                        boolean isOrganic = Boolean.parseBoolean(parts[8]);
                        String imageFileName = parts[9];

                        products[i] = new Product(id, name, brand, description, price, stock, category, skinType, isOrganic, imageFileName);
                    }
                }
            } catch (Exception ex) {
                // اگر خطایی تو پارسینگ بود، اون محصول رد میشه
                System.out.println("❌ Error reading product at row " + i + ": " + ex.getMessage());
                products[i] = null;
            }
        }
        return products;
    }
}
