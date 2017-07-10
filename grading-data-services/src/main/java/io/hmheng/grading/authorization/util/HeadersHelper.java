package io.hmheng.grading.authorization.util;

import io.hmheng.grading.authorization.AuthorizationDetails;
import io.hmheng.grading.authorization.AuthorizationService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


/**
 * Created by nandipatim on 3/16/16.
 */
@Component
public class HeadersHelper {

    @Autowired
    private AuthorizationService authorizationService;

    public Map<String, Object> createBasicHeaders(AuthorizationService.Service service) {
        AuthorizationDetails authorizationDetails = authorizationService.createSIFAuthorization(service);
        Map<String, Object> headers = new HashMap<>();

        headers.put(Headers.AUTHORIZATION, authorizationDetails.getSifAuthorization().getAuthorization());
        headers.put(Headers.AUTH_CURRENT_DATE_TIME, authorizationDetails.getAuthCurrentDateTime());

        return headers;
    }

    public Map<String, Object> createBasicHeadersWithCorrelation(AuthorizationService.Service service) {
        AuthorizationDetails authorizationDetails = authorizationService.createSIFAuthorization(service);
        Map<String, Object> headers = new HashMap<>();
        headers.put(Headers.AUTHORIZATION, authorizationDetails.getSifAuthorization().getAuthorization());
        headers.put(Headers.AUTH_CURRENT_DATE_TIME, authorizationDetails.getAuthCurrentDateTime());
        headers.put(Headers.CORRELATION_ID, MDC.get(Headers.CORRELATION_ID));

        return headers;
    }

    public Map<String, Object> createHeaderWithCorrelationOnly() {
        Map<String, Object> headers = new HashMap<>();
        headers.put(Headers.CORRELATION_ID, MDC.get(Headers.CORRELATION_ID));
        return headers;
    }

    public HttpHeaders createHttpHeaders(AuthorizationService.Service service){
        AuthorizationDetails authorizationDetails = authorizationService.createSIFAuthorization(service);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Headers.AUTHORIZATION, authorizationDetails.getSifAuthorization().getAuthorization());
        headers.add(Headers.AUTH_CURRENT_DATE_TIME, authorizationDetails.getAuthCurrentDateTime());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }
}
