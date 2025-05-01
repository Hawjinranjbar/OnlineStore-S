package common;

public class Order {
    private int orderId;
    private int customerId;
    private int addressId;
    private double totalAmount;
    private String discountCode;
    private String cartItems;
    private String orderDate;

    // 🔽 فیلدهای اضافی برای نمایش (نه برای ذخیره در فایل)
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    private String city;
    private String street;
    private String postalCode;
    private String details;

    public Order(int orderId, int customerId, int addressId, double totalAmount,
                 String discountCode, String cartItems, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.addressId = addressId;
        this.totalAmount = totalAmount;
        this.discountCode = discountCode;
        this.cartItems = cartItems;
        this.orderDate = orderDate;
    }

    // 🟢 Getter و Setter های اصلی
    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public int getAddressId() { return addressId; }
    public double getTotalAmount() { return totalAmount; }
    public String getDiscountCode() { return discountCode; }
    public String getCartItems() { return cartItems; }
    public String getOrderDate() { return orderDate; }

    // 🟠 Getter و Setter برای اطلاعات مشتری
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    // 🟡 Getter و Setter برای اطلاعات آدرس
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
