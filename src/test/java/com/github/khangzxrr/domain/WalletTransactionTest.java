// package com.github.khangzxrr.domain;

// import static com.github.khangzxrr.domain.RequestProgressTestSamples.*;
// import static com.github.khangzxrr.domain.SellingBidTestSamples.*;
// import static com.github.khangzxrr.domain.WalletTestSamples.*;
// import static com.github.khangzxrr.domain.WalletTransactionTestSamples.*;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.github.khangzxrr.web.rest.TestUtil;
// import org.junit.jupiter.api.Test;

// class WalletTransactionTest {

//     @Test
//     void equalsVerifier() throws Exception {
//         TestUtil.equalsVerifier(WalletTransaction.class);
//         WalletTransaction walletTransaction1 = getWalletTransactionSample1();
//         WalletTransaction walletTransaction2 = new WalletTransaction();
//         assertThat(walletTransaction1).isNotEqualTo(walletTransaction2);

//         walletTransaction2.setId(walletTransaction1.getId());
//         assertThat(walletTransaction1).isEqualTo(walletTransaction2);

//         walletTransaction2 = getWalletTransactionSample2();
//         assertThat(walletTransaction1).isNotEqualTo(walletTransaction2);
//     }

//     @Test
//     void walletTest() throws Exception {
//         WalletTransaction walletTransaction = getWalletTransactionRandomSampleGenerator();
//         Wallet walletBack = getWalletRandomSampleGenerator();

//         walletTransaction.setWallet(walletBack);
//         assertThat(walletTransaction.getWallet()).isEqualTo(walletBack);

//         walletTransaction.wallet(null);
//         assertThat(walletTransaction.getWallet()).isNull();
//     }

//     @Test
//     void requestProgressTest() throws Exception {
//         WalletTransaction walletTransaction = getWalletTransactionRandomSampleGenerator();
//         RequestProgress requestProgressBack = getRequestProgressRandomSampleGenerator();

//         walletTransaction.setRequestProgress(requestProgressBack);
//         assertThat(walletTransaction.getRequestProgress()).isEqualTo(requestProgressBack);
//         assertThat(requestProgressBack.getTransaction()).isEqualTo(walletTransaction);

//         walletTransaction.requestProgress(null);
//         assertThat(walletTransaction.getRequestProgress()).isNull();
//         assertThat(requestProgressBack.getTransaction()).isNull();
//     }

//     @Test
//     void sellingBidTest() throws Exception {
//         WalletTransaction walletTransaction = getWalletTransactionRandomSampleGenerator();
//         SellingBid sellingBidBack = getSellingBidRandomSampleGenerator();

//         walletTransaction.setSellingBid(sellingBidBack);
//         assertThat(walletTransaction.getSellingBid()).isEqualTo(sellingBidBack);
//         assertThat(sellingBidBack.getTransaction()).isEqualTo(walletTransaction);

//         walletTransaction.sellingBid(null);
//         assertThat(walletTransaction.getSellingBid()).isNull();
//         assertThat(sellingBidBack.getTransaction()).isNull();
//     }
// }
