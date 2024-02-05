package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArtworkCommentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArtworkComment getArtworkCommentSample1() {
        return new ArtworkComment().id(1L).content("content1");
    }

    public static ArtworkComment getArtworkCommentSample2() {
        return new ArtworkComment().id(2L).content("content2");
    }

    public static ArtworkComment getArtworkCommentRandomSampleGenerator() {
        return new ArtworkComment().id(longCount.incrementAndGet()).content(UUID.randomUUID().toString());
    }
}
