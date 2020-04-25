package com.loghme.models.DTOs.Food;

public class FoodInput {
    private String name;
    private String description;
    private String image;
    private double price;
    private double popularity;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public double getPopularity() {
        return popularity;
    }
}
