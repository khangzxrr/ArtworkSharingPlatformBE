package com.github.khangzxrr.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SellingBidDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SellingBidDTO.class);
        SellingBidDTO sellingBidDTO1 = new SellingBidDTO();
        sellingBidDTO1.setId(1L);
        SellingBidDTO sellingBidDTO2 = new SellingBidDTO();
        assertThat(sellingBidDTO1).isNotEqualTo(sellingBidDTO2);
        sellingBidDTO2.setId(sellingBidDTO1.getId());
        assertThat(sellingBidDTO1).isEqualTo(sellingBidDTO2);
        sellingBidDTO2.setId(2L);
        assertThat(sellingBidDTO1).isNotEqualTo(sellingBidDTO2);
        sellingBidDTO1.setId(null);
        assertThat(sellingBidDTO1).isNotEqualTo(sellingBidDTO2);
    }
}
