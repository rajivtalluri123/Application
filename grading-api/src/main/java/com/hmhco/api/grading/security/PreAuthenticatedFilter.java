package com.hmhco.api.grading.security;

import com.hmhpub.common.token.HMHPrincipal;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreAuthenticatedFilter extends AbstractPreAuthenticatedProcessingFilter {

  private static final Logger LOGGER = Logger.getLogger(PreAuthenticatedFilter.class);

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    HMHPrincipal hmhPrincipal = (HMHPrincipal) request.getUserPrincipal();
    return hmhPrincipal;

  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return "N/A";
  }

}
