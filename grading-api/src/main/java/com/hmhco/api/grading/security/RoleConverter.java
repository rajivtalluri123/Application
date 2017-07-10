package com.hmhco.api.grading.security;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class RoleConverter {

    private static final Logger LOGGER = Logger.getLogger(RoleConverter.class);

    // These are the Spring Rolls... :)
    /**
     * Spring Role SPRING_SECURITY_ROLE_NAME_STUDENT.
     */
    public static final String ROLE_STUDENT = "ROLE_STUDENT";
    /**
     * Spring Role SPRING_SECURITY_ROLE_NAME_TEACHER.
     */
    public static final String ROLE_TEACHER = "ROLE_TEACHER";
    /**
     * Spring Role SPRING_SECURITY_ROLE_NAME_DISTRICT_ADMINISTRATOR.
     */
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";

    public static final String ROLE_TRUSTED_API = "ROLE_TRUSTED_API";

    public static final String SPRING_SECURITY_HAS_NO_STUDENT_ROLE = "!hasRole('" + ROLE_STUDENT + "')";


    // Maps user input roles to Spring Security roles...
    private static Map<String, String> springSecurityRoleMap = new HashMap<String, String>();

    static {

        springSecurityRoleMap.put("INSTRUCTOR",   ROLE_TEACHER);
        springSecurityRoleMap.put("LEARNER",      ROLE_STUDENT);
        springSecurityRoleMap.put("ADMINISTRATOR",      ROLE_ADMINISTRATOR);
        springSecurityRoleMap.put("TEACHER",      ROLE_TEACHER);
        springSecurityRoleMap.put("TRUSTEDAPI", ROLE_TRUSTED_API);
    }

    /**
     *
     * @param tokenRoleName
     * @return springRoleName
     */
    public String getSpringSecurityRole(String tokenRoleName) {

        String springRoleName = springSecurityRoleMap.get(tokenRoleName.toUpperCase());

        if (springRoleName == null) {
            throw new RuntimeException("Role : " + tokenRoleName + " is not currently supported");
        }
        LOGGER.debug("Assigning role : " + springRoleName + " to user");

        return springRoleName;
    }
}
