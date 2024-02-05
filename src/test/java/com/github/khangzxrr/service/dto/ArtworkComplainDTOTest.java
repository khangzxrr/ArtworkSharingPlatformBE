package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkComplainDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkComplainDTO.class);
        ArtworkComplainDTO artworkComplainDTO1 = new ArtworkComplainDTO();
        artworkComplainDTO1.setId(1L);
        ArtworkComplainDTO artworkComplainDTO2 = new ArtworkComplainDTO();
        assertThat(artworkComplainDTO1).isNotEqualTo(artworkComplainDTO2);
        artworkComplainDTO2.setId(artworkComplainDTO1.getId());
        assertThat(artworkComplainDTO1).isEqualTo(artworkComplainDTO2);
        artworkComplainDTO2.setId(2L);
        assertThat(artworkComplainDTO1).isNotEqualTo(artworkComplainDTO2);
        artworkComplainDTO1.setId(null);
        assertThat(artworkComplainDTO1).isNotEqualTo(artworkComplainDTO2);
    }
}
