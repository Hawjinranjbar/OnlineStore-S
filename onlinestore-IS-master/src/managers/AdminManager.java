// === 2. MANAGER ===
package managers;

import common.Admin;
import filemanager.txtFileManager;

public class AdminManager {
    private txtFileManager fm;

    public AdminManager() {
        fm = new txtFileManager("admin.txt");
        ensureAdminFileExists();
    }

    private void ensureAdminFileExists() {
        // اگر فایل خالیه، یه ادمین پیش‌فرض اضافه کن
        String[] lines = fm.GetArray();
        if (lines == null || lines.length == 0 || lines[0].trim().isEmpty()) {
            fm.AppendRow("admin;1234");
        }
    }

    public Admin findByUsernameAndPassword(String username, String password) {
        String[] lines = fm.GetArray();
        if (lines == null) return null;

        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) continue;
            String[] parts = line.split(";");
            if (parts.length == 2) {
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return new Admin(username, password);
                }
            }
        }
        return null;
    }
}
