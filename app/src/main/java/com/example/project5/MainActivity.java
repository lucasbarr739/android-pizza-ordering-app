package com.example.project5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<String> pizzaNames = Arrays.asList(
            "Deluxe",
            "BBQ Chicken",
            "Meatzza",
            "Build Your Own"
    );

    private final List<Integer> pizzaImages = Arrays.asList(
            R.drawable.deluxe,
            R.drawable.bbqchicken,
            R.drawable.meatzza,
            R.drawable.buildyourown
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 🔥 REQUIRED

        RecyclerView recyclerView = findViewById(R.id.pizzaRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PizzaAdapter adapter = new PizzaAdapter(pizzaNames, pizzaImages, position -> {
            Intent intent = new Intent(MainActivity.this, PizzaBuilderActivity.class);
            intent.putExtra("pizzaType", pizzaNames.get(position));
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        Button currentOrder = findViewById(R.id.currentOrderButton);
        Button storeOrders = findViewById(R.id.storeOrdersButton);

        currentOrder.setOnClickListener(v -> {
            startActivity(new Intent(this, CurrentOrderActivity.class));
        });

        storeOrders.setOnClickListener(v -> {
            startActivity(new Intent(this, StoreOrdersActivity.class));
        });
    }
}