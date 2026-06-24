package com.example.project5;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StoreOrders {

    private final ArrayList<Order> orders = new ArrayList<>();

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(int index) {
        if (index >= 0 && index < orders.size()) {
            orders.remove(index);
        }
    }

    public void exportToFile(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (Order order : orders) {
                writer.write(order.detailedString());
                writer.write("\n========================================\n");
            }
        }
    }
}