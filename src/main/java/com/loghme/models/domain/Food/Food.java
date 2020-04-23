package com.loghme.models.domain.Food;

import com.loghme.models.domain.Food.exceptions.InvalidCount;

public class Food {
    private String name;
    private String description;
    private String image;
    private String restaurantId;
    private double popularity;
    private double price;

    public Food(String name, String description, String image, double popularity, double price) {
        this.name = name;
        this.description = description;
        this.image = image;
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

    public String getRestaurantId() {
        return restaurantId;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getPrice() {
        return price;
    }

    public void validateCount(double count) throws InvalidCount {}

    public void sell(int count) throws InvalidCount {}

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
