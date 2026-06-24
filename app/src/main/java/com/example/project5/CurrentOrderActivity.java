package com.example.project5;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

/**
 * This class represents the Current Order screen.
 * It allows the user to view all pizzas in the current order,
 * remove individual pizzas, clear the order, and place the order.
 */
public class CurrentOrderActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter; // Adapter for ListView
    private ArrayList<String> pizzaStrings; // Stores string versions of pizzas
    private ListView listView; // Displays pizzas in the order

    /**
     * Initializes the UI components and sets up event listeners.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        // Back button returns to previous screen
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        listView = findViewById(R.id.orderList);
        pizzaStrings = new ArrayList<>();

        // Initialize adapter for displaying pizzas
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,
                pizzaStrings);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Populate list initially
        refreshList();

        /**
         * Removes the selected pizza after confirmation.
         */
        findViewById(R.id.removeButton).setOnClickListener(v -> {
            int pos = listView.getCheckedItemPosition();

            if (pos == ListView.INVALID_POSITION) {
                Toast.makeText(this, "Select a pizza first", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Remove Pizza")
                    .setMessage("Are you sure you want to remove this pizza?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        AppState.currentOrder.removePizza(pos);
                        refreshList();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        /**
         * Clears the entire order after confirmation.
         */
        findViewById(R.id.clearButton).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear Order")
                    .setMessage("Are you sure you want to clear the entire order?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        AppState.currentOrder.getPizzas().clear();
                        refreshList();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        /**
         * Places the order and moves it to StoreOrders after confirmation.
         */
        findViewById(R.id.placeOrderButton).setOnClickListener(v -> {

            if (AppState.currentOrder.getPizzas().isEmpty()) {
                Toast.makeText(this, "Order is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Place Order")
                    .setMessage("Are you sure you want to place this order?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        // Add order to store orders and reset current order
                        AppState.storeOrders.addOrder(AppState.currentOrder);
                        AppState.currentOrder = new Order();

                        Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();
                        refreshList();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    /**
     * Refreshes the ListView and updates subtotal, tax, and total.
     */
    private void refreshList() {
        pizzaStrings.clear();

        // Convert each pizza to string for display
        for (Pizza p : AppState.currentOrder.getPizzas()) {
            pizzaStrings.add(p.toString());
        }

        adapter.notifyDataSetChanged();

        // Update pricing display
        ((TextView) findViewById(R.id.subtotalText))
                .setText(String.format("Subtotal: $%.2f", AppState.currentOrder.getSubtotal()));

        ((TextView) findViewById(R.id.taxText))
                .setText(String.format("Tax: $%.2f", AppState.currentOrder.getSalesTax()));

        ((TextView) findViewById(R.id.totalText))
                .setText(String.format("Total: $%.2f", AppState.currentOrder.getTotal()));
    }
}