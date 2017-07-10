package io.hmheng.grading.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class SifClientConfig {

    private String clientId;
    private String secret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
