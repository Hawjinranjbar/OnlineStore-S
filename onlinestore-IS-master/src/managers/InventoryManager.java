package managers;

import common.Inventory;
import filemanager.txtFileManager;

import java.io.File;

public class InventoryManager {
    private txtFileManager fm;

    public InventoryManager() {
        // مطمئن می‌شویم فایل داخل پوشه myFiles وجود دارد
        File file = new File("myFiles/inventory.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("📁 Created missing inventory.txt file.");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to create inventory.txt: " + e.getMessage());
        }

        fm = new txtFileManager("inventory.txt"); // این به صورت پیش‌فرض می‌ره داخل myFiles/
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
