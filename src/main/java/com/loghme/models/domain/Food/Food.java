package com.loghme.models.domain.Food;

import com.loghme.exceptions.InvalidCount;
import com.loghme.exceptions.RestaurantDoesntExist;
import com.loghme.models.domain.Restaurant.Restaurant;
import com.loghme.models.repositories.RestaurantRepository;

import java.sql.SQLException;

public class Food {
    private String name;
    private String restaurantId;
    private String description;
    private String image;
    private double popularity;
    private double price;

    public Food(
            String name,
            String restaurantId,
            String description,
            String image,
            double popularity,
            double price) {
        this.name = name;
        this.restaurantId = restaurantId;
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

    public void sell(int count) throws InvalidCount, SQLException {}

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant getRestaurant() throws RestaurantDoesntExist, SQLException {
        return RestaurantRepository.getInstance().getRestaurant(restaurantId);
    }
}
