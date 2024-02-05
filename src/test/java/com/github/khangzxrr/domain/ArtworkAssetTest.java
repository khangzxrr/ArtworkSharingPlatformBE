package com.github.khangzxrr.domain;

import static com.github.khangzxrr.domain.ArtworkAssetTestSamples.*;
import static com.github.khangzxrr.domain.ArtworkTestSamples.*;
import static com.github.khangzxrr.domain.MediaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkAssetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkAsset.class);
        ArtworkAsset artworkAsset1 = getArtworkAssetSample1();
        ArtworkAsset artworkAsset2 = new ArtworkAsset();
        assertThat(artworkAsset1).isNotEqualTo(artworkAsset2);

        artworkAsset2.setId(artworkAsset1.getId());
        assertThat(artworkAsset1).isEqualTo(artworkAsset2);

        artworkAsset2 = getArtworkAssetSample2();
        assertThat(artworkAsset1).isNotEqualTo(artworkAsset2);
    }

    @Test
    void mediaTest() throws Exception {
        ArtworkAsset artworkAsset = getArtworkAssetRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        artworkAsset.setMedia(mediaBack);
        assertThat(artworkAsset.getMedia()).isEqualTo(mediaBack);

        artworkAsset.media(null);
        assertThat(artworkAsset.getMedia()).isNull();
    }

    @Test
    void artworkTest() throws Exception {
        ArtworkAsset artworkAsset = getArtworkAssetRandomSampleGenerator();
        Artwork artworkBack = getArtworkRandomSampleGenerator();

        artworkAsset.setArtwork(artworkBack);
        assertThat(artworkAsset.getArtwork()).isEqualTo(artworkBack);

        artworkAsset.artwork(null);
        assertThat(artworkAsset.getArtwork()).isNull();
    }
}
