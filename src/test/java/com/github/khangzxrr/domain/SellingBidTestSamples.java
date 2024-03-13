// package com.github.khangzxrr.domain;

// import java.util.Random;
// import java.util.concurrent.atomic.AtomicLong;

// public class SellingBidTestSamples {

//     private static final Random random = new Random();
//     private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

//     public static SellingBid getSellingBidSample1() {
//         return new SellingBid().id(1L).bidPrice(1L);
//     }

//     public static SellingBid getSellingBidSample2() {
//         return new SellingBid().id(2L).bidPrice(2L);
//     }

//     public static SellingBid getSellingBidRandomSampleGenerator() {
//         return new SellingBid().id(longCount.incrementAndGet()).bidPrice(longCount.incrementAndGet());
//     }
// }
