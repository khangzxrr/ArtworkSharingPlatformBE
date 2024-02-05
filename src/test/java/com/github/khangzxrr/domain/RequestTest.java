package com.github.khangzxrr.domain;

import static com.github.khangzxrr.domain.RequestAttachmentTestSamples.*;
import static com.github.khangzxrr.domain.RequestBidTestSamples.*;
import static com.github.khangzxrr.domain.RequestProgressTestSamples.*;
import static com.github.khangzxrr.domain.RequestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Request.class);
        Request request1 = getRequestSample1();
        Request request2 = new Request();
        assertThat(request1).isNotEqualTo(request2);

        request2.setId(request1.getId());
        assertThat(request1).isEqualTo(request2);

        request2 = getRequestSample2();
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    void requestBidsTest() throws Exception {
        Request request = getRequestRandomSampleGenerator();
        RequestBid requestBidBack = getRequestBidRandomSampleGenerator();

        request.addRequestBids(requestBidBack);
        assertThat(request.getRequestBids()).containsOnly(requestBidBack);
        assertThat(requestBidBack.getRequest()).isEqualTo(request);

        request.removeRequestBids(requestBidBack);
        assertThat(request.getRequestBids()).doesNotContain(requestBidBack);
        assertThat(requestBidBack.getRequest()).isNull();

        request.requestBids(new HashSet<>(Set.of(requestBidBack)));
        assertThat(request.getRequestBids()).containsOnly(requestBidBack);
        assertThat(requestBidBack.getRequest()).isEqualTo(request);

        request.setRequestBids(new HashSet<>());
        assertThat(request.getRequestBids()).doesNotContain(requestBidBack);
        assertThat(requestBidBack.getRequest()).isNull();
    }

    @Test
    void requestProgressesTest() throws Exception {
        Request request = getRequestRandomSampleGenerator();
        RequestProgress requestProgressBack = getRequestProgressRandomSampleGenerator();

        request.addRequestProgresses(requestProgressBack);
        assertThat(request.getRequestProgresses()).containsOnly(requestProgressBack);
        assertThat(requestProgressBack.getRequest()).isEqualTo(request);

        request.removeRequestProgresses(requestProgressBack);
        assertThat(request.getRequestProgresses()).doesNotContain(requestProgressBack);
        assertThat(requestProgressBack.getRequest()).isNull();

        request.requestProgresses(new HashSet<>(Set.of(requestProgressBack)));
        assertThat(request.getRequestProgresses()).containsOnly(requestProgressBack);
        assertThat(requestProgressBack.getRequest()).isEqualTo(request);

        request.setRequestProgresses(new HashSet<>());
        assertThat(request.getRequestProgresses()).doesNotContain(requestProgressBack);
        assertThat(requestProgressBack.getRequest()).isNull();
    }

    @Test
    void attachmentsTest() throws Exception {
        Request request = getRequestRandomSampleGenerator();
        RequestAttachment requestAttachmentBack = getRequestAttachmentRandomSampleGenerator();

        request.addAttachments(requestAttachmentBack);
        assertThat(request.getAttachments()).containsOnly(requestAttachmentBack);
        assertThat(requestAttachmentBack.getRequest()).isEqualTo(request);

        request.removeAttachments(requestAttachmentBack);
        assertThat(request.getAttachments()).doesNotContain(requestAttachmentBack);
        assertThat(requestAttachmentBack.getRequest()).isNull();

        request.attachments(new HashSet<>(Set.of(requestAttachmentBack)));
        assertThat(request.getAttachments()).containsOnly(requestAttachmentBack);
        assertThat(requestAttachmentBack.getRequest()).isEqualTo(request);

        request.setAttachments(new HashSet<>());
        assertThat(request.getAttachments()).doesNotContain(requestAttachmentBack);
        assertThat(requestAttachmentBack.getRequest()).isNull();
    }
}
