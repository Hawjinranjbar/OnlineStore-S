package filemanager;

import java.io.*;
import java.util.Scanner;

public class txtFileManager {
    private String FileName;

    public txtFileManager(String FileName) {
        this.FileName = "myFiles/" + FileName;
    }

    public void CreateFile() {
        try {
            PrintWriter out = new PrintWriter(this.FileName);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void Clear() {
        CreateFile();
    }

    public void AppendRow(String NewRow) {
        try {
            FileWriter fw = new FileWriter(this.FileName, true);
            fw.write(NewRow + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] GetArray() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.FileName));
            int count = 0;
            while (br.readLine() != null) count++;
            br.close();

            String[] result = new String[count];
            BufferedReader br2 = new BufferedReader(new FileReader(this.FileName));
            for (int i = 0; i < count; i++) {
                result[i] = br2.readLine();
            }
            br2.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public int SelectCount() {
        return GetArray().length;
    }

    public String GetRow(int RowNumber) {
        String[] lines = GetArray();
        if (RowNumber >= 0 && RowNumber < lines.length) {
            return lines[RowNumber];
        }
        return null;
    }

    public void DeleteRow(int RowNumber) {
        String[] lines = GetArray();
        try {
            PrintWriter pw = new PrintWriter(this.FileName);
            for (int i = 0; i < lines.length; i++) {
                if (i != RowNumber) {
                    pw.println(lines[i]);
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void UpdateRow(String NewRow, int RowNumber) {
        String[] lines = GetArray();
        try {
            PrintWriter pw = new PrintWriter(this.FileName);
            for (int i = 0; i < lines.length; i++) {
                if (i == RowNumber) {
                    pw.println(NewRow);
                } else {
                    pw.println(lines[i]);
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void InsertRow(String NewRow, int RowNumber) {
        String[] lines = GetArray();
        try {
            PrintWriter pw = new PrintWriter(this.FileName);
            for (int i = 0; i <= lines.length; i++) {
                if (i == RowNumber) {
                    pw.println(NewRow);
                }
                if (i < lines.length) {
                    pw.println(lines[i]);
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
