// package com.github.khangzxrr.domain;

// import java.util.Random;
// import java.util.concurrent.atomic.AtomicLong;

// public class WalletTransactionTestSamples {

//     private static final Random random = new Random();
//     private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

//     public static WalletTransaction getWalletTransactionSample1() {
//         return new WalletTransaction().id(1L).amount(1L);
//     }

//     public static WalletTransaction getWalletTransactionSample2() {
//         return new WalletTransaction().id(2L).amount(2L);
//     }

//     public static WalletTransaction getWalletTransactionRandomSampleGenerator() {
//         return new WalletTransaction().id(longCount.incrementAndGet()).amount(longCount.incrementAndGet());
//     }
// }
