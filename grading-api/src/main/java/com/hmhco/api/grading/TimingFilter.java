package com.hmhco.api.grading;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;


public class TimingFilter extends GenericFilterBean {

  private final Logger logger = LoggerFactory.getLogger(TimingFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String fullRequestString = "";
    String queryString = "";
    if (request instanceof HttpServletRequest) {
      queryString = ((HttpServletRequest)request).getQueryString();
      fullRequestString = ((HttpServletRequest)request).getRequestURL().toString() + (queryString == null ? "" :
          "?" + queryString);
    }
    MDC.put("fullRequestString", fullRequestString);
    logger.info("Received request to {}", fullRequestString);
    StopWatch sw = new StopWatch();
    sw.start();
    chain.doFilter(request, response);
    sw.stop();
    MDC.put("elapsedTimeMillis", String.valueOf(sw.getTotalTimeMillis()));
    MDC.put("elapsedTimeSeconds", String.valueOf(sw.getTotalTimeSeconds()));
    logger.info("Finished request to {}. Request took {} seconds.", fullRequestString, sw.getTotalTimeSeconds());
    MDC.remove("fullRequestString");
    MDC.remove("elapsedTimeMillis");
    MDC.remove("elapsedTimeSeconds");
  }
}
