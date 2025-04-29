package managers;

import common.Discount;
import filemanager.txtFileManager;

public class DiscountManager {
    private txtFileManager fm;

    public DiscountManager() {
        fm = new txtFileManager("discount.txt");
    }

    public void Insert(Discount d) {
        fm.AppendRow(d.toString());
    }

    public void Update(Discount d, int row) {
        fm.UpdateRow(d.toString(), row);
    }

    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    public Discount[] SelectAll() {
        String[] rows = fm.GetArray();
        Discount[] discounts = new Discount[rows.length];
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";");
                if (parts.length == 3) {
                    discounts[i] = new Discount(
                            parts[0],
                            Integer.parseInt(parts[1]),
                            Boolean.parseBoolean(parts[2])
                    );
                }
            }
        }
        return discounts;
    }
}
