// package com.github.khangzxrr.domain;

// import java.util.Random;
// import java.util.concurrent.atomic.AtomicLong;

// public class ArtworkSellingTestSamples {

//     private static final Random random = new Random();
//     private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

//     public static ArtworkSelling getArtworkSellingSample1() {
//         return new ArtworkSelling().id(1L).expectedSellingPrice(1L);
//     }

//     public static ArtworkSelling getArtworkSellingSample2() {
//         return new ArtworkSelling().id(2L).expectedSellingPrice(2L);
//     }

//     public static ArtworkSelling getArtworkSellingRandomSampleGenerator() {
//         return new ArtworkSelling().id(longCount.incrementAndGet()).expectedSellingPrice(longCount.incrementAndGet());
//     }
// }
