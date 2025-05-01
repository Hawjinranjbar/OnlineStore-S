package managers;

import common.Category;
import filemanager.txtFileManager;

public class CategoryManager {
    private txtFileManager fm;

    public CategoryManager() {
        fm = new txtFileManager("category.txt");
    }

    public void Insert(Category c) {
        fm.AppendRow(c.toString());
    }

    public void Update(Category c, int row) {
        fm.UpdateRow(c.toString(), row);
    }

    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    public Category[] SelectAll() {
        String[] rows = fm.GetArray();
        if (rows == null || rows.length == 0) {
            return new Category[0];
        }

        Category[] categories = new Category[rows.length];
        for (int i = 0; i < rows.length; i++) {
            try {
                if (rows[i] != null && !rows[i].trim().isEmpty()) {
                    String[] parts = rows[i].split(";");
                    if (parts.length == 5) {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String description = parts[2];
                        int parentId = Integer.parseInt(parts[3]);
                        String imageFileName = parts[4];

                        categories[i] = new Category(id, name, description, parentId, imageFileName);
                    }
                }
            } catch (Exception ex) {
                System.out.println("❌ Error reading category at row " + i + ": " + ex.getMessage());
                categories[i] = null;
            }
        }
        return categories;
    }
}
