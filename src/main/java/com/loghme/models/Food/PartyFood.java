package com.loghme.models.Food;

public class PartyFood extends Food {
    private int count;
    private double oldPrice;

    public PartyFood(String name, String description, double popularity, double price, int count, double oldPrice) {
        super(name, description, popularity, price);
        this.count = count;
        this.oldPrice = oldPrice;
    }

    public int getCount() {
        return count;
    }

    public double getOldPrice() {
        return oldPrice;
    }
}
