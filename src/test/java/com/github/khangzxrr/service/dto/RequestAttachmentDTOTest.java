package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestAttachmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestAttachmentDTO.class);
        RequestAttachmentDTO requestAttachmentDTO1 = new RequestAttachmentDTO();
        requestAttachmentDTO1.setId(1L);
        RequestAttachmentDTO requestAttachmentDTO2 = new RequestAttachmentDTO();
        assertThat(requestAttachmentDTO1).isNotEqualTo(requestAttachmentDTO2);
        requestAttachmentDTO2.setId(requestAttachmentDTO1.getId());
        assertThat(requestAttachmentDTO1).isEqualTo(requestAttachmentDTO2);
        requestAttachmentDTO2.setId(2L);
        assertThat(requestAttachmentDTO1).isNotEqualTo(requestAttachmentDTO2);
        requestAttachmentDTO1.setId(null);
        assertThat(requestAttachmentDTO1).isNotEqualTo(requestAttachmentDTO2);
    }
}
