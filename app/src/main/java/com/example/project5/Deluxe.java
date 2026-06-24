package com.example.project5;
public class Deluxe extends Pizza {

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 18.99;
            case MEDIUM: return 20.99;
            case LARGE: return 22.99;
            default: return 0.0;
        }
    }
}