package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RequestBidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RequestBid getRequestBidSample1() {
        return new RequestBid().id(1L).description("description1").price(1L).duration(1L);
    }

    public static RequestBid getRequestBidSample2() {
        return new RequestBid().id(2L).description("description2").price(2L).duration(2L);
    }

    public static RequestBid getRequestBidRandomSampleGenerator() {
        return new RequestBid()
            .id(longCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .price(longCount.incrementAndGet())
            .duration(longCount.incrementAndGet());
    }
}
