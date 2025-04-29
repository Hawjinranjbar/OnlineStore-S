package managers;

import common.Address;
import filemanager.txtFileManager;

public class AddressManager {
    private txtFileManager fm;

    public AddressManager() {
        fm = new txtFileManager("address.txt");
    }

    public void Insert(Address a) {
        fm.AppendRow(a.toString());
    }

    public void Update(Address a, int row) {
        fm.UpdateRow(a.toString(), row);
    }

    public void Delete(int row) {
        fm.DeleteRow(row);
    }

    public Address[] SelectAll() {
        String[] rows = fm.GetArray();
        Address[] addresses = new Address[rows.length];
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";");
                if (parts.length == 5) {
                    addresses[i] = new Address(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4]
                    );
                }
            }
        }
        return addresses;
    }
}
