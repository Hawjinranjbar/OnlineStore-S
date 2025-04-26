package managers;

import common.User;
import fileManager.txtFileManager;
import java.io.File;

public class UserManager {

    private static final String FILE_NAME = "User.txt";

    static {
        // Ensure the "myFiles" folder exists
        File folder = new File("myFiles");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Auto-create User.txt if it does not exist
        File file = new File("myFiles/" + FILE_NAME);
        if (!file.exists()) {
            txtFileManager fileManager = new txtFileManager(FILE_NAME);
            fileManager.CreateFile();
        }
    }

    public static void addUser(User user) {
        txtFileManager fileManager = new txtFileManager(FILE_NAME);
        fileManager.AppendRow(user.getId() + ";" + user.getUsername() + ";" + user.getPassword() + ";" + user.getRole());
    }

    public static User[] getAllUsers() {
        txtFileManager fileManager = new txtFileManager(FILE_NAME);
        String[] lines = fileManager.GetArray();
        User[] users = new User[lines.length];
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(";");
            if (parts.length == 4) {
                try {
                    users[i] = new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    public static void deleteUser(int rowIndex) {
        txtFileManager fileManager = new txtFileManager(FILE_NAME);
        fileManager.DeleteRow(rowIndex);
    }

    public static void updateUser(User user, int rowIndex) {
        txtFileManager fileManager = new txtFileManager(FILE_NAME);
        fileManager.UpdateRow(user.getId() + ";" + user.getUsername() + ";" + user.getPassword() + ";" + user.getRole(), rowIndex);
    }

    public static User getUser(int rowIndex) {
        txtFileManager fileManager = new txtFileManager(FILE_NAME);
        String line = fileManager.GetRow(rowIndex);
        if (line != null && !line.trim().isEmpty()) {
            String[] parts = line.split(";");
            if (parts.length == 4) {
                try {
                    return new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static User findUserByUsernameAndPassword(String username, String password) {
        User[] users = getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null &&
                    users[i].getUsername().equals(username) &&
                    users[i].getPassword().equals(password)) {
                return users[i];
            }
        }
        return null;
    }
}
