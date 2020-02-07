package com.loghme.Food;

public class Food {
    private String name;
    private String description;
    private double popularity;
    private double price;

    public Food(String name, String description, double popularity, double price) {
        this.name = name;
        this.description = description;
        this.popularity = popularity;
        this.price = price;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    double getPopularity() {
        return popularity;
    }

    double getPrice() {
        return price;
    }

    void setName(String name) {
        this.name = name;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    void setPrice(double price) {
        this.price = price;
    }
}