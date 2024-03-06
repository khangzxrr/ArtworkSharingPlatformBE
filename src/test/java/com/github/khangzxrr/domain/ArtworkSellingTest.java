// package com.github.khangzxrr.domain;

// import static com.github.khangzxrr.domain.ArtworkSellingTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkTestSamples.*;
// import static com.github.khangzxrr.domain.SellingBidTestSamples.*;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.github.khangzxrr.web.rest.TestUtil;
// import java.util.HashSet;
// import java.util.Set;
// import org.junit.jupiter.api.Test;

// class ArtworkSellingTest {

//     @Test
//     void equalsVerifier() throws Exception {
//         TestUtil.equalsVerifier(ArtworkSelling.class);
//         ArtworkSelling artworkSelling1 = getArtworkSellingSample1();
//         ArtworkSelling artworkSelling2 = new ArtworkSelling();
//         assertThat(artworkSelling1).isNotEqualTo(artworkSelling2);

//         artworkSelling2.setId(artworkSelling1.getId());
//         assertThat(artworkSelling1).isEqualTo(artworkSelling2);

//         artworkSelling2 = getArtworkSellingSample2();
//         assertThat(artworkSelling1).isNotEqualTo(artworkSelling2);
//     }

//     @Test
//     void bidsTest() throws Exception {
//         ArtworkSelling artworkSelling = getArtworkSellingRandomSampleGenerator();
//         SellingBid sellingBidBack = getSellingBidRandomSampleGenerator();

//         artworkSelling.addBids(sellingBidBack);
//         assertThat(artworkSelling.getBids()).containsOnly(sellingBidBack);
//         assertThat(sellingBidBack.getArtworkSelling()).isEqualTo(artworkSelling);

//         artworkSelling.removeBids(sellingBidBack);
//         assertThat(artworkSelling.getBids()).doesNotContain(sellingBidBack);
//         assertThat(sellingBidBack.getArtworkSelling()).isNull();

//         artworkSelling.bids(new HashSet<>(Set.of(sellingBidBack)));
//         assertThat(artworkSelling.getBids()).containsOnly(sellingBidBack);
//         assertThat(sellingBidBack.getArtworkSelling()).isEqualTo(artworkSelling);

//         artworkSelling.setBids(new HashSet<>());
//         assertThat(artworkSelling.getBids()).doesNotContain(sellingBidBack);
//         assertThat(sellingBidBack.getArtworkSelling()).isNull();
//     }

//     @Test
//     void artworkTest() throws Exception {
//         ArtworkSelling artworkSelling = getArtworkSellingRandomSampleGenerator();
//         Artwork artworkBack = getArtworkRandomSampleGenerator();

//         artworkSelling.setArtwork(artworkBack);
//         assertThat(artworkSelling.getArtwork()).isEqualTo(artworkBack);
//         assertThat(artworkBack.getArtworkSelling()).isEqualTo(artworkSelling);

//         artworkSelling.artwork(null);
//         assertThat(artworkSelling.getArtwork()).isNull();
//         assertThat(artworkBack.getArtworkSelling()).isNull();
//     }
// }
