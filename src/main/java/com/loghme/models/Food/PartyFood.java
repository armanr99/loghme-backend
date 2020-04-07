package com.loghme.models.Food;

import com.loghme.models.Food.exceptions.InvalidCount;

public class PartyFood extends Food {
    private int count;
    private double oldPrice;

    public PartyFood(String name, String description, double popularity, double price, int count, double oldPrice) {
        super(name, description, popularity, price);
        this.count = count;
        this.oldPrice = oldPrice;
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