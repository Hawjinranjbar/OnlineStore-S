package managers;

import common.Review; // Import Review class
import fileManager.txtFileManager; // Import file manager
import java.io.File; // File handling

// Manager class to manage file operations for Review
public class ReviewManager {

    private static final String FILE_NAME = "Review.txt"; // Name of file

    // Static block to make sure file and folder exist
    static {
        File folder = new File("myFiles");
        if (!folder.exists()) {
            folder.mkdirs(); // Create "myFiles" folder if missing
        }

        File file = new File("myFiles/" + FILE_NAME);
        if (!file.exists()) {
            txtFileManager fm = new txtFileManager(FILE_NAME);
            fm.CreateFile(); // Create Review.txt if missing
        }
    }

    // Add a new Review to file
    public static void addReview(Review review) {
        txtFileManager fm = new txtFileManager(FILE_NAME);
        fm.AppendRow(review.getProductId() + ";" + review.getCustomerName() + ";" + review.getRating() + ";" + review.getComment());
    }

    // Get all Reviews from file
    public static Review[] getAllReviews() {
        txtFileManager fm = new txtFileManager(FILE_NAME);
        String[] lines = fm.GetArray();
        Review[] reviews = new Review[lines.length];
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(";");
            if (parts.length == 4) {
                try {
                    reviews[i] = new Review(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]), parts[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Show error if data wrong
                }
            }
        }
        return reviews; // Return all loaded reviews
    }

    // Delete a Review by row index
    public static void deleteReview(int rowIndex) {
        txtFileManager fm = new txtFileManager(FILE_NAME);
        fm.DeleteRow(rowIndex);
    }

    // Update a Review at specific row
    public static void updateReview(Review review, int rowIndex) {
        txtFileManager fm = new txtFileManager(FILE_NAME);
        fm.UpdateRow(review.getProductId() + ";" + review.getCustomerName() + ";" + review.getRating() + ";" + review.getComment(), rowIndex);
    }

    // Get a single Review by row index
    public static Review getReview(int rowIndex) {
        txtFileManager fm = new txtFileManager(FILE_NAME);
        String line = fm.GetRow(rowIndex);
        if (line != null && !line.trim().isEmpty()) {
            String[] parts = line.split(";");
            if (parts.length == 4) {
                try {
                    return new Review(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]), parts[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}