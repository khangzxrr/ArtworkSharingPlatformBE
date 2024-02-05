package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArtworkCategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArtworkCategory getArtworkCategorySample1() {
        return new ArtworkCategory().id(1L).name("name1");
    }

    public static ArtworkCategory getArtworkCategorySample2() {
        return new ArtworkCategory().id(2L).name("name2");
    }

    public static ArtworkCategory getArtworkCategoryRandomSampleGenerator() {
        return new ArtworkCategory().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
