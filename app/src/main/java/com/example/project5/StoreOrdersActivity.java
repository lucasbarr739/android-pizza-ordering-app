package com.example.project5;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * This class represents the Store Orders screen.
 * It allows the user to view all placed orders,
 * see detailed information for each order, and remove orders.
 */
public class StoreOrdersActivity extends AppCompatActivity {

    private ListView storeOrdersList; // Displays list of all orders
    private TextView orderDetailsText; // Displays detailed info for selected order
    private ArrayAdapter<String> adapter; // Adapter for ListView
    private ArrayList<String> orderStrings; // Stores string representations of orders

    private int selectedIndex = ListView.INVALID_POSITION; // Tracks selected order index

    /**
     * Initializes UI components and sets up event listeners.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        // Back button returns to previous screen
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // UI references
        storeOrdersList = findViewById(R.id.storeOrdersList);
        orderDetailsText = findViewById(R.id.orderDetailsText);

        orderStrings = new ArrayList<>();

        // Initialize adapter for displaying order summaries
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_single_choice,
                orderStrings
        );

        storeOrdersList.setAdapter(adapter);
        storeOrdersList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        /**
         * Handles selection of an order from the list.
         * Displays detailed information about the selected order.
         */
        storeOrdersList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;

            // Get selected order from AppState and display details
            Order selectedOrder = AppState.storeOrders.getOrders().get(position);
            orderDetailsText.setText(selectedOrder.detailedString());
        });

        /**
         * Removes the selected order.
         */
        findViewById(R.id.removeOrderButton).setOnClickListener(v -> {
            if (selectedIndex == ListView.INVALID_POSITION) {
                Toast.makeText(this, "Select an order first", Toast.LENGTH_SHORT).show();
                return;
            }

            // Remove order from store
            AppState.storeOrders.removeOrder(selectedIndex);

            // Reset selection and UI
            selectedIndex = ListView.INVALID_POSITION;
            orderDetailsText.setText("Select an order to view details");

            refreshOrders();
        });

        // Initial population of orders list
        refreshOrders();
    }

    /**
     * Refreshes the ListView with current store orders.
     */
    private void refreshOrders() {
        orderStrings.clear();

        // Convert each order to string summary
        for (Order order : AppState.storeOrders.getOrders()) {
            orderStrings.add(order.toString());
        }

        adapter.notifyDataSetChanged();

        // If no orders exist, show message
        if (orderStrings.isEmpty()) {
            orderDetailsText.setText("No store orders yet");
        }
    }
}