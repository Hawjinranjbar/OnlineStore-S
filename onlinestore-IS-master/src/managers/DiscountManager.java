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
        if (rows == null || rows.length == 0) {
            return new Discount[0];
        }

        Discount[] discounts = new Discount[rows.length];
        for (int i = 0; i < rows.length; i++) {
            try {
                if (rows[i] != null && !rows[i].trim().isEmpty()) {
                    String[] parts = rows[i].split(";");
                    if (parts.length == 6) {
                        discounts[i] = new Discount(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                Integer.parseInt(parts[2]),
                                parts[3],
                                Double.parseDouble(parts[4]),
                                Boolean.parseBoolean(parts[5])
                        );
                    }
                }
            } catch (Exception ex) {
                System.out.println("❌ Error reading discount at row " + i + ": " + ex.getMessage());
                discounts[i] = null;
            }
        }
        return discounts;
    }

    public Discount findByCode(String code) {
        Discount[] discounts = SelectAll();
        for (Discount d : discounts) {
            if (d != null && d.getDiscountCode().equalsIgnoreCase(code) && d.isActive()) {
                return d;
            }
        }
        return null;
    }
}
