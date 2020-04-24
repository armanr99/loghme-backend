package com.loghme.models.domain.Food;

import com.loghme.models.domain.Food.exceptions.InvalidCount;

public class PartyFood extends Food {
    private int count;
    private double oldPrice;

    public PartyFood(String name, String restaurantId, String description, String image, double popularity, double price, int count, double oldPrice) {
        super(name, restaurantId, description, image,  popularity, price);
        this.count = count;
        this.oldPrice = oldPrice;
    }

    public int getCount() {
        return count;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    @Override
    public void validateCount(double count) throws InvalidCount {
        if(this.count < count)
            throw new InvalidCount(this.getName(), this.count);
    }

    @Override
    public void sell(int count) throws InvalidCount {
        validateCount(count);
        this.count -= count;
    }
}