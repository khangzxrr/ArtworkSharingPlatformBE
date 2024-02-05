package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkSellingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkSellingDTO.class);
        ArtworkSellingDTO artworkSellingDTO1 = new ArtworkSellingDTO();
        artworkSellingDTO1.setId(1L);
        ArtworkSellingDTO artworkSellingDTO2 = new ArtworkSellingDTO();
        assertThat(artworkSellingDTO1).isNotEqualTo(artworkSellingDTO2);
        artworkSellingDTO2.setId(artworkSellingDTO1.getId());
        assertThat(artworkSellingDTO1).isEqualTo(artworkSellingDTO2);
        artworkSellingDTO2.setId(2L);
        assertThat(artworkSellingDTO1).isNotEqualTo(artworkSellingDTO2);
        artworkSellingDTO1.setId(null);
        assertThat(artworkSellingDTO1).isNotEqualTo(artworkSellingDTO2);
    }
}
