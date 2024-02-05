package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkAssetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkAssetDTO.class);
        ArtworkAssetDTO artworkAssetDTO1 = new ArtworkAssetDTO();
        artworkAssetDTO1.setId(1L);
        ArtworkAssetDTO artworkAssetDTO2 = new ArtworkAssetDTO();
        assertThat(artworkAssetDTO1).isNotEqualTo(artworkAssetDTO2);
        artworkAssetDTO2.setId(artworkAssetDTO1.getId());
        assertThat(artworkAssetDTO1).isEqualTo(artworkAssetDTO2);
        artworkAssetDTO2.setId(2L);
        assertThat(artworkAssetDTO1).isNotEqualTo(artworkAssetDTO2);
        artworkAssetDTO1.setId(null);
        assertThat(artworkAssetDTO1).isNotEqualTo(artworkAssetDTO2);
    }
}
