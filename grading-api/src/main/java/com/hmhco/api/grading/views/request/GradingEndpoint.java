package com.hmhco.api.grading.views.request;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nandipatim on 9/30/15.
 */
public enum GradingEndpoint {


    ACTIVITY_INIT_POST(SupportedVersion.V1),
    ACTIVITY_ITEM_SCORES_POST(SupportedVersion.V1),
    STUDENT_ITEMSCORES_RESPONSE_POST(SupportedVersion.V1),
    STUDENT_ITEM_DETAILS_GET(SupportedVersion.V1),
    STUDENT_SESSION_STATUS(SupportedVersion.V1),
    STUDENT_SESSION_SCORING_COMPLETE(SupportedVersion.V1),
    STUDENT_SESSION_PUSH_SCORING(SupportedVersion.V1),
    STUDENT_ITEMS_GET(SupportedVersion.V1);

    private final List<SupportedVersion> supportedVersions;

    GradingEndpoint(SupportedVersion... supportedVersions) {
        this.supportedVersions = Arrays.asList(supportedVersions);
    }

    public List<SupportedVersion> getSupportedVersions() {
        return supportedVersions;
    }

}
