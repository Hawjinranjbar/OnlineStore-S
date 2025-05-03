
package common;

public class Wishlist {
    private int customerId;
    private int productId;

    public Wishlist(int customerId, int productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getProductId() {
        return productId;
    }

    public String toString() {
        return customerId + ";" + productId;
    }

    public static Wishlist fromString(String row) {
        String[] parts = row.split(";");
        int customerId = Integer.parseInt(parts[0]);
        int productId = Integer.parseInt(parts[1]);
        return new Wishlist(customerId, productId);
    }
}
