package com.github.khangzxrr.domain;

import static com.github.khangzxrr.domain.CertificateTestSamples.*;
import static com.github.khangzxrr.domain.MediaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.khangzxrr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CertificateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Certificate.class);
        Certificate certificate1 = getCertificateSample1();
        Certificate certificate2 = new Certificate();
        assertThat(certificate1).isNotEqualTo(certificate2);

        certificate2.setId(certificate1.getId());
        assertThat(certificate1).isEqualTo(certificate2);

        certificate2 = getCertificateSample2();
        assertThat(certificate1).isNotEqualTo(certificate2);
    }

    @Test
    void mediaTest() throws Exception {
        Certificate certificate = getCertificateRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        certificate.setMedia(mediaBack);
        assertThat(certificate.getMedia()).isEqualTo(mediaBack);

        certificate.media(null);
        assertThat(certificate.getMedia()).isNull();
    }
}
