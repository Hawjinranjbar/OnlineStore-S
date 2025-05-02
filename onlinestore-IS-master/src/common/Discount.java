package common;

public class Discount {
    // کد تخفیف، درصد تخفیف، فعال یا غیرفعال بودن
    private String discountCode;
    private int discountPercent;
    private boolean isActive;

    // کانستراکتور برای ساختن آبجکت تخفیف
    public Discount(String discountCode, int discountPercent, boolean isActive) {
        this.discountCode = discountCode;
        this.discountPercent = discountPercent;
        this.isActive = isActive;
    }

    // Getterها برای گرفتن اطلاعات
    public String getDiscountCode() { return discountCode; }
    public int getDiscountPercent() { return discountPercent; }
    public boolean isActive() { return isActive; }

    // Setterها برای تغییر اطلاعات
    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }
    public void setActive(boolean active) { isActive = active; }

    // تبدیل تخفیف به فرمت قابل ذخیره در فایل (مثلاً تو discount.txt)
    @Override
    public String toString() {
        return discountCode + ";" + discountPercent + ";" + isActive;
    }
}
