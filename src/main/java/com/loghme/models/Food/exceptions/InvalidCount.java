package com.loghme.models.Food.exceptions;

public class InvalidCount extends Exception {
    private String foodName;
    private int count;

    public InvalidCount(String foodName, int count) {
        this.foodName = foodName;
        this.count = count;
    }

    public String toString() {
        return String.format("There are %d %s available", count, foodName);
    }
}