// package com.github.khangzxrr.domain;

// import static com.github.khangzxrr.domain.ArtworkAssetTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkCategoryTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkCommentTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkComplainTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkLikeTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkSellingTestSamples.*;
// import static com.github.khangzxrr.domain.ArtworkTestSamples.*;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.github.khangzxrr.web.rest.TestUtil;
// import java.util.HashSet;
// import java.util.Set;
// import org.junit.jupiter.api.Test;

// class ArtworkTest {

//     @Test
//     void equalsVerifier() throws Exception {
//         TestUtil.equalsVerifier(Artwork.class);
//         Artwork artwork1 = getArtworkSample1();
//         Artwork artwork2 = new Artwork();
//         assertThat(artwork1).isNotEqualTo(artwork2);

//         artwork2.setId(artwork1.getId());
//         assertThat(artwork1).isEqualTo(artwork2);

//         artwork2 = getArtworkSample2();
//         assertThat(artwork1).isNotEqualTo(artwork2);
//     }

//     @Test
//     void artworkSellingTest() throws Exception {
//         Artwork artwork = getArtworkRandomSampleGenerator();
//         ArtworkSelling artworkSellingBack = getArtworkSellingRandomSampleGenerator();

//         artwork.setArtworkSelling(artworkSellingBack);
//         assertThat(artwork.getArtworkSelling()).isEqualTo(artworkSellingBack);

//         artwork.artworkSelling(null);
//         assertThat(artwork.getArtworkSelling()).isNull();
//     }

//     @Test
//     void artworkAssetsTest() throws Exception {
//         Artwork artwork = getArtworkRandomSampleGenerator();
//         ArtworkAsset artworkAssetBack = getArtworkAssetRandomSampleGenerator();

//         artwork.addArtworkAssets(artworkAssetBack);
//         assertThat(artwork.getArtworkAssets()).containsOnly(artworkAssetBack);
//         assertThat(artworkAssetBack.getArtwork()).isEqualTo(artwork);

//         artwork.removeArtworkAssets(artworkAssetBack);
//         assertThat(artwork.getArtworkAssets()).doesNotContain(artworkAssetBack);
//         assertThat(artworkAssetBack.getArtwork()).isNull();

//         artwork.artworkAssets(new HashSet<>(Set.of(artworkAssetBack)));
//         assertThat(artwork.getArtworkAssets()).containsOnly(artworkAssetBack);
//         assertThat(artworkAssetBack.getArtwork()).isEqualTo(artwork);

//         artwork.setArtworkAssets(new HashSet<>());
//         assertThat(artwork.getArtworkAssets()).doesNotContain(artworkAssetBack);
//         assertThat(artworkAssetBack.getArtwork()).isNull();
//     }

//     @Test
//     void commentsTest() throws Exception {
//         Artwork artwork = getArtworkRandomSampleGenerator();
//         ArtworkComment artworkCommentBack = getArtworkCommentRandomSampleGenerator();

//         artwork.addComments(artworkCommentBack);
//         assertThat(artwork.getComments()).containsOnly(artworkCommentBack);
//         assertThat(artworkCommentBack.getArtwork()).isEqualTo(artwork);

//         artwork.removeComments(artworkCommentBack);
//         assertThat(artwork.getComments()).doesNotContain(artworkCommentBack);
//         assertThat(artworkCommentBack.getArtwork()).isNull();

//         artwork.comments(new HashSet<>(Set.of(artworkCommentBack)));
//         assertThat(artwork.getComments()).containsOnly(artworkCommentBack);
//         assertThat(artworkCommentBack.getArtwork()).isEqualTo(artwork);

//         artwork.setComments(new HashSet<>());
//         assertThat(artwork.getComments()).doesNotContain(artworkCommentBack);
//         assertThat(artworkCommentBack.getArtwork()).isNull();
//     }

//     @Test
//     void complainsTest() throws Exception {
//         Artwork artwork = getArtworkRandomSampleGenerator();
//         ArtworkComplain artworkComplainBack = getArtworkComplainRandomSampleGenerator();

//         artwork.addComplains(artworkComplainBack);
//         assertThat(artwork.getComplains()).containsOnly(artworkComplainBack);
//         assertThat(artworkComplainBack.getArtwork()).isEqualTo(artwork);

//         artwork.removeComplains(artworkComplainBack);
//         assertThat(artwork.getComplains()).doesNotContain(artworkComplainBack);
//         assertThat(artworkComplainBack.getArtwork()).isNull();

//         artwork.complains(new HashSet<>(Set.of(artworkComplainBack)));
//         assertThat(artwork.getComplains()).containsOnly(artworkComplainBack);
//         assertThat(artworkComplainBack.getArtwork()).isEqualTo(artwork);

//         artwork.setComplains(new HashSet<>());
//         assertThat(artwork.getComplains()).doesNotContain(artworkComplainBack);
//         assertThat(artworkComplainBack.getArtwork()).isNull();
//     }

//     @Test
//     void likesTest() throws Exception {
//         Artwork artwork = getArtworkRandomSampleGenerator();
//         ArtworkLike artworkLikeBack = getArtworkLikeRandomSampleGenerator();

//         artwork.addLikes(artworkLikeBack);
//         assertThat(artwork.getLikes()).containsOnly(artworkLikeBack);
//         assertThat(artworkLikeBack.getArtwork()).isEqualTo(artwork);

//         artwork.removeLikes(artworkLikeBack);
//         assertThat(artwork.getLikes()).doesNotContain(artworkLikeBack);
//         assertThat(artworkLikeBack.getArtwork()).isNull();

//         artwork.likes(new HashSet<>(Set.of(artworkLikeBack)));
//         assertThat(artwork.getLikes()).containsOnly(artworkLikeBack);
//         assertThat(artworkLikeBack.getArtwork()).isEqualTo(artwork);

//         artwork.setLikes(new HashSet<>());
//         assertThat(artwork.getLikes()).doesNotContain(artworkLikeBack);
//         assertThat(artworkLikeBack.getArtwork()).isNull();
//     }

//     @Test
//     void categoryTest() throws Exception {
//         Artwork artwork = getArtworkRandomSampleGenerator();
//         ArtworkCategory artworkCategoryBack = getArtworkCategoryRandomSampleGenerator();

//         artwork.setCategory(artworkCategoryBack);
//         assertThat(artwork.getCategory()).isEqualTo(artworkCategoryBack);

//         artwork.category(null);
//         assertThat(artwork.getCategory()).isNull();
//     }
// }
