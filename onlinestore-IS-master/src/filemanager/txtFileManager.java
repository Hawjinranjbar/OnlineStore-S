package filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class txtFileManager {
    private String FileName;

    public txtFileManager(String FileName) {
        this.FileName = "myFiles/" + FileName; // پوشه پیش‌فرض
    }

    // ساخت فایل جدید
    public void CreateFile() {
        try {
            PrintWriter out = new PrintWriter(this.FileName);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // پاک کردن محتویات فایل
    public void Clear() {
        CreateFile(); // همون متد بالا فایل رو خالی می‌کنه
    }

    // اضافه کردن سطر به آخر فایل
    public void AppendRow(String NewRow) {
        try {
            FileWriter fw = new FileWriter(this.FileName, true);
            fw.write(NewRow + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // خواندن همه سطرها
    public String[] GetArray() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(this.FileName));
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[0]);
    }

    // شمارش تعداد سطرها
    public int SelectCount() {
        return GetArray().length;
    }

    // گرفتن یک سطر خاص
    public String GetRow(int RowNumber) {
        String[] lines = GetArray();
        if (RowNumber >= 0 && RowNumber < lines.length) {
            return lines[RowNumber];
        }
        return null;
    }

    // حذف یک سطر خاص
    public void DeleteRow(int RowNumber) {
        ArrayList<String> list = new ArrayList<>(ListFromArray());
        if (RowNumber >= 0 && RowNumber < list.size()) {
            list.remove(RowNumber);
            SaveListToFile(list);
        }
    }

    // ویرایش سطر خاص
    public void UpdateRow(String NewRow, int RowNumber) {
        ArrayList<String> list = new ArrayList<>(ListFromArray());
        if (RowNumber >= 0 && RowNumber < list.size()) {
            list.set(RowNumber, NewRow);
            SaveListToFile(list);
        }
    }

    // درج در سطر خاص
    public void InsertRow(String NewRow, int RowNumber) {
        ArrayList<String> list = new ArrayList<>(ListFromArray());
        if (RowNumber >= 0 && RowNumber <= list.size()) {
            list.add(RowNumber, NewRow);
            SaveListToFile(list);
        }
    }

    // کمک‌کننده: تبدیل آرایه به لیست
    private ArrayList<String> ListFromArray() {
        String[] arr = GetArray();
        ArrayList<String> list = new ArrayList<>();
        for (String s : arr) {
            list.add(s);
        }
        return list;
    }

    // کمک‌کننده: ذخیره کل لیست در فایل
    private void SaveListToFile(ArrayList<String> list) {
        try {
            PrintWriter pw = new PrintWriter(this.FileName);
            for (String s : list) {
                pw.println(s);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
