package common;

public class Order {
    private int orderId;
    private int customerId;
    private int addressId;
    private double totalAmount;
    private String discountCode;
    private String cartItems; // مثلا: 2xLipstick, 1xCream
    private String orderDate;

    public Order(int orderId, int customerId, int addressId, double totalAmount, String discountCode, String cartItems, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.addressId = addressId;
        this.totalAmount = totalAmount;
        this.discountCode = discountCode;
        this.cartItems = cartItems;
        this.orderDate = orderDate;
    }

    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public int getAddressId() { return addressId; }
    public double getTotalAmount() { return totalAmount; }
    public String getDiscountCode() { return discountCode; }
    public String getCartItems() { return cartItems; }
    public String getOrderDate() { return orderDate; }

    public String toString() {
        return orderId + ";" + customerId + ";" + addressId + ";" + totalAmount + ";" +
                discountCode + ";" + cartItems + ";" + orderDate;
    }
}
