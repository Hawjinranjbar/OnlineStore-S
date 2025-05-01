package managers;

import common.Cart;
import filemanager.txtFileManager;

public class CartManager {
    private txtFileManager fm;

    public CartManager() {
        fm = new txtFileManager("cart.txt");
    }

    public void Insert(Cart c) {
        fm.AppendRow(c.toString());
    }

    public void Update(Cart c, int row) {
        fm.UpdateRow(c.toString(), row);
    }

    public void Delete(int row) {
        fm.DeleteRow(row);
    }
    public void ClearAll() {
        fm.Clear();
    }

    public Cart[] SelectAll() {
        String[] rows = fm.GetArray();
        Cart[] carts = new Cart[rows.length];
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";");
                if (parts.length == 2) {
                    carts[i] = new Cart(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1])
                    );
                }
            }
        }
        return carts;
    }
}
