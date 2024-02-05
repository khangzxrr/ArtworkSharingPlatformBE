package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestProgressDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestProgressDTO.class);
        RequestProgressDTO requestProgressDTO1 = new RequestProgressDTO();
        requestProgressDTO1.setId(1L);
        RequestProgressDTO requestProgressDTO2 = new RequestProgressDTO();
        assertThat(requestProgressDTO1).isNotEqualTo(requestProgressDTO2);
        requestProgressDTO2.setId(requestProgressDTO1.getId());
        assertThat(requestProgressDTO1).isEqualTo(requestProgressDTO2);
        requestProgressDTO2.setId(2L);
        assertThat(requestProgressDTO1).isNotEqualTo(requestProgressDTO2);
        requestProgressDTO1.setId(null);
        assertThat(requestProgressDTO1).isNotEqualTo(requestProgressDTO2);
    }
}
