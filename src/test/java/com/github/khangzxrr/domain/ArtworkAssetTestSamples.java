package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ArtworkAssetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArtworkAsset getArtworkAssetSample1() {
        return new ArtworkAsset().id(1L);
    }

    public static ArtworkAsset getArtworkAssetSample2() {
        return new ArtworkAsset().id(2L);
    }

    public static ArtworkAsset getArtworkAssetRandomSampleGenerator() {
        return new ArtworkAsset().id(longCount.incrementAndGet());
    }
}
