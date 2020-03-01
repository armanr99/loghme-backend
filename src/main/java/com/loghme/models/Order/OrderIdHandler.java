package com.loghme.models.Order;

import java.util.concurrent.atomic.AtomicLong;

class OrderIdHandler {
    private static AtomicLong currentId = new AtomicLong(0L);

    static String getNextId() {
        return String.valueOf(currentId.getAndIncrement());
    }
}
