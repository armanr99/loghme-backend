package com.loghme.models.domain.Wallet;

import com.loghme.exceptions.NotEnoughBalance;
import com.loghme.exceptions.WrongAmount;
import com.loghme.models.repositories.UserRepository;

import java.sql.SQLException;

public class Wallet {
    private int userId;
    private double credit;

    public Wallet(int userId, double credit) {
        this.userId = userId;
        this.credit = credit;
    }

    public void charge(double amount) throws WrongAmount, SQLException {
        if (amount >= 0) {
            credit += amount;
            UserRepository.getInstance().updateCredit(userId, getCredit());
        } else {
            throw new WrongAmount();
        }
    }

    public void withdraw(double amount) throws NotEnoughBalance, SQLException {
        if (credit - amount >= 0) {
            credit -= amount;
            UserRepository.getInstance().updateCredit(userId, getCredit());
        } else {
            throw new NotEnoughBalance();
        }
    }

    public double getCredit() {
        return credit;
    }
}
