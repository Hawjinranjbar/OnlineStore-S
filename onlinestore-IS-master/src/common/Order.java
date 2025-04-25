package common;

public class Order {
    private int id;
    private String customerName;
    private String phoneNumber;
    private String productDetails; // مثلا: "Lipstick x2 - 500000, Cream x1 - 180000"
    private double totalPrice;

    public Order() {
    }

    public Order(int id, String customerName, String phoneNumber, String productDetails, double totalPrice) {
        this.id = id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.productDetails = productDetails;
        this.totalPrice = totalPrice;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // toString برای ذخیره در فایل
    @Override
    public String toString() {
        return id + ";" + customerName + ";" + phoneNumber + ";" + productDetails + ";" + totalPrice;
    }

    // تبدیل از رشته به شی Order
    public static Order fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid order format: " + line);
        }
        return new Order(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                Double.parseDouble(parts[4])
        );
    }
}
