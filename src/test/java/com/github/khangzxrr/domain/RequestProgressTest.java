package com.github.khangzxrr.domain;

import static com.github.khangzxrr.domain.RequestProgressTestSamples.*;
import static com.github.khangzxrr.domain.RequestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestProgressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestProgress.class);
        RequestProgress requestProgress1 = getRequestProgressSample1();
        RequestProgress requestProgress2 = new RequestProgress();
        assertThat(requestProgress1).isNotEqualTo(requestProgress2);

        requestProgress2.setId(requestProgress1.getId());
        assertThat(requestProgress1).isEqualTo(requestProgress2);

        requestProgress2 = getRequestProgressSample2();
        assertThat(requestProgress1).isNotEqualTo(requestProgress2);
    }

    @Test
    void transactionTest() throws Exception {
        RequestProgress requestProgress = getRequestProgressRandomSampleGenerator();
        // WalletTransaction walletTransactionBack = getWalletTransactionRandomSampleGenerator();

        // requestProgress.setTransaction(walletTransactionBack);
        // assertThat(requestProgress.getTransaction()).isEqualTo(walletTransactionBack);

        requestProgress.transaction(null);
        assertThat(requestProgress.getTransaction()).isNull();
    }

    @Test
    void requestTest() throws Exception {
        RequestProgress requestProgress = getRequestProgressRandomSampleGenerator();
        Request requestBack = getRequestRandomSampleGenerator();

        requestProgress.setRequest(requestBack);
        assertThat(requestProgress.getRequest()).isEqualTo(requestBack);

        requestProgress.request(null);
        assertThat(requestProgress.getRequest()).isNull();
    }
}
