package com.hmhco.api.grading.security.config;

import com.hmhco.api.grading.security.ApiOriginFilter;
import com.hmhco.api.grading.security.GradingApiValidationFilter;
import com.hmhco.api.grading.security.PreAuthenticatedFilter;
import com.hmhco.api.grading.security.GradingUserDetailsService;
import com.hmhpub.common.security.session.filter.AuthorizationHeaderFilter;
import com.hmhpub.common.security.session.header.AuthorizationHeaderManager;
import com.hmhpub.common.token.auth.BasicClientInfo;
import com.hmhpub.common.token.auth.ClientInfo;
import com.hmhpub.common.token.auth.InMemoryClientInfoStore;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityProperties securityProperties;

  @Bean
  public ApiOriginFilter apiOriginFilter() {
    return new ApiOriginFilter();
  }

  @Bean
  public PreAuthenticatedAuthenticationProvider preAuthProvider() {
    PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(preAuthenticatedUserDetailsService());
    return provider;
  }

  @Bean
  public GradingUserDetailsService preAuthenticatedUserDetailsService() {
    return new GradingUserDetailsService();
  }

  private Set<ClientInfo> getSetOfClientInfo() {
    Set<ClientInfo> setOfClientInfo = new HashSet<ClientInfo>();
    for (Map.Entry<String, String> entry : securityProperties.getClientStore().entrySet()) {
      BasicClientInfo info = new BasicClientInfo(entry.getKey());
      info.setClientSecret(entry.getValue());
      setOfClientInfo.add(info);
    }
    return setOfClientInfo;
  }

  @Bean
  public InMemoryClientInfoStore getInMemoryClientInfoStore() {
    InMemoryClientInfoStore store = new InMemoryClientInfoStore();
    store.setClients(getSetOfClientInfo());
    return store;
  }

  @Bean
  public AuthorizationHeaderManager authorizationManager() {
    AuthorizationHeaderManager headerMgr = new AuthorizationHeaderManager();
    headerMgr.setAudience(securityProperties.getAudience());
    headerMgr.setIssuer(securityProperties.getIssuer());
    headerMgr.setSharedSecret(securityProperties.getSharedSecret());
    headerMgr.setClientInfoStore(getInMemoryClientInfoStore());

    return headerMgr;
  }

  public Filter authorizationFilter() {
    AuthorizationHeaderFilter filter = new AuthorizationHeaderFilter();
    filter.setHeaderManager(authorizationManager());
    return filter;
  }

  public Filter scoringApiValidationFilter() {
    return new GradingApiValidationFilter();
  }

  public PreAuthenticatedFilter preAuthFilter() {

    PreAuthenticatedFilter filter = new PreAuthenticatedFilter();
    try {
      filter.setAuthenticationManager(authenticationManagerBean());
    } catch (Exception e) {
      // should never get here!
    }
    filter.setCheckForPrincipalChanges(true);
    return filter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.addFilterAfter(authorizationFilter(), SecurityContextPersistenceFilter.class);
    http.addFilterAfter(preAuthFilter(), authorizationFilter().getClass());
    http.addFilterAfter(scoringApiValidationFilter(), preAuthFilter().getClass());

    http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    if (!securityProperties.isCsrfEnabled()) {
      http.csrf().disable(); // it's on by default...
    }
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/health", "/**/swagger*", "/**/api-docs", "/**/webjars/**", "/**/configuration/**");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(preAuthProvider());
  }
}
