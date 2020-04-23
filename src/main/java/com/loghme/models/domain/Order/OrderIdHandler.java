package com.loghme.models.domain.Order;

import java.util.concurrent.atomic.AtomicLong;

class OrderIdHandler {
    private static AtomicLong currentId = new AtomicLong(0L);

    static int getNextId() {
        return (int)(currentId.getAndIncrement());
    }
}
