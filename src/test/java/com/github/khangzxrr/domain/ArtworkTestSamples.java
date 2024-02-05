package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArtworkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Artwork getArtworkSample1() {
        return new Artwork().id(1L).name("name1").description("description1").createAt("createAt1");
    }

    public static Artwork getArtworkSample2() {
        return new Artwork().id(2L).name("name2").description("description2").createAt("createAt2");
    }

    public static Artwork getArtworkRandomSampleGenerator() {
        return new Artwork()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createAt(UUID.randomUUID().toString());
    }
}
