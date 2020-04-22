package com.loghme.models.domain.Food;

import com.loghme.models.domain.Food.exceptions.InvalidCount;

public class Food {
    private String name;
    private String description;
    private String image;
    private double popularity;
    private double price;

    public Food(String name, String description, double popularity, double price) {
        this.name = name;
        this.description = description;
        this.popularity = popularity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getPrice() {
        return price;
    }

    public void validateCount(double count) throws InvalidCount {}

    public void sell(int count) throws InvalidCount {}
}