package com.hmhco.api.grading.views.request;

import java.util.Arrays;

/**
 * Created by nandipatim on 9/30/15.
 */
public enum SupportedVersion {

    V1("1", JsonViews.V1.class);

    private final String version;
    private final Class<?> viewClass;

    SupportedVersion(String version, Class<?> viewClass) {
        this.version = version;
        this.viewClass = viewClass;
    }

    public String getVersion() {
        return version;
    }

    public Class<?> getViewClass() {
        return viewClass;
    }

    public static SupportedVersion fromString(String version) {
        return Arrays.stream(SupportedVersion.values()).filter(v -> v.version.equalsIgnoreCase(version)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("no SupportedVersion for " + version));
    }

    public static SupportedVersion fromStringSafe(String version) {
        return Arrays.stream(SupportedVersion.values()).filter(v -> v.version.equalsIgnoreCase(version)).findFirst().orElseGet(() -> null);
    }
}
