package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkCategoryDTO.class);
        ArtworkCategoryDTO artworkCategoryDTO1 = new ArtworkCategoryDTO();
        artworkCategoryDTO1.setId(1L);
        ArtworkCategoryDTO artworkCategoryDTO2 = new ArtworkCategoryDTO();
        assertThat(artworkCategoryDTO1).isNotEqualTo(artworkCategoryDTO2);
        artworkCategoryDTO2.setId(artworkCategoryDTO1.getId());
        assertThat(artworkCategoryDTO1).isEqualTo(artworkCategoryDTO2);
        artworkCategoryDTO2.setId(2L);
        assertThat(artworkCategoryDTO1).isNotEqualTo(artworkCategoryDTO2);
        artworkCategoryDTO1.setId(null);
        assertThat(artworkCategoryDTO1).isNotEqualTo(artworkCategoryDTO2);
    }
}
