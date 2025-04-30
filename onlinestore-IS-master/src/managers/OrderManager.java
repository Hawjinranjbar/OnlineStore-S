package managers;

import common.Order;
import filemanager.txtFileManager;

public class OrderManager {
    private txtFileManager fm;

    public OrderManager() {
        fm = new txtFileManager("order.txt");
    }

    public void Insert(Order o) {
        fm.AppendRow(o.toString());
    }

    public Order[] SelectAll() {
        String[] rows = fm.GetArray();
        Order[] orders = new Order[rows.length];

        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != null) {
                String[] parts = rows[i].split(";");
                if (parts.length == 7) {
                    orders[i] = new Order(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            parts[4],
                            parts[5],
                            parts[6]
                    );
                }
            }
        }

        return orders;
    }
}
