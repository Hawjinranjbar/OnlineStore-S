package common;

public class Discount {
    private int discountId;
    private String discountCode;
    private int discountPercent;
    private String applicableCategory;
    private double minPurchaseAmount;
    private boolean isActive;
    
    public Discount(int discountId, String discountCode, int discountPercent, String applicableCategory, double minPurchaseAmount, boolean isActive) {
        this.discountId = discountId;
        this.discountCode = discountCode;
        this.discountPercent = discountPercent;
        this.applicableCategory = applicableCategory;
        this.minPurchaseAmount = minPurchaseAmount;
        this.isActive = isActive;
    }

    // Getters
    public int getDiscountId() { return discountId; }
    public String getDiscountCode() { return discountCode; }
    public int getDiscountPercent() { return discountPercent; }
    public String getApplicableCategory() { return applicableCategory; }
    public double getMinPurchaseAmount() { return minPurchaseAmount; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setDiscountId(int discountId) { this.discountId = discountId; }
    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }
    public void setApplicableCategory(String applicableCategory) { this.applicableCategory = applicableCategory; }
    public void setMinPurchaseAmount(double minPurchaseAmount) { this.minPurchaseAmount = minPurchaseAmount; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return discountId + ";" + discountCode + ";" + discountPercent + ";" + applicableCategory + ";" + minPurchaseAmount + ";" + isActive;
    }
}
