package com.hmhco.api.grading.security.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hmhsecurity")
public class SecurityProperties {

  private boolean csrfEnabled;
  private String sharedSecret;
  private String audience;
  private String issuer;
  private Map<String, String> clientStore = new HashMap<>();

  public String getSharedSecret() {
    return sharedSecret;
  }

  public void setSharedSecret(String sharedSecret) {
    this.sharedSecret = sharedSecret;
  }

  public boolean isCsrfEnabled() {
    return csrfEnabled;
  }

  public void setCsrfEnabled(boolean csrfEnabled) {
    this.csrfEnabled = csrfEnabled;
  }

  public String getAudience() {
    return audience;
  }

  public void setAudience(String audience) {
    this.audience = audience;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public Map<String, String> getClientStore() {
    return clientStore;
  }
}
