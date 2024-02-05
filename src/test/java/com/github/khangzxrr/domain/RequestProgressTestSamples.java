package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RequestProgressTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RequestProgress getRequestProgressSample1() {
        return new RequestProgress().id(1L).description("description1");
    }

    public static RequestProgress getRequestProgressSample2() {
        return new RequestProgress().id(2L).description("description2");
    }

    public static RequestProgress getRequestProgressRandomSampleGenerator() {
        return new RequestProgress().id(longCount.incrementAndGet()).description(UUID.randomUUID().toString());
    }
}
