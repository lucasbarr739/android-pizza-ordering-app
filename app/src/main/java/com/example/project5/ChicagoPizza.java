package com.example.project5;
import java.util.ArrayList;
import java.util.Arrays;

public class ChicagoPizza implements PizzaFactory {

    @Override
    public Pizza createDeluxe() {
        Pizza pizza = new Deluxe();
        pizza.setCrust(Crust.DEEP_DISH);
        pizza.setSize(Size.SMALL);
        pizza.setToppings(new ArrayList<>(Arrays.asList(
                Topping.SAUSAGE,
                Topping.PEPPERONI,
                Topping.GREEN_PEPPER,
                Topping.ONION,
                Topping.MUSHROOM
        )));
        return pizza;
    }

    @Override
    public Pizza createMeatzza() {
        Pizza pizza = new Meatzza();
        pizza.setCrust(Crust.STUFFED);
        pizza.setSize(Size.SMALL);
        pizza.setToppings(new ArrayList<>(Arrays.asList(
                Topping.SAUSAGE,
                Topping.PEPPERONI,
                Topping.BEEF,
                Topping.HAM
        )));
        return pizza;
    }

    @Override
    public Pizza createBBQChicken() {
        Pizza pizza = new BBQChicken();
        pizza.setCrust(Crust.PAN);
        pizza.setSize(Size.SMALL);
        pizza.setToppings(new ArrayList<>(Arrays.asList(
                Topping.BBQ_CHICKEN,
                Topping.GREEN_PEPPER,
                Topping.PROVOLONE,
                Topping.CHEDDAR
        )));
        return pizza;
    }

    @Override
    public Pizza createBuildYourOwn() {
        Pizza pizza = new BuildYourOwn();
        pizza.setCrust(Crust.PAN);
        pizza.setSize(Size.SMALL);
        return pizza;
    }
}