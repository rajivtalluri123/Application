package com.hmhco.api.grading.security;

import com.hmhpub.common.token.HMHPrincipal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class GradingUserDetailsService
    implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

  public static final String DIST_REFID = "dist_refid";
  public static final String SCHOOL_REFID = "school_refid";

  @Autowired
  private RoleConverter roleConverter;

  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

    HMHPrincipal principal = (HMHPrincipal) token.getPrincipal();

    String[] tokenRoles = principal.getRoles();

    Collection<GrantedAuthority> grants = new ArrayList<>();
    String role = null;
    for (int i = 0; i < tokenRoles.length; i++) {
      role = roleConverter.getSpringSecurityRole(tokenRoles[i]);
      grants.add(new SimpleGrantedAuthority(role));
    }

    String userId = principal.getGuid();
    // In the case of a RoleConverter.ROLE_TRUSTEDAPI the Guid will be null, so fallback to username
    if (StringUtils.isBlank(userId)) {
      userId = principal.getUserName();
    }

    GradingUser user = new GradingUser(userId, grants);

    user.setUserGuid(principal.getGuid() == null ? null : UUID.fromString(principal.getGuid()));

    return user;
  }

}
