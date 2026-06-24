package com.example.project5;
import java.util.ArrayList;

public abstract class Pizza {
    private ArrayList<Topping> toppings; //Topping is a enum class
    private Crust crust; //Crust is a enum class
    private Size size; //Size is a enum class

    public Pizza() {
        this.toppings = new ArrayList<>();
    }

    public abstract double price(); //polymorphism

    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    public void addTopping(Topping topping) {
        if (!toppings.contains(topping)) {
            toppings.add(topping);
        }
    }

    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    public Crust getCrust() {
        return crust;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getStyle() {
        if (crust == null) {
            return "-";
        }
        switch (crust) {
            case DEEP_DISH:
            case PAN:
            case STUFFED:
                return "Chicago";
            case BROOKLYN:
            case THIN:
            case HAND_TOSSED:
                return "New York";
            default:
                return "-";
        }
    }

    public String toppingsAsString() {
        if (toppings == null || toppings.isEmpty()) {
            return "None";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < toppings.size(); i++) {
            sb.append(toppings.get(i).name().replace('_', ' '));

            if (i < toppings.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | $%.2f",
                getStyle(),
                getClass().getSimpleName(),
                size == null ? "-" : size.name(),
                crust == null ? "-" : crust.name().replace('_', ' '),
                price());
    }
}