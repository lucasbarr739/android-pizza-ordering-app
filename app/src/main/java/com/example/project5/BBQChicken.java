package com.example.project5;
public class BBQChicken extends Pizza {

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 16.99;
            case MEDIUM: return 18.99;
            case LARGE: return 20.99;
            default: return 0.0;
        }
    }
}