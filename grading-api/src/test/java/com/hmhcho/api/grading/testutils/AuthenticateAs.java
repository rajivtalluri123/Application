package com.hmhcho.api.grading.testutils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by srikanthk on 4/28/17.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticateAs {

    AuthType value();



    String userId() default "";

    String leaRefId() default "";

    String schoolRefId() default "";

    enum AuthType{
        STUDENT, TEACHER, DISTRICT_ADMIN, SCHOOL_ADMIN, PROCTOR, TRUSTED_API

    }


}
