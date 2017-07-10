package io.hmheng.grading.authorization;

import com.hmhpub.common.token.auth.SIFAuthorization;

public class AuthorizationDetails {
    
    private final SIFAuthorization sifAuthorization;
    private final String authCurrentDateTime;
    
    public AuthorizationDetails(SIFAuthorization sifAuthorization, String authCurrentDateTime) {
        this.sifAuthorization = sifAuthorization;
        this.authCurrentDateTime = authCurrentDateTime;
    }

    public SIFAuthorization getSifAuthorization() {
        return sifAuthorization;
    }

    public String getAuthCurrentDateTime() {
        return authCurrentDateTime;
    }
    
}
