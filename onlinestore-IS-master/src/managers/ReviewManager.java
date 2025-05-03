
package managers;

import common.Review;
import filemanager.txtFileManager;

import java.util.ArrayList;

public class ReviewManager {
    private txtFileManager fm;

    public ReviewManager() {
        fm = new txtFileManager("review.txt");
    }

    public void Insert(Review r) {
        fm.AppendRow(r.toString());
    }

    public Review[] GetByProductId(int productId) {
        String[] rows = fm.GetArray();
        if (rows == null || rows.length == 0) return new Review[0];

        ArrayList<Review> result = new ArrayList<Review>();

        for (int i = 0; i < rows.length; i++) {
            try {
                Review r = Review.fromString(rows[i]);
                if (r != null && r.getProductId() == productId) {
                    result.add(r);
                }
            } catch (Exception ex) {
                System.out.println("❌ Error reading review at row " + i + ": " + ex.getMessage());
            }
        }

        return result.toArray(new Review[0]);
    }

    // Optional: all reviews
    public Review[] SelectAll() {
        String[] rows = fm.GetArray();
        if (rows == null || rows.length == 0) return new Review[0];

        Review[] reviews = new Review[rows.length];
        for (int i = 0; i < rows.length; i++) {
            try {
                reviews[i] = Review.fromString(rows[i]);
            } catch (Exception e) {
                reviews[i] = null;
            }
        }
        return reviews;
    }
}

