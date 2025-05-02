// ===========================
// File: common/Review.java
// ===========================

package common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Review {
    private int productId;
    private String customerName;
    private int rating;
    private String comment;
    private String date;

    public Review(int productId, String customerName, int rating, String comment, String date) {
        this.productId = productId;
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getProductId() { return productId; }
    public String getCustomerName() { return customerName; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getDate() { return date; }

    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return productId + ";" + customerName + ";" + rating + ";" + comment + ";" + date;
    }

    public static Review fromString(String row) {
        String[] parts = row.split(";");
        if (parts.length != 5) throw new IllegalArgumentException("Invalid review row");
        int productId = Integer.parseInt(parts[0]);
        String name = parts[1];
        int rating = Integer.parseInt(parts[2]);
        String comment = parts[3];
        String date = parts[4];
        return new Review(productId, name, rating, comment, date);
    }

    // Helper method to create review with today’s date
    public static Review create(int productId, String customerName, int rating, String comment) {
        String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        return new Review(productId, customerName, rating, comment, date);
    }
}
