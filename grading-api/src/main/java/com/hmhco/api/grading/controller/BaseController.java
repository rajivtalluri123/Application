package com.hmhco.api.grading.controller;

import com.hmhco.api.grading.controller.exception.UnsupportedVersionException;
import com.hmhco.api.grading.views.request.GradingEndpoint;
import com.hmhco.api.grading.views.utils.EndpointVersionUtil;

/**
 * Created by srikanthk on 4/24/17.
 */
public abstract class BaseController {

    /**
     * Return EndpointVersionConfig if Version is supported by for endpoint. If version is not supported null is returned.
     *
     * @param version
     * @param gradingEndpoint
     */
    // TODO Look at possibility to move method to Filter or some other way so that it will Transporent to Controller.
    protected void checkForVersion(String version, GradingEndpoint gradingEndpoint) {

        if (!EndpointVersionUtil.isSupportedVersion(version, gradingEndpoint)) {
            throw new UnsupportedVersionException("version 'v"+version+"' not supported for this endpoint");
        }
    }

}
