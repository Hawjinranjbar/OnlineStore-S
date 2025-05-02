

package managers;

import common.ItemFeatures;
import filemanager.txtFileManager;

public class ItemFeaturesManager {
    private txtFileManager fm;

    public ItemFeaturesManager() {
        fm = new txtFileManager("itemfeatures.txt");
    }

    public void SaveOrUpdate(ItemFeatures newItem) {
        ItemFeatures[] all = SelectAll();
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && all[i].getProductId() == newItem.getProductId()) {
                fm.UpdateRow(newItem.toString(), i);
                return;
            }
        }
        fm.AppendRow(newItem.toString());
    }

    public ItemFeatures[] SelectAll() {
        String[] rows = fm.GetArray();
        if (rows == null || rows.length == 0) return new ItemFeatures[0];

        ItemFeatures[] list = new ItemFeatures[rows.length];
        for (int i = 0; i < rows.length; i++) {
            try {
                list[i] = ItemFeatures.fromString(rows[i]);
            } catch (Exception e) {
                list[i] = null;
            }
        }
        return list;
    }

    public ItemFeatures SearchByProductId(int id) {
        ItemFeatures[] all = SelectAll();
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && all[i].getProductId() == id) {
                return all[i];
            }
        }
        return null;
    }
}


























//package managers;
//
//import common.ItemFeatures;
//import filemanager.txtFileManager;
//
//public class ItemFeaturesManager {
//    private txtFileManager fm;
//
//    public ItemFeaturesManager() {
//        fm = new txtFileManager("itemfeatures.txt");
//    }
//
//    public void Insert(ItemFeatures item) {
//        fm.AppendRow(item.toString());
//    }
//
//    public void Update(ItemFeatures item, int row) {
//        fm.UpdateRow(item.toString(), row);
//    }
//
//    public void Delete(int row) {
//        fm.DeleteRow(row);
//    }
//
//    public ItemFeatures[] SelectAll() {
//        String[] rows = fm.GetArray();
//        if (rows == null || rows.length == 0) return new ItemFeatures[0];
//
//        ItemFeatures[] list = new ItemFeatures[rows.length];
//        for (int i = 0; i < rows.length; i++) {
//            try {
//                list[i] = ItemFeatures.fromString(rows[i]);
//            } catch (Exception e) {
//                list[i] = null;
//            }
//        }
//        return list;
//    }
//
//    public ItemFeatures SearchByProductId(int productId) {
//        ItemFeatures[] all = SelectAll();
//        for (int i = 0; i < all.length; i++) {
//            if (all[i] != null && all[i].getProductId() == productId) {
//                return all[i];
//            }
//        }
//        return null;
//    }
//
//    public void InsertOrUpdate(ItemFeatures item) {
//        ItemFeatures[] all = SelectAll();
//        for (int i = 0; i < all.length; i++) {
//            if (all[i] != null && all[i].getProductId() == item.getProductId()) {
//                Update(item, i);
//                return;
//            }
//        }
//        Insert(item);
//    }
//}
//






//
//
//// =============================
//// File: managers/ItemFeaturesManager.java
//// =============================
//
//package managers;
//
//import common.ItemFeatures;
//import filemanager.txtFileManager;
//
//public class ItemFeaturesManager {
//    private txtFileManager fm;
//
//    public ItemFeaturesManager() {
//        fm = new txtFileManager("itemfeatures.txt");
//    }
//
//    public void Insert(ItemFeatures item) {
//        fm.AppendRow(item.toString());
//    }
//
//    public void Update(ItemFeatures item, int row) {
//        fm.UpdateRow(item.toString(), row);
//    }
//
//    public void Delete(int row) {
//        fm.DeleteRow(row);
//    }
//
//    public ItemFeatures[] SelectAll() {
//        String[] rows = fm.GetArray();
//        if (rows == null || rows.length == 0) return new ItemFeatures[0];
//
//        ItemFeatures[] list = new ItemFeatures[rows.length];
//        for (int i = 0; i < rows.length; i++) {
//            try {
//                list[i] = ItemFeatures.fromString(rows[i]);
//            } catch (Exception e) {
//                list[i] = null;
//            }
//        }
//        return list;
//    }
//
//    public ItemFeatures SearchByBrand(String brand) {
//        ItemFeatures[] all = SelectAll();
//        if (all == null) return null;
//
//        for (int i = 0; i < all.length; i++) {
//            if (all[i] != null && all[i].getBrand().equalsIgnoreCase(brand)) {
//                return all[i];
//            }
//        }
//        return null;
//    }
//}
