package com.loghme.Wallet;

import com.loghme.Wallet.Exceptions.NotEnoughBalance;
import com.loghme.Wallet.Exceptions.WrongAmount;

public class Wallet {
    private double credit;

    public Wallet(int credit) {
        this.credit = credit;
    }

    public void charge(double amount) throws WrongAmount {
        if(amount >= 0)
            credit += amount;
        else
            throw new WrongAmount();
    }

    public void withdraw(double amount) throws NotEnoughBalance {
        if(credit - amount >= 0)
            credit -= amount;
        else
            throw new NotEnoughBalance();
    }

    public double getCredit() {
        return credit;
    }
}
