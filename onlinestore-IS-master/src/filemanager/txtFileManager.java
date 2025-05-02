package filemanager;

import java.io.*;

public class txtFileManager {
    private String FileName; // مسیر فایل

    // کانستراکتور: مسیر فایل رو مشخص می‌کنه
    public txtFileManager(String FileName) {
        this.FileName = "myFiles/" + FileName;
    }

    // ساخت فایل (یا پاک کردن محتوای قبلی)
    public void CreateFile() {
        try {
            PrintWriter out = new PrintWriter(this.FileName);
            out.close(); // فقط باز و بلافاصله بسته میشه، یعنی فایل ساخته یا خالی میشه
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // پاک کردن کامل فایل (همون متد بالا رو صدا می‌زنه)
    public void Clear() {
        CreateFile();
    }

    // اضافه کردن یک خط جدید به انتهای فایل
    public void AppendRow(String NewRow) {
        try {
            FileWriter fw = new FileWriter(this.FileName, true); // true یعنی append
            fw.write(NewRow + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // خوندن کل فایل و برگردوندن به صورت آرایه‌ای از خط‌ها
    public String[] GetArray() {
        try {
            // اول بشمار چندتا خط داریم
            BufferedReader br = new BufferedReader(new FileReader(this.FileName));
            int count = 0;
            while (br.readLine() != null) count++;
            br.close();

            // حالا همونا رو بخون و بریز تو آرایه
            String[] result = new String[count];
            BufferedReader br2 = new BufferedReader(new FileReader(this.FileName));
            for (int i = 0; i < count; i++) {
                result[i] = br2.readLine();
            }
            br2.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0]; // اگر ارور خورد، آرایه خالی برگردون
        }
    }

    // تعداد کل خط‌ها (همون تعداد رکوردها)
    public int SelectCount() {
        return GetArray().length;
    }

    // گرفتن یه خط خاص با شماره ردیف مشخص
    public String GetRow(int RowNumber) {
        String[] lines = GetArray();
        if (RowNumber >= 0 && RowNumber < lines.length) {
            return lines[RowNumber];
        }
        return null;
    }

    // حذف یک خط خاص از فایل
    public void DeleteRow(int RowNumber) {
        String[] lines = GetArray();
        try {
            PrintWriter pw = new PrintWriter(this.FileName);
            for (int i = 0; i < lines.length; i++) {
                if (i != RowNumber) {
                    pw.println(lines[i]); // همه رو بنویس بجز اون ردیفی که باید حذف بشه
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // جایگزینی یک خط خاص با خط جدید
    public void UpdateRow(String NewRow, int RowNumber) {
        String[] lines = GetArray();
        try {
            PrintWriter pw = new PrintWriter(this.FileName);
            for (int i = 0; i < lines.length; i++) {
                if (i == RowNumber) {
                    pw.println(NewRow); // خط جدید جایگزین
                } else {
                    pw.println(lines[i]);
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // درج یک خط جدید در یک ردیف خاص و بقیه رو بعدش بنویسه
    public void InsertRow(String NewRow, int RowNumber) {
        String[] lines = GetArray();
        try {
            PrintWriter pw = new PrintWriter(this.FileName);
            for (int i = 0; i <= lines.length; i++) {
                if (i == RowNumber) {
                    pw.println(NewRow); // درج در مکان مورد نظر
                }
                if (i < lines.length) {
                    pw.println(lines[i]); // نوشتن بقیه خط‌ها
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

