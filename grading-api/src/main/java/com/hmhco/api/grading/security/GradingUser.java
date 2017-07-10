package com.hmhco.api.grading.security;

import java.util.Collection;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class GradingUser extends User {

  private static final long serialVersionUID = 2319550281067485889L;

  private UUID userGuid;
  private UUID leaRefId;
  private UUID schoolRefId;

  public GradingUser(String username, Collection<? extends GrantedAuthority> authorities) {
    super(username, "password", authorities);
  }

  public UUID getUserGuid() {
    return userGuid;
  }

  public void setUserGuid(UUID userGuid) {
    this.userGuid = userGuid;
  }

  public UUID getLeaRefId() {
    return leaRefId;
  }

  public void setLeaRefId(UUID leaRefId) {
    this.leaRefId = leaRefId;
  }

  public UUID getSchoolRefId() {
    return schoolRefId;
  }

  public void setSchoolRefId(UUID schoolRefId) {
    this.schoolRefId = schoolRefId;
  }

}
