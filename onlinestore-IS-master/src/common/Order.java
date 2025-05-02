package common;

public class Order {

    // 🧾 فیلدهای اصلی سفارش که توی فایل ذخیره می‌شن:
    private int orderId;           // آیدی سفارش
    private int customerId;        // آیدی مشتری
    private int addressId;         // آیدی آدرسی که سفارش به اون می‌ره
    private double totalAmount;    // مبلغ کل سفارش
    private String discountCode;   // کد تخفیف استفاده شده
    private String cartItems;      // لیست محصولات سفارش به صورت متن
    private String orderDate;      // تاریخ سفارش

    // 🔍 فیلدهای اضافه فقط برای نمایش داخل UI، ذخیره نمی‌شن!
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    private String city;
    private String street;
    private String postalCode;
    private String details;

    // ✅ سازنده اصلی با فیلدهایی که داخل فایل ذخیره می‌شن
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

    // 🟢 Getter و Setter برای فیلدهای اصلی
    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public int getAddressId() { return addressId; }
    public double getTotalAmount() { return totalAmount; }
    public String getDiscountCode() { return discountCode; }
    public String getCartItems() { return cartItems; }
    public String getOrderDate() { return orderDate; }

    // 🟠 اطلاعات نمایشی مشتری (برای نمایش توی فرم)
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    // 🟡 اطلاعات نمایشی آدرس سفارش
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}

