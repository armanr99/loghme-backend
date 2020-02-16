package com.loghme.User;

import com.loghme.Cart.Exceptions.EmptyCartFinalize;
import com.loghme.Wallet.Exceptions.NotEnoughBalance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class UserRepositoryTest {
    @After
    public void tearDown() {
        UserRepository.clearInstance();
    }

    @Test (expected = EmptyCartFinalize.class)
    public void testFinalizeOrderEmptyCart() throws EmptyCartFinalize {
        try {
            UserRepository.getInstance().finalizeOrder();
        } catch (NotEnoughBalance notEnoughBalance) {
            Assert.fail();
        }
    }
}
