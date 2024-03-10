// package com.github.khangzxrr.domain;

// import static com.github.khangzxrr.domain.ArtworkComplainTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkTestSamples.*;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.github.khangzxrr.web.rest.TestUtil;
// import org.junit.jupiter.api.Test;

// class ArtworkComplainTest {

//     @Test
//     void equalsVerifier() throws Exception {
//         TestUtil.equalsVerifier(ArtworkComplain.class);
//         ArtworkComplain artworkComplain1 = getArtworkComplainSample1();
//         ArtworkComplain artworkComplain2 = new ArtworkComplain();
//         assertThat(artworkComplain1).isNotEqualTo(artworkComplain2);

//         artworkComplain2.setId(artworkComplain1.getId());
//         assertThat(artworkComplain1).isEqualTo(artworkComplain2);

//         artworkComplain2 = getArtworkComplainSample2();
//         assertThat(artworkComplain1).isNotEqualTo(artworkComplain2);
//     }

//     @Test
//     void artworkTest() throws Exception {
//         ArtworkComplain artworkComplain = getArtworkComplainRandomSampleGenerator();
//         Artwork artworkBack = getArtworkRandomSampleGenerator();

//         artworkComplain.setArtwork(artworkBack);
//         assertThat(artworkComplain.getArtwork()).isEqualTo(artworkBack);

//         artworkComplain.artwork(null);
//         assertThat(artworkComplain.getArtwork()).isNull();
//     }
// }
