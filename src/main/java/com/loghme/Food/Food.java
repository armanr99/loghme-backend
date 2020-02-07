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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}