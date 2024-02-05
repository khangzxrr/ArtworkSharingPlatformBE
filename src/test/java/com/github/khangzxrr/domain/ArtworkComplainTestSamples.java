package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArtworkComplainTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArtworkComplain getArtworkComplainSample1() {
        return new ArtworkComplain().id(1L).content("content1");
    }

    public static ArtworkComplain getArtworkComplainSample2() {
        return new ArtworkComplain().id(2L).content("content2");
    }

    public static ArtworkComplain getArtworkComplainRandomSampleGenerator() {
        return new ArtworkComplain().id(longCount.incrementAndGet()).content(UUID.randomUUID().toString());
    }
}
