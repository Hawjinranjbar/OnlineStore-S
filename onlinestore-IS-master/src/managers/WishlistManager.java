

package managers;

import common.Wishlist;
import filemanager.txtFileManager;
import java.util.ArrayList;

public class WishlistManager {
    private txtFileManager fm;

    public WishlistManager() {
        fm = new txtFileManager("wishlist.txt");
    }

    public void Insert(Wishlist wishlist) {
        fm.AppendRow(wishlist.toString());
    }

    public Wishlist[] SelectAll() {
        String[] rows = fm.GetArray();
        if (rows == null || rows.length == 0) return new Wishlist[0];

        Wishlist[] result = new Wishlist[rows.length];
        for (int i = 0; i < rows.length; i++) {
            try {
                result[i] = Wishlist.fromString(rows[i]);
            } catch (Exception e) {
                result[i] = null;
            }
        }
        return result;
    }

    public boolean Exists(int customerId, int productId) {
        Wishlist[] all = SelectAll();
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && all[i].getCustomerId() == customerId && all[i].getProductId() == productId) {
                return true;
            }
        }
        return false;
    }

    public void Delete(int customerId, int productId) {
        String[] rows = fm.GetArray();
        ArrayList<String> updated = new ArrayList<String>();

        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null && !rows[i].trim().isEmpty()) {
                String[] parts = rows[i].split(";");
                if (parts.length >= 2) {
                    int cId = Integer.parseInt(parts[0]);
                    int pId = Integer.parseInt(parts[1]);
                    if (cId == customerId && pId == productId) {
                        continue; // حذفش کن
                    }
                }
                updated.add(rows[i]);
            }
        }

        fm.Clear();
        for (String line : updated) {
            fm.AppendRow(line);
        }
    }
}


