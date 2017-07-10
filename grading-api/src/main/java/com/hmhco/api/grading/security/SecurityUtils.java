package com.hmhco.api.grading.security;

import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUserName() {
        GradingUser user = getCurrentUser();
        return user == null ? null : user.getUsername();
    }

    public static UUID getLeaRefId() {
        GradingUser gradingUser = getCurrentUser();
        return gradingUser == null ? null : gradingUser.getLeaRefId();
    }

    public static UUID getSchoolRefId() {
        GradingUser reportingUser = getCurrentUser();
        return reportingUser == null ? null : reportingUser.getSchoolRefId();
    }

    public static UUID getUserGuid() {
        GradingUser reportingUser = getCurrentUser();
        return reportingUser == null ? null : reportingUser.getUserGuid();
    }

    public static boolean isTeacher() {
        return hasRole(RoleConverter.ROLE_TEACHER);
    }

    public static boolean isStudent() {
        return hasRole(RoleConverter.ROLE_STUDENT);
    }

    public static boolean isDistrictAdmin() {
        return hasRole(RoleConverter.ROLE_ADMINISTRATOR) && getLeaRefId() != null && getSchoolRefId() == null;
    }

    public static boolean isSchoolAdmin() {
        return hasRole(RoleConverter.ROLE_ADMINISTRATOR) && getSchoolRefId() != null;
    }

    public static boolean isTrustedApi() {
        return hasRole(RoleConverter.ROLE_TRUSTED_API);
    }

    private static GradingUser getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal == null || !principal.getClass().isAssignableFrom(GradingUser.class)) {
            return null;
        }

        return (GradingUser) principal;
    }

    private static boolean hasRole(String role) {
        GradingUser reportingUser = getCurrentUser();
        return reportingUser == null ? false : reportingUser.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }
}
