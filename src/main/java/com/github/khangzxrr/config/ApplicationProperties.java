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

    private final ArtworkConfiguration artwork = new ArtworkConfiguration();

    public ArtworkConfiguration getArtworkConfiguration() {
        return artwork;
    }

    public static class ArtworkConfiguration {

        private double firstPaymentPercent;
        private double secondPaymentPercent;

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
    }
}
