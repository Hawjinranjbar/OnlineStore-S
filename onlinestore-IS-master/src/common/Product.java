package common;

public class Product {
    private int id;
    private String name;
    private String brand;
    private String description;
    private double price;
    private int stock;
    private String category;
    private String skinType;
    private boolean isOrganic;
    private String imageFileName; // تغییر نام متغیر

    // Constructor
    public Product(int id, String name, String brand, String description, double price, int stock, String category, String skinType, boolean isOrganic, String imageFileName) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.skinType = skinType;
        this.isOrganic = isOrganic;
        this.imageFileName = imageFileName;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getCategory() { return category; }
    public String getSkinType() { return skinType; }
    public boolean isOrganic() { return isOrganic; }
    public String getImageFileName() { return imageFileName; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setCategory(String category) { this.category = category; }
    public void setSkinType(String skinType) { this.skinType = skinType; }
    public void setOrganic(boolean isOrganic) { this.isOrganic = isOrganic; }
    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }

    // متد برای تبدیل به فایل
    @Override
    public String toString() {
        return id + ";" + name + ";" + brand + ";" + description + ";" + price + ";" + stock + ";" + category + ";" + skinType + ";" + isOrganic + ";" + imageFileName;
    }

    // متد اضافه برای راحت لود کردن عکس
    public String getImagePath() {
        return "images/" + imageFileName;
    }
}
