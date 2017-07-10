package io.hmheng.grading.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "sifAuthorization")
public class SifAuthConfig {


    @NestedConfigurationProperty
    private SifClientConfig scoring;

    @NestedConfigurationProperty
    private SifClientConfig grading;


    public SifClientConfig getScoring() {
        return scoring;
    }

    public void setScoring(SifClientConfig scoring) {
        this.scoring = scoring;
    }

    public SifClientConfig getGrading() {
        return grading;
    }

    public void setGrading(SifClientConfig grading) {
        this.grading = grading;
    }
}
