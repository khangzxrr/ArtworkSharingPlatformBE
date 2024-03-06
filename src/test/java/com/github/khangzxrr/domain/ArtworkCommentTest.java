// package com.github.khangzxrr.domain;

// import static com.github.khangzxrr.domain.ArtworkCommentTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkTestSamples.*;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.github.khangzxrr.web.rest.TestUtil;
// import org.junit.jupiter.api.Test;

// class ArtworkCommentTest {

//     @Test
//     void equalsVerifier() throws Exception {
//         TestUtil.equalsVerifier(ArtworkComment.class);
//         ArtworkComment artworkComment1 = getArtworkCommentSample1();
//         ArtworkComment artworkComment2 = new ArtworkComment();
//         assertThat(artworkComment1).isNotEqualTo(artworkComment2);

//         artworkComment2.setId(artworkComment1.getId());
//         assertThat(artworkComment1).isEqualTo(artworkComment2);

//         artworkComment2 = getArtworkCommentSample2();
//         assertThat(artworkComment1).isNotEqualTo(artworkComment2);
//     }

//     @Test
//     void artworkTest() throws Exception {
//         ArtworkComment artworkComment = getArtworkCommentRandomSampleGenerator();
//         Artwork artworkBack = getArtworkRandomSampleGenerator();

//         artworkComment.setArtwork(artworkBack);
//         assertThat(artworkComment.getArtwork()).isEqualTo(artworkBack);

//         artworkComment.artwork(null);
//         assertThat(artworkComment.getArtwork()).isNull();
//     }
// }
