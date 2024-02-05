package com.github.khangzxrr.domain;

import static com.github.khangzxrr.domain.ArtworkCategoryTestSamples.*;
import static com.github.khangzxrr.domain.ArtworkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArtworkCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkCategory.class);
        ArtworkCategory artworkCategory1 = getArtworkCategorySample1();
        ArtworkCategory artworkCategory2 = new ArtworkCategory();
        assertThat(artworkCategory1).isNotEqualTo(artworkCategory2);

        artworkCategory2.setId(artworkCategory1.getId());
        assertThat(artworkCategory1).isEqualTo(artworkCategory2);

        artworkCategory2 = getArtworkCategorySample2();
        assertThat(artworkCategory1).isNotEqualTo(artworkCategory2);
    }

    @Test
    void artworksTest() throws Exception {
        ArtworkCategory artworkCategory = getArtworkCategoryRandomSampleGenerator();
        Artwork artworkBack = getArtworkRandomSampleGenerator();

        artworkCategory.addArtworks(artworkBack);
        assertThat(artworkCategory.getArtworks()).containsOnly(artworkBack);
        assertThat(artworkBack.getCategory()).isEqualTo(artworkCategory);

        artworkCategory.removeArtworks(artworkBack);
        assertThat(artworkCategory.getArtworks()).doesNotContain(artworkBack);
        assertThat(artworkBack.getCategory()).isNull();

        artworkCategory.artworks(new HashSet<>(Set.of(artworkBack)));
        assertThat(artworkCategory.getArtworks()).containsOnly(artworkBack);
        assertThat(artworkBack.getCategory()).isEqualTo(artworkCategory);

        artworkCategory.setArtworks(new HashSet<>());
        assertThat(artworkCategory.getArtworks()).doesNotContain(artworkBack);
        assertThat(artworkBack.getCategory()).isNull();
    }
}
