// package com.github.khangzxrr.domain;

// import static com.github.khangzxrr.domain.ArtworkLikeTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkTestSamples.*;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.github.khangzxrr.web.rest.TestUtil;
// import org.junit.jupiter.api.Test;

// class ArtworkLikeTest {

//     @Test
//     void equalsVerifier() throws Exception {
//         TestUtil.equalsVerifier(ArtworkLike.class);
//         ArtworkLike artworkLike1 = getArtworkLikeSample1();
//         ArtworkLike artworkLike2 = new ArtworkLike();
//         assertThat(artworkLike1).isNotEqualTo(artworkLike2);

//         artworkLike2.setId(artworkLike1.getId());
//         assertThat(artworkLike1).isEqualTo(artworkLike2);

//         artworkLike2 = getArtworkLikeSample2();
//         assertThat(artworkLike1).isNotEqualTo(artworkLike2);
//     }

//     @Test
//     void artworkTest() throws Exception {
//         ArtworkLike artworkLike = getArtworkLikeRandomSampleGenerator();
//         Artwork artworkBack = getArtworkRandomSampleGenerator();

//         artworkLike.setArtwork(artworkBack);
//         assertThat(artworkLike.getArtwork()).isEqualTo(artworkBack);

//         artworkLike.artwork(null);
//         assertThat(artworkLike.getArtwork()).isNull();
//     }
// }
