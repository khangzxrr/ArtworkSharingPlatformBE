package com.github.khangzxrr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Artwork Sharing Platform Jhipter.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    // jhipster-needle-application-properties-property
    // jhipster-needle-application-properties-property-getter
    // jhipster-needle-application-properties-property-class

    private final PaypalConfiguration paypal = new PaypalConfiguration();

    private final ArtworkConfiguration artwork = new ArtworkConfiguration();

    public ArtworkConfiguration getArtworkConfiguration() {
        return artwork;
    }

    public PaypalConfiguration getPaypalConfiguration() {
        return paypal;
    }

    public static class PaypalConfiguration {

        private String clientId;
        private String secretKey;
        private String verifyUrl;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getVerifyUrl() {
            return verifyUrl;
        }

        public void setVerifyUrl(String verifyUrl) {
            this.verifyUrl = verifyUrl;
        }
    }

    public static class ArtworkConfiguration {

        private double firstPaymentPercent;
        private double secondPaymentPercent;
        private double refundPercent;
        private double serviceFeeEarnPercent;

        public double getFirstPaymentPercent() {
            return firstPaymentPercent;
        }

        public void setFirstPaymentPercent(double firstPaymentPercent) {
            this.firstPaymentPercent = firstPaymentPercent;
        }

        public double getSecondPaymentPercent() {
            return secondPaymentPercent;
        }

        public void setSecondPaymentPercent(double secondPaymentPercent) {
            this.secondPaymentPercent = secondPaymentPercent;
        }

        public double getRefundPercent() {
            return refundPercent;
        }

        public void setRefundPercent(double refundPercent) {
            this.refundPercent = refundPercent;
        }

        public double getServiceFeeEarnPercent() {
            return serviceFeeEarnPercent;
        }

        public void setServiceFeeEarnPercent(double serviceFeeEarnPercent) {
            this.serviceFeeEarnPercent = serviceFeeEarnPercent;
        }
    }
}
