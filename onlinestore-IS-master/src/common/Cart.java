package common;

public class Cart {
    // آیدی محصول و تعدادش توی سبد خرید
    private int productId;
    private int quantity;

    // سازنده: مقداردهی اولیه موقع ساختن آیتم سبد خرید
    public Cart(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // getterها برای گرفتن مقادیر
    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    // setterها برای تغییر مقادیر بعداً
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // تبدیل به رشته برای ذخیره در فایل (مثلاً "102;2")
    @Override
    public String toString() {
        return productId + ";" + quantity;
    }
}
