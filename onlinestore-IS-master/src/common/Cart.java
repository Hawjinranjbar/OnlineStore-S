package common;

public class Cart {
    private int productId;
    private int quantity;

    public Cart(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return productId + ";" + quantity;
    }
}
