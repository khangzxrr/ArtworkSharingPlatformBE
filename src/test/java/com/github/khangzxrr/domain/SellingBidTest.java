// package com.github.khangzxrr.domain;

// import static com.github.khangzxrr.domain.ArtworkSellingTestSamples.*;
// import static com.github.khangzxrr.domain.SellingBidTestSamples.*;
// //import static com.github.khangzxrr.domain.WalletTransactionTestSamples.*;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.github.khangzxrr.web.rest.TestUtil;
// import org.junit.jupiter.api.Test;

// class SellingBidTest {

//     @Test
//     void equalsVerifier() throws Exception {
//         TestUtil.equalsVerifier(SellingBid.class);
//         SellingBid sellingBid1 = getSellingBidSample1();
//         SellingBid sellingBid2 = new SellingBid();
//         assertThat(sellingBid1).isNotEqualTo(sellingBid2);

//         sellingBid2.setId(sellingBid1.getId());
//         assertThat(sellingBid1).isEqualTo(sellingBid2);

//         sellingBid2 = getSellingBidSample2();
//         assertThat(sellingBid1).isNotEqualTo(sellingBid2);
//     }

//     @Test
//     void transactionTest() throws Exception {
//         SellingBid sellingBid = getSellingBidRandomSampleGenerator();
//         // WalletTransaction walletTransactionBack = getWalletTransactionRandomSampleGenerator();

//         // sellingBid.setTransaction(walletTransactionBack);
//         // assertThat(sellingBid.getTransaction()).isEqualTo(walletTransactionBack);

//         sellingBid.transaction(null);
//         assertThat(sellingBid.getTransaction()).isNull();
//     }

//     @Test
//     void artworkSellingTest() throws Exception {
//         SellingBid sellingBid = getSellingBidRandomSampleGenerator();
//         ArtworkSelling artworkSellingBack = getArtworkSellingRandomSampleGenerator();

//         sellingBid.setArtworkSelling(artworkSellingBack);
//         assertThat(sellingBid.getArtworkSelling()).isEqualTo(artworkSellingBack);

//         sellingBid.artworkSelling(null);
//         assertThat(sellingBid.getArtworkSelling()).isNull();
//     }
// }
