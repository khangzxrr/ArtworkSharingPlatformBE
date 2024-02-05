package com.github.khangzxrr.domain;

import static com.github.khangzxrr.domain.ArtworkAssetTestSamples.*;
import static com.github.khangzxrr.domain.CertificateTestSamples.*;
import static com.github.khangzxrr.domain.MediaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Media.class);
        Media media1 = getMediaSample1();
        Media media2 = new Media();
        assertThat(media1).isNotEqualTo(media2);

        media2.setId(media1.getId());
        assertThat(media1).isEqualTo(media2);

        media2 = getMediaSample2();
        assertThat(media1).isNotEqualTo(media2);
    }

    @Test
    void artworkAssetTest() throws Exception {
        Media media = getMediaRandomSampleGenerator();
        ArtworkAsset artworkAssetBack = getArtworkAssetRandomSampleGenerator();

        media.setArtworkAsset(artworkAssetBack);
        assertThat(media.getArtworkAsset()).isEqualTo(artworkAssetBack);
        assertThat(artworkAssetBack.getMedia()).isEqualTo(media);

        media.artworkAsset(null);
        assertThat(media.getArtworkAsset()).isNull();
        assertThat(artworkAssetBack.getMedia()).isNull();
    }

    @Test
    void certificateTest() throws Exception {
        Media media = getMediaRandomSampleGenerator();
        Certificate certificateBack = getCertificateRandomSampleGenerator();

        media.setCertificate(certificateBack);
        assertThat(media.getCertificate()).isEqualTo(certificateBack);
        assertThat(certificateBack.getMedia()).isEqualTo(media);

        media.certificate(null);
        assertThat(media.getCertificate()).isNull();
        assertThat(certificateBack.getMedia()).isNull();
    }
}
