package com.example.project5;
public class BuildYourOwn extends Pizza {

    private static final double SMALL_BASE = 10.99;
    private static final double MEDIUM_BASE = 12.99;
    private static final double LARGE_BASE = 14.99;
    private static final double TOPPING_PRICE = 1.69;
    private static final int MAX_TOPPINGS = 5;

    @Override
    public double price() {
        if (getSize() == null) return 0.0;

        double base;

        switch (getSize()) {
            case SMALL:
                base = SMALL_BASE;
                break;
            case MEDIUM:
                base = MEDIUM_BASE;
                break;
            case LARGE:
                base = LARGE_BASE;
                break;
            default:
                base = 0.0;
        }

        int toppingCount = Math.min(getToppings().size(), MAX_TOPPINGS);

        return base + (toppingCount * TOPPING_PRICE);
    }
}