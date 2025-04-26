package common; // Package location

// Review class stores review details
public class Review {
    private int productId; // ID of the product being reviewed
    private String customerName; // Name of the customer writing the review
    private int rating; // Rating (out of 5)
    private String comment; // Comment text

    public Review() {} // Empty constructor

    // Constructor with all fields
    public Review(int productId, String customerName, int rating, String comment) {
        this.productId = productId;
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters (simple read/write functions)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}