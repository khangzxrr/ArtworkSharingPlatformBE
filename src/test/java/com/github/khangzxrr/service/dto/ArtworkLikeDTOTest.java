package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkLikeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkLikeDTO.class);
        ArtworkLikeDTO artworkLikeDTO1 = new ArtworkLikeDTO();
        artworkLikeDTO1.setId(1L);
        ArtworkLikeDTO artworkLikeDTO2 = new ArtworkLikeDTO();
        assertThat(artworkLikeDTO1).isNotEqualTo(artworkLikeDTO2);
        artworkLikeDTO2.setId(artworkLikeDTO1.getId());
        assertThat(artworkLikeDTO1).isEqualTo(artworkLikeDTO2);
        artworkLikeDTO2.setId(2L);
        assertThat(artworkLikeDTO1).isNotEqualTo(artworkLikeDTO2);
        artworkLikeDTO1.setId(null);
        assertThat(artworkLikeDTO1).isNotEqualTo(artworkLikeDTO2);
    }
}
