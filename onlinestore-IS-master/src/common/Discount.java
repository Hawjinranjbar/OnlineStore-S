package common;

public class Discount {
    private String discountCode;
    private int discountPercent;
    private boolean isActive;

    public Discount(String discountCode, int discountPercent, boolean isActive) {
        this.discountCode = discountCode;
        this.discountPercent = discountPercent;
        this.isActive = isActive;
    }

    public String getDiscountCode() { return discountCode; }
    public int getDiscountPercent() { return discountPercent; }
    public boolean isActive() { return isActive; }

    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return discountCode + ";" + discountPercent + ";" + isActive;
    }
}
