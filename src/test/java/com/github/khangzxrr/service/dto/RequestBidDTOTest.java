package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestBidDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestBidDTO.class);
        RequestBidDTO requestBidDTO1 = new RequestBidDTO();
        requestBidDTO1.setId(1L);
        RequestBidDTO requestBidDTO2 = new RequestBidDTO();
        assertThat(requestBidDTO1).isNotEqualTo(requestBidDTO2);
        requestBidDTO2.setId(requestBidDTO1.getId());
        assertThat(requestBidDTO1).isEqualTo(requestBidDTO2);
        requestBidDTO2.setId(2L);
        assertThat(requestBidDTO1).isNotEqualTo(requestBidDTO2);
        requestBidDTO1.setId(null);
        assertThat(requestBidDTO1).isNotEqualTo(requestBidDTO2);
    }
}
