package com.example.project5;
public class Meatzza extends Pizza {

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL: return 19.99;
            case MEDIUM: return 21.99;
            case LARGE: return 23.99;
            default: return 0.0;
        }
    }
}