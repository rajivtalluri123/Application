package io.hmheng.grading.authorization;

import com.hmhpub.common.token.auth.SIFAuthorization;
import io.hmheng.grading.config.SifAuthConfig;
import io.hmheng.grading.config.SifClientConfig;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(SifAuthConfig.class)
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private SifAuthConfig sifAuthConfig;

    private DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    @Override
    public AuthorizationDetails createSIFAuthorization(Service service) {

        String isoAuthCurrentDateTime = dateTimeFormatter.print(DateTime.now());

        SifClientConfig sifClientConfig;

        switch (service) {
            case GRADING:
                sifClientConfig = sifAuthConfig.getGrading();
                break;

            case SCORING:
                sifClientConfig = sifAuthConfig.getScoring();
                break;

            default:
                throw new IllegalArgumentException("unknown service: " + service);
        }

        SIFAuthorization sifAuthorization = new SIFAuthorization(sifClientConfig.getClientId(), sifClientConfig.getSecret(),
                isoAuthCurrentDateTime);
        return new AuthorizationDetails(sifAuthorization, isoAuthCurrentDateTime);
    }

}
