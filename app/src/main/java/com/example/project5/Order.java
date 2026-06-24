package com.example.project5;

import java.util.ArrayList;

/**
 * This class represents a customer's order.
 * Each order contains a list of pizzas and calculates
 * subtotal, tax, and total cost.
 */
public class Order {

    private int number; // Unique order number
    private ArrayList<Pizza> pizzas; // List of pizzas in this order

    private static int nextOrderNumber = 1; // Used to assign unique order numbers
    private static final double SALES_TAX = 0.06625; // Sales tax rate

    /**
     * Constructor initializes a new order with a unique number
     * and an empty list of pizzas.
     */
    public Order() {
        this.number = nextOrderNumber++;
        this.pizzas = new ArrayList<>();
    }

    /**
     * Returns the order number.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the list of pizzas in the order.
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Adds a pizza to the order.
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Removes a pizza at the specified index if valid.
     */
    public void removePizza(int index) {
        if (index >= 0 && index < pizzas.size()) {
            pizzas.remove(index);
        }
    }

    /**
     * Calculates the subtotal by summing the price of all pizzas.
     */
    public double getSubtotal() {
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        return subtotal;
    }

    /**
     * Calculates the sales tax based on the subtotal.
     */
    public double getSalesTax() {
        return getSubtotal() * SALES_TAX;
    }

    /**
     * Calculates the total cost including tax.
     */
    public double getTotal() {
        return getSubtotal() + getSalesTax();
    }

    /**
     * Returns a detailed string representation of the order,
     * including each pizza's attributes and pricing breakdown.
     */
    public String detailedString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Order Number: ").append(number).append("\n");

        // Loop through each pizza and append its details
        for (Pizza pizza : pizzas) {
            sb.append("Style: ").append(pizza.getStyle()).append("\n");
            sb.append("Pizza: ").append(pizza.getClass().getSimpleName()).append("\n");
            sb.append("Size: ").append(pizza.getSize()).append("\n");
            sb.append("Crust: ")
                    .append(pizza.getCrust().name().replace('_', ' '))
                    .append("\n");
            sb.append("Toppings: ").append(pizza.toppingsAsString()).append("\n");

            // Display individual pizza price
            sb.append(String.format("Pizza Price: $%.2f%n", pizza.price()));
            sb.append("----------------------------------------\n");
        }

        // Append overall pricing summary
        sb.append(String.format("Subtotal: $%.2f%n", getSubtotal()));
        sb.append(String.format("Sales Tax: $%.2f%n", getSalesTax()));
        sb.append(String.format("Order Total: $%.2f%n", getTotal()));

        return sb.toString();
    }

    /**
     * Returns a concise summary of the order.
     */
    @Override
    public String toString() {
        return String.format(
                "Order #%d - %d pizza(s) - $%.2f",
                number,
                pizzas.size(),
                getTotal()
        );
    }
}