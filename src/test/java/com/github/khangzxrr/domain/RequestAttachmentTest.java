package com.github.khangzxrr.domain;

import static com.github.khangzxrr.domain.MediaTestSamples.*;
import static com.github.khangzxrr.domain.RequestAttachmentTestSamples.*;
import static com.github.khangzxrr.domain.RequestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestAttachmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestAttachment.class);
        RequestAttachment requestAttachment1 = getRequestAttachmentSample1();
        RequestAttachment requestAttachment2 = new RequestAttachment();
        assertThat(requestAttachment1).isNotEqualTo(requestAttachment2);

        requestAttachment2.setId(requestAttachment1.getId());
        assertThat(requestAttachment1).isEqualTo(requestAttachment2);

        requestAttachment2 = getRequestAttachmentSample2();
        assertThat(requestAttachment1).isNotEqualTo(requestAttachment2);
    }

    @Test
    void mediaTest() throws Exception {
        RequestAttachment requestAttachment = getRequestAttachmentRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        requestAttachment.setMedia(mediaBack);
        assertThat(requestAttachment.getMedia()).isEqualTo(mediaBack);

        requestAttachment.media(null);
        assertThat(requestAttachment.getMedia()).isNull();
    }

    @Test
    void requestTest() throws Exception {
        RequestAttachment requestAttachment = getRequestAttachmentRandomSampleGenerator();
        Request requestBack = getRequestRandomSampleGenerator();

        requestAttachment.setRequest(requestBack);
        assertThat(requestAttachment.getRequest()).isEqualTo(requestBack);

        requestAttachment.request(null);
        assertThat(requestAttachment.getRequest()).isNull();
    }
}
