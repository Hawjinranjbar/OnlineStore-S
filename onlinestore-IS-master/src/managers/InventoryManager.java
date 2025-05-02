



package managers;

import common.Inventory;
import filemanager.txtFileManager;

public class InventoryManager {
    private txtFileManager fm;

    public InventoryManager() {
        fm = new txtFileManager("inventory.txt");
    }

    public void Insert(Inventory inv) {
        fm.AppendRow(inv.toString());
    }

    public Inventory[] SelectAll() {
        String[] rows = fm.GetArray();
        if (rows == null || rows.length == 0) return new Inventory[0];

        Inventory[] list = new Inventory[rows.length];
        for (int i = 0; i < rows.length; i++) {
            try {
                list[i] = Inventory.fromString(rows[i]);
            } catch (Exception e) {
                list[i] = null;
            }
        }
        return list;
    }
}

