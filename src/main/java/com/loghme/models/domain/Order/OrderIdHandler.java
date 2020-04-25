package com.loghme.models.domain.Order;

import java.util.concurrent.atomic.AtomicLong;

public class OrderIdHandler {
    private static AtomicLong currentId = new AtomicLong(0L);

    public static int getNextId() {
        return (int)(currentId.getAndIncrement());
    }
}
