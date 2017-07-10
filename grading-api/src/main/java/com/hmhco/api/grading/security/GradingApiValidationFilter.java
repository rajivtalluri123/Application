package com.hmhco.api.grading.security;

import com.hmhpub.common.token.HMHPrincipal;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class GradingApiValidationFilter implements Filter {

  private static final Logger LOGGER = Logger.getLogger(GradingApiValidationFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    HMHPrincipal userPrincipal = (HMHPrincipal) httpRequest.getUserPrincipal();
    if (userPrincipal != null) {
      LOGGER.debug("User Authenticated: " + userPrincipal.getGuid());
      filterChain.doFilter(httpRequest, httpResponse);
    } else {
      LOGGER.error("User not authorized");
      httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Security token not found");
    }
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    LOGGER.debug("GradingApiValidationFilter initialized");
  }

  @Override
  public void destroy() {
    LOGGER.debug("GradingApiValidationFilter destroyed");
  }

}
