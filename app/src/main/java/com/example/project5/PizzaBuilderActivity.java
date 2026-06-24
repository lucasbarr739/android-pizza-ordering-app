package com.example.project5;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents the Pizza Builder screen.
 * It allows the user to select pizza style, size, and toppings,
 * and dynamically updates the price based on selections.
 */
public class PizzaBuilderActivity extends AppCompatActivity {

    private Pizza pizza; // Current pizza being built
    private String pizzaType; // Type of pizza selected (Deluxe, BBQ, etc.)
    private TextView priceText; // Displays current price

    // ListViews for Build Your Own toppings
    private ListView availableList, selectedList;

    // Buttons for adding/removing toppings
    private Button addToppingButton, removeToppingButton;

    private ArrayAdapter<Topping> selectedAdapter; // Adapter for selected toppings list
    private ArrayList<Topping> selectedToppings = new ArrayList<>(); // Stores selected toppings

    private Topping selectedAvailable; // Currently selected available topping
    private Topping selectedChosen; // Currently selected chosen topping

    /**
     * Initializes UI components and sets up listeners.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_builder);

        // Back button returns to previous screen
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // UI references
        ImageView image = findViewById(R.id.pizzaImage);
        Spinner styleSpinner = findViewById(R.id.styleSpinner);
        Spinner sizeSpinner = findViewById(R.id.sizeSpinner);
        priceText = findViewById(R.id.priceText);

        availableList = findViewById(R.id.availableToppingsList);
        selectedList = findViewById(R.id.selectedToppingsList);
        addToppingButton = findViewById(R.id.addToppingButton);
        removeToppingButton = findViewById(R.id.removeToppingButton);
        TextView toppingsLabel = findViewById(R.id.toppingsLabel);

        // Get selected pizza type from previous screen
        pizzaType = getIntent().getStringExtra("pizzaType");

        // Default to Deluxe if null
        if (pizzaType == null) {
            pizzaType = "Deluxe";
        }

        // Set pizza image based on type
        switch (pizzaType) {
            case "Deluxe":
                image.setImageResource(R.drawable.deluxe);
                break;
            case "BBQ Chicken":
                image.setImageResource(R.drawable.bbqchicken);
                break;
            case "Meatzza":
                image.setImageResource(R.drawable.meatzza);
                break;
            default:
                image.setImageResource(R.drawable.buildyourown);
                break;
        }

        // Setup style spinner (NY / Chicago)
        ArrayAdapter<String> styleAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList("New York", "Chicago")
        );
        styleSpinner.setAdapter(styleAdapter);

        // Setup size spinner
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Arrays.asList("SMALL", "MEDIUM", "LARGE")
        );
        sizeSpinner.setAdapter(sizeAdapter);

        /**
         * Listener that updates pizza whenever style or size changes.
         */
        AdapterView.OnItemSelectedListener refreshListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Create new pizza based on selected style
                createPizza(styleSpinner.getSelectedItem().toString());

                // Set size
                pizza.setSize(Size.values()[sizeSpinner.getSelectedItemPosition()]);

                // Reapply selected toppings
                for (Topping topping : selectedToppings) {
                    pizza.addTopping(topping);
                }

                // Update displayed price
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };

        styleSpinner.setOnItemSelectedListener(refreshListener);
        sizeSpinner.setOnItemSelectedListener(refreshListener);

        // Show toppings UI only for Build Your Own
        boolean isBuildYourOwn = pizzaType.equals("Build Your Own");

        int visibility = isBuildYourOwn ? View.VISIBLE : View.GONE;
        toppingsLabel.setVisibility(visibility);
        availableList.setVisibility(visibility);
        selectedList.setVisibility(visibility);
        addToppingButton.setVisibility(visibility);
        removeToppingButton.setVisibility(visibility);

        // Setup topping selection logic
        setupBuildYourOwnToppings();

        /**
         * Adds pizza to current order.
         */
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            AppState.currentOrder.addPizza(pizza);
            Toast.makeText(this, "Pizza added to order", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    /**
     * Creates a pizza using the factory pattern based on style and type.
     */
    private void createPizza(String style) {
        PizzaFactory factory;

        // Choose factory based on style
        if (style.equals("Chicago")) {
            factory = new ChicagoPizza();
        } else {
            factory = new NYPizza();
        }

        // Create specific pizza type
        switch (pizzaType) {
            case "Deluxe":
                pizza = factory.createDeluxe();
                break;
            case "BBQ Chicken":
                pizza = factory.createBBQChicken();
                break;
            case "Meatzza":
                pizza = factory.createMeatzza();
                break;
            default:
                pizza = factory.createBuildYourOwn();
                break;
        }
    }

    /**
     * Sets up topping selection and management for Build Your Own pizza.
     */
    private void setupBuildYourOwnToppings() {

        // Adapter for available toppings
        ArrayAdapter<Topping> availableAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_single_choice,
                Topping.values()
        );

        // Adapter for selected toppings
        selectedAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_single_choice,
                selectedToppings
        );

        availableList.setAdapter(availableAdapter);
        selectedList.setAdapter(selectedAdapter);

        availableList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        selectedList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Track selected topping from available list
        availableList.setOnItemClickListener((parent, view, position, id) -> {
            selectedAvailable = (Topping) parent.getItemAtPosition(position);
        });

        // Track selected topping from selected list
        selectedList.setOnItemClickListener((parent, view, position, id) -> {
            selectedChosen = (Topping) parent.getItemAtPosition(position);
        });

        /**
         * Adds a topping (max 5).
         */
        addToppingButton.setOnClickListener(v -> {
            if (selectedAvailable == null) {
                Toast.makeText(this, "Select a topping first", Toast.LENGTH_SHORT).show();
                return;
            }

            // Limit toppings to 5
            if (selectedToppings.size() >= 5) {
                Toast.makeText(this, "Maximum 5 toppings allowed", Toast.LENGTH_SHORT).show();
                return;
            }

            // Avoid duplicates
            if (!selectedToppings.contains(selectedAvailable)) {
                selectedToppings.add(selectedAvailable);
                pizza.addTopping(selectedAvailable);
                selectedAdapter.notifyDataSetChanged();
                updatePrice();
            }
        });

        /**
         * Removes selected topping.
         */
        removeToppingButton.setOnClickListener(v -> {
            if (selectedChosen == null) {
                Toast.makeText(this, "Select a topping to remove", Toast.LENGTH_SHORT).show();
                return;
            }

            selectedToppings.remove(selectedChosen);
            pizza.removeTopping(selectedChosen);
            selectedChosen = null;
            selectedAdapter.notifyDataSetChanged();
            updatePrice();
        });
    }

    /**
     * Updates the displayed price based on current pizza configuration.
     */
    private void updatePrice() {
        priceText.setText(String.format("Price: $%.2f", pizza.price()));
    }
}