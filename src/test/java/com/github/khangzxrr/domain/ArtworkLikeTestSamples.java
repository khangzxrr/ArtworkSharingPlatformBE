package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ArtworkLikeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArtworkLike getArtworkLikeSample1() {
        return new ArtworkLike().id(1L);
    }

    public static ArtworkLike getArtworkLikeSample2() {
        return new ArtworkLike().id(2L);
    }

    public static ArtworkLike getArtworkLikeRandomSampleGenerator() {
        return new ArtworkLike().id(longCount.incrementAndGet());
    }
}
