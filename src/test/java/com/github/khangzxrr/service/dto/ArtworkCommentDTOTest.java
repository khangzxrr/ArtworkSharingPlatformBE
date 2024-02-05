package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkCommentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkCommentDTO.class);
        ArtworkCommentDTO artworkCommentDTO1 = new ArtworkCommentDTO();
        artworkCommentDTO1.setId(1L);
        ArtworkCommentDTO artworkCommentDTO2 = new ArtworkCommentDTO();
        assertThat(artworkCommentDTO1).isNotEqualTo(artworkCommentDTO2);
        artworkCommentDTO2.setId(artworkCommentDTO1.getId());
        assertThat(artworkCommentDTO1).isEqualTo(artworkCommentDTO2);
        artworkCommentDTO2.setId(2L);
        assertThat(artworkCommentDTO1).isNotEqualTo(artworkCommentDTO2);
        artworkCommentDTO1.setId(null);
        assertThat(artworkCommentDTO1).isNotEqualTo(artworkCommentDTO2);
    }
}
