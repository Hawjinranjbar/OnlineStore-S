package common;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product() {
    }

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // تبدیل به رشته برای ذخیره در فایل
    public String toString() {
        return id + ";" + name + ";" + price + ";" + stock;
    }

    // تبدیل از رشته به شی
    public static Product fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length < 4) return null;
        return new Product(
                Integer.parseInt(parts[0]),
                parts[1],
                Double.parseDouble(parts[2]),
                Integer.parseInt(parts[3])
        );
    }
}
