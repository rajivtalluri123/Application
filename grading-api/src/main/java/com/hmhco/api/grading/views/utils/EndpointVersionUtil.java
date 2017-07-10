package com.hmhco.api.grading.views.utils;

import com.hmhco.api.grading.views.request.GradingEndpoint;
import com.hmhco.api.grading.views.request.SupportedVersion;

/**
 * Created by nandipatim on 10/1/15.
 */
public class EndpointVersionUtil {

    public static boolean isSupportedVersion(String version, GradingEndpoint gradingEndpoint) {

        if (gradingEndpoint == null || gradingEndpoint.getSupportedVersions() == null || version == null) {
            return false;
        }

        SupportedVersion supportedVersion = SupportedVersion.fromStringSafe(version);

        if (supportedVersion == null) {
            return false;
        }

        return gradingEndpoint.getSupportedVersions().contains(supportedVersion);
    }
}
