


package common;

public class Inventory {
    private int productId;
    private String productName;
    private int quantity;
    private boolean isAddition; // true = ورود کالا | false = خروج کالا

    public Inventory(int productId, String productName, int quantity, boolean isAddition) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.isAddition = isAddition;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAddition() {
        return isAddition;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAddition(boolean isAddition) {
        this.isAddition = isAddition;
    }

    @Override
    public String toString() {
        return productId + ";" + productName + ";" + quantity + ";" + isAddition;
    }

    public static Inventory fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length != 4) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int qty = Integer.parseInt(parts[2]);
            boolean isAdd = Boolean.parseBoolean(parts[3]);
            return new Inventory(id, name, qty, isAdd);
        } catch (Exception e) {
            return null;
        }
    }
}


